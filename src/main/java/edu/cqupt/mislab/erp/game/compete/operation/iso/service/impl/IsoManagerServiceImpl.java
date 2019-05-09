package edu.cqupt.mislab.erp.game.compete.operation.iso.service.impl;

import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.dto.IsoBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.service.IsoManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yuanyiwen
 * @create 2019-05-08 21:45
 * @description
 */
@Service
public class IsoManagerServiceImpl implements IsoManagerService {

    @Autowired
    private IsoBasicInfoRepository isoBasicInfoRepository;

    @Override
    public IsoBasicVo addIsoBasicInfo(IsoBasicDto isoBasicDto) {

        // 将接受到的dto中的数据复制给isoBasicInfo
        IsoBasicInfo isoBasicInfo = new IsoBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(isoBasicDto,isoBasicInfo);

        // 启用该条设置
        isoBasicInfo.setEnable(true);

        // 保存修改并刷新
        isoBasicInfo = isoBasicInfoRepository.saveAndFlush(isoBasicInfo);

        // 将获取了新id的info数据复制给isoBasicVo
        IsoBasicVo isoBasicVo = new IsoBasicVo();
        BeanCopyUtil.copyPropertiesSimple(isoBasicInfo, isoBasicVo);

        // 返回vo
        return isoBasicVo;
    }

    @Override
    public IsoBasicVo updateIsoBasicInfo(Long isoBasicId, IsoBasicDto isoBasicDto) {

        // 获取之前的iso信息并设置为不启用
        IsoBasicInfo isoBasicInfo = isoBasicInfoRepository.findOne(isoBasicId);
        isoBasicInfo.setEnable(false);

        isoBasicInfoRepository.save(isoBasicInfo);

        // 重新生成一条数据
        IsoBasicInfo newIsoBasicInfo = new IsoBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(isoBasicDto,isoBasicInfo);

        newIsoBasicInfo = isoBasicInfoRepository.saveAndFlush(newIsoBasicInfo);

        IsoBasicVo isoBasicVo = new IsoBasicVo();
        BeanCopyUtil.copyPropertiesSimple(newIsoBasicInfo, isoBasicVo);

        return isoBasicVo;
    }

    @Override
    public IsoBasicVo closeIsoBasicInfo(Long isoBasicId) {

        // 获取这个iso
        IsoBasicInfo isoBasicInfo = isoBasicInfoRepository.findOne(isoBasicId);

        // 设置为不启用
        isoBasicInfo.setEnable(false);

        // 保存修改
        isoBasicInfoRepository.save(isoBasicInfo);

        IsoBasicVo isoBasicVo = new IsoBasicVo();
        BeanCopyUtil.copyPropertiesSimple(isoBasicInfo, isoBasicVo);

        return isoBasicVo;
    }
}
