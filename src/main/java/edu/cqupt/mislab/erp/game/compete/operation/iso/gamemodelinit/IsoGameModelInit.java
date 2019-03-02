package edu.cqupt.mislab.erp.game.compete.operation.iso.gamemodelinit;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.basic.impl.GameModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo.IsoDevelopInfoBuilder;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * author： chuyunfei date：2019/3/1
 */
@Component
public class IsoGameModelInit implements GameModelInit {

    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private IsoBasicInfoRepository isoBasicInfoRepository;
    @Autowired private IsoDevelopInfoRepository isoDevelopInfoRepository;

    @Autowired private GameModelInitService gameModelInitService;

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 17:37
     * @Description: 初始化ISO模块的比赛信息，确保信息完整
     **/
    @Override
    public List<String> initGameModel(Long gameId){

        //判断是否已经被初始化过了
        if(gameModelInitService.addInitializedModelIfNotExist(gameId,this)){
            //如果没有被初始化过就进行初始化

            //选取所有的基本ISO信息
            final List<IsoBasicInfo> isoBasicInfos = isoBasicInfoRepository.findAllNewestApplicationIsoBasicInfos();

            //如果没有ISO认证信息就直接返回
            if(isoBasicInfos == null){

                return null;
            }

            //选取所有的比赛企业，这些企业是绝对存在的
            final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameInfo_Id(gameId);

            //为所有的企业生产ISO基本信息
            for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfos){

                for(IsoBasicInfo isoBasicInfo : isoBasicInfos){

                    final IsoDevelopInfoBuilder builder = IsoDevelopInfo.builder()
                            .isoBasicInfo(isoBasicInfo)
                            .enterpriseBasicInfo(enterpriseBasicInfo)
                            .isoStatus(isoBasicInfo.getIsoStatus());

                    //特殊状态认证信息处理
                    if(isoBasicInfo.getIsoStatus() == IsoStatusEnum.DEVELOPED){

                        builder.developBeginPeriod(1)
                                .researchedPeriod(0)
                                .developEndPeriod(1);
                    }

                    final IsoDevelopInfo isoDevelopInfo = builder.build();

                    //存储该信息
                    isoDevelopInfoRepository.save(isoDevelopInfo);
                }
            }
        }

        return null;
    }
}
