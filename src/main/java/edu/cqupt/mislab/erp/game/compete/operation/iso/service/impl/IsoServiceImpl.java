package edu.cqupt.mislab.erp.game.compete.operation.iso.service.impl;

import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.dto.IsoBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.service.IsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IsoServiceImpl implements IsoService {

    @Autowired
    private IsoBasicInfoRepository isoBasicInfoRepository;

    @Autowired
    private IsoDevelopInfoRepository isoDevelopInfoRepository;


    @Override
    public List<IsoBasicInfo> findAllNewestApplicationIsoBasicInfos(){

        return isoBasicInfoRepository.findAllNewestApplicationIsoBasicInfos();
    }

    @Override
    public List<IsoDisplayVo> findByEnterpriseId(Long enterpriseId) {

        // 根据企业id获取全部iso认证信息
        List<IsoDevelopInfo> isoDevelopInfoList = isoDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        // 非空判断
        if(isoDevelopInfoList == null || isoDevelopInfoList.size() == 0) {
            return null;
        }

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

        // 非空判断
        if(isoDevelopInfoList == null || isoDevelopInfoList.size() == 0) {
            return null;
        }

        // 将认证信息依次转换为vo实体
        List<IsoDisplayVo> isoDisplayVoList = new ArrayList<>();
        for (IsoDevelopInfo isoDevelopInfo : isoDevelopInfoList) {
            isoDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(isoDevelopInfo));
        }

        // 返回vo实体集
        return isoDisplayVoList;
    }

    @Override
    public IsoDisplayVo updateIsoStatus(Long isoDevelopId, IsoStatusEnum isoStatus) {

        // 根据id获取对应要修改的那条iso认证
        IsoDevelopInfo isoDevelopInfo = isoDevelopInfoRepository.findOne(isoDevelopId);

        // 非空判断
        if(isoDevelopInfo == null) {
            return null;
        }

        // 修改认证状态
        isoDevelopInfo.setIsoStatus(isoStatus);

        // 保存修改
        try {
            isoDevelopInfoRepository.save(isoDevelopInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 转换为vo实体并返回
        return EntityVoUtil.copyFieldsFromEntityToVo(isoDevelopInfo);
    }

    @Override
    public IsoBasicVo updateIsoBasicInfo(IsoBasicDto isoBasicDto) {

        // 将接受到的dto中的数据复制给isoBasicInfo
        IsoBasicInfo isoBasicInfo = new IsoBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(isoBasicDto,isoBasicInfo);

        // 保存修改
        try {
            isoBasicInfo = isoBasicInfoRepository.save(isoBasicInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 将获取了新id的info数据复制给isoBasicVo
        IsoBasicVo isoBasicVo = new IsoBasicVo();
        BeanCopyUtil.copyPropertiesSimple(isoBasicInfo, isoBasicVo);

        // 返回vo
        return isoBasicVo;
    }
}
