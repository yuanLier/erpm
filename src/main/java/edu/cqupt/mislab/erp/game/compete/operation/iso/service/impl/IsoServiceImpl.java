package edu.cqupt.mislab.erp.game.compete.operation.iso.service.impl;

import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.*;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.*;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.iso.service.IsoService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IsoServiceImpl implements IsoService {

    @Autowired
    private IsoBasicInfoRepository isoBasicInfoRepository;
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private IsoDevelopedInfoRepository developedInfoRepository;
    @Autowired
    private IsoDevelopingInfoRepository developingInfoRepository;
    @Autowired
    private IsoToDevelopInfoRepository toDevelopInfoRepository;

    @Override
    public List<IsoBasicInfoDisplayVo> getAllIsoInfos(Boolean newest){

        List<IsoBasicInfo> isoBasicInfos = null;

        //选取最新的一批ISO认证信息，每个认证信息只有一条
        if(newest != null && newest){

            isoBasicInfos = isoBasicInfoRepository.findAllNewestIsoInfos();
        }else {

            isoBasicInfos = isoBasicInfoRepository.findAll();
        }

        if(isoBasicInfos != null){

            List<IsoBasicInfoDisplayVo> displayVos = new ArrayList<>();

            for(IsoBasicInfo isoBasicInfo : isoBasicInfos){

                IsoBasicInfoDisplayVo displayVo = new IsoBasicInfoDisplayVo();

                EntityVoUtil.copyFieldsFromEntityToVo(isoBasicInfo,displayVo);

                displayVos.add(displayVo);
            }

            return displayVos;
        }

        return null;
    }

    @Override
    public EnterpriseIsoDisplayVo getAllIsoInfosOfOneEnterprise(Long enterpriseId){

        final EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        //还是检测一下企业是否存在再说
        if(enterpriseBasicInfo == null){

            return null;
        }

        EnterpriseIsoDisplayVo enterpriseIsoDisplayVo = new EnterpriseIsoDisplayVo();

        //选取认证完成的认证信息
        final List<IsoDevelopedInfo> developedInfos = developedInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        //转换为Vo对象
        if(developedInfos != null){

            List<IsoDevelopedInfoDisplayVo> developedInfoDisplayVos = new ArrayList<>();

            for(IsoDevelopedInfo developedInfo : developedInfos){

                IsoDevelopedInfoDisplayVo developedInfoDisplayVo = new IsoDevelopedInfoDisplayVo();

                EntityVoUtil.copyFieldsFromEntityToVo(developedInfo,developedInfoDisplayVo);

                developedInfoDisplayVos.add(developedInfoDisplayVo);
            }

            enterpriseIsoDisplayVo.setDevelopedInfoDisplayVos(developedInfoDisplayVos);
        }

        //选取正在认证的认证信息
        final List<IsoDevelopingInfo> developingInfos = developingInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        //转换为Vo对象
        if(developingInfos != null){

            List<IsoDevelopingInfoDisplayVo> developingInfoDisplayVos = new ArrayList<>();

            for(IsoDevelopingInfo developingInfo : developingInfos){

                IsoDevelopingInfoDisplayVo developingInfoDisplayVo = new IsoDevelopingInfoDisplayVo();

                EntityVoUtil.copyFieldsFromEntityToVo(developingInfo,developingInfoDisplayVo);

                developingInfoDisplayVos.add(developingInfoDisplayVo);
            }

            enterpriseIsoDisplayVo.setDevelopingInfoDisplayVos(developingInfoDisplayVos);
        }

        //选未认证的认证信息
        final List<IsoToDevelopInfo> toDevelopInfos = toDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        //转换为Vo对象
        if(toDevelopInfos != null){

            List<IsoToDevelopInfoDisplayVo> toDevelopInfoDisplayVos = new ArrayList<>();

            for(IsoToDevelopInfo toDevelopInfo : toDevelopInfos){

                IsoToDevelopInfoDisplayVo toDevelopInfoDisplayVo = new IsoToDevelopInfoDisplayVo();

                EntityVoUtil.copyFieldsFromEntityToVo(toDevelopInfo,toDevelopInfoDisplayVo);

                toDevelopInfoDisplayVos.add(toDevelopInfoDisplayVo);
            }

            enterpriseIsoDisplayVo.setToDevelopInfoDisplayVos(toDevelopInfoDisplayVos);
        }

        return enterpriseIsoDisplayVo;
    }
}
