package edu.cqupt.mislab.erp.game.compete.operation.order.gamemodelinit;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * author： chuyunfei date：2019/3/1
 */
@Component
public class OrderGameModelInit implements GameModelInit {

    @Autowired private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private IsoBasicInfoRepository isoBasicInfoRepository;
    @Autowired private IsoDevelopInfoRepository isoDevelopInfoRepository;
    @Autowired private ProductBasicInfoRepository productBasicInfoRepository;
    @Autowired private ProductDevelopInfoRepository productDevelopInfoRepository;
    @Autowired private MarketBasicInfoRepository marketBasicInfoRepository;
    @Autowired private MarketDevelopInfoRepository marketDevelopInfoRepository;

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/1 20:11
     * @Description: 初始化订单模块的信息，在初始化之前需要保证必须保证初始化的状态是正确的
     **/
    @Override
    public List<String> initGameModel(Long gameId){

        //必须要保证ISO、Market、Product、Material全部已经被初始化了
        List<String> preInitResult = orderPreInit(gameId);

        return null;
    }

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/1 20:59
     * @Description: 确保前面的四个模块都已经被初始化后才行
     **/
    private List<String> orderPreInit(Long gameId){
        return null;
    }
}
