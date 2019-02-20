package edu.cqupt.mislab.erp.game.compete.operation.iso.service.impl;

import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.service.IsoService;
import edu.cqupt.mislab.erp.game.compete.operation.iso.util.VoUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class IsoServiceImpl implements IsoService {

    @Autowired
    private IsoBasicInfoRepository isoBasicInfoRepository;

    @Autowired
    private IsoDevelopInfoRepository isoDevelopInfoRepository;


    @Override
    public List<IsoDisplayVo> findByEnterpriseId(Long enterpriseId) {

        // 根据企业id获取全部iso认证信息
        List<IsoDevelopInfo> isoDevelopInfoList = isoDevelopInfoRepository.findByEnterpriseId(enterpriseId);

        // 将认证信息依次转换为vo实体
        List<IsoDisplayVo> isoDisplayVoList = new ArrayList<>();
        for (IsoDevelopInfo isoDevelopInfo : isoDevelopInfoList) {
            isoDisplayVoList.add(VoUtil.castEntityToVo(isoDevelopInfo));
        }

        // 返回vo实体集
        return isoDisplayVoList;
    }

    @Override
    public List<IsoDisplayVo> findByEnterpriseIdAndAndIsoStatus(Long enterpriseId, IsoStatusEnum isoStatus) {

        // 根据企业id及认证状态获取iso认证信息
        List<IsoDevelopInfo> isoDevelopInfoList = isoDevelopInfoRepository.findByEnterpriseIdAndAndIsoStatus(enterpriseId, isoStatus);

        // 将认证信息依次转换为vo实体
        List<IsoDisplayVo> isoDisplayVoList = new ArrayList<>();
        for (IsoDevelopInfo isoDevelopInfo : isoDevelopInfoList) {
            isoDisplayVoList.add(VoUtil.castEntityToVo(isoDevelopInfo));
        }

        // 返回vo实体集
        return isoDisplayVoList;
    }

    @Override
    public IsoDisplayVo updateIsoStatus(Long isoDevelopId, IsoStatusEnum isoStatus) {

        // 根据id获取对应要修改的那条iso认证
        IsoDevelopInfo isoDevelopInfo = isoDevelopInfoRepository.findOne(isoDevelopId);

        // 修改认证状态
        isoDevelopInfo.setIsoStatus(isoStatus);

        // 保存修改
        isoDevelopInfoRepository.save(isoDevelopInfo);

        // 转换为vo实体并返回
        return VoUtil.castEntityToVo(isoDevelopInfo);
    }

    @Override
    public IsoBasicInfo updateIsoBasicInfo(IsoBasicInfo isoBasicInfo) {
        // 保存修改并返回
        return isoBasicInfoRepository.save(isoBasicInfo);
    }
}
