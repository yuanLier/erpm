package edu.cqupt.mislab.erp.game.compete.operation.iso.service.impl;

import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.service.IsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @description
 **/

@Service
public class IsoServiceImpl implements IsoService {

    @Autowired
    private IsoDevelopInfoRepository isoDevelopInfoRepository;

    @Override
    public List<IsoDisplayVo> findByEnterpriseId(Long gameId) {

        // 根据比赛id获取全部iso认证信息
        List<IsoDevelopInfo> isoDevelopInfoList = isoDevelopInfoRepository.findByEnterpriseBasicInfo_GameBasicInfo_Id(gameId);

        // 将认证信息依次转换为vo实体
        List<IsoDisplayVo> isoDisplayVoList = new ArrayList<>();
        for (IsoDevelopInfo isoDevelopInfo : isoDevelopInfoList) {
            isoDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(isoDevelopInfo));
        }

        // 返回vo实体集
        return isoDisplayVoList;
    }

    @Override
    public List<IsoDisplayVo> findByEnterpriseIdAndIsoStatus(Long enterpriseId, IsoStatusEnum isoStatus) {

        // 根据企业id及认证状态获取iso认证信息
        List<IsoDevelopInfo> isoDevelopInfoList = isoDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndIsoStatus(enterpriseId, isoStatus);

        // 将认证信息依次转换为vo实体
        List<IsoDisplayVo> isoDisplayVoList = new ArrayList<>();
        for (IsoDevelopInfo isoDevelopInfo : isoDevelopInfoList) {
            isoDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(isoDevelopInfo));
        }

        // 返回vo实体集
        return isoDisplayVoList;
    }

    @Override
    public IsoDisplayVo startDevelopIso(Long isoDevelopId) {
        // 根据id获取对应要修改的那条iso认证
        IsoDevelopInfo isoDevelopInfo = isoDevelopInfoRepository.findOne(isoDevelopId);

        // 开始认证
        isoDevelopInfo.setIsoStatus(IsoStatusEnum.DEVELOPING);
        // 设置认证开始的周期及已经认证的周期数
        isoDevelopInfo.setDevelopBeginPeriod(isoDevelopInfo.getEnterpriseBasicInfo().getEnterpriseCurrentPeriod());
        isoDevelopInfo.setResearchedPeriod(0);

        // 保存修改
        isoDevelopInfoRepository.save(isoDevelopInfo);

        // 转换为vo实体并返回
        return EntityVoUtil.copyFieldsFromEntityToVo(isoDevelopInfo);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IsoDisplayVo updateIsoStatus(Long isoDevelopId, IsoStatusEnum isoStatus) {

        // 根据id获取对应要修改的那条iso认证
        IsoDevelopInfo isoDevelopInfo = isoDevelopInfoRepository.findOne(isoDevelopId);

        // 修改认证状态
        isoDevelopInfo.setIsoStatus(isoStatus);

        // 保存修改
        isoDevelopInfoRepository.save(isoDevelopInfo);

        // 转换为vo实体并返回
        return EntityVoUtil.copyFieldsFromEntityToVo(isoDevelopInfo);
    }

}
