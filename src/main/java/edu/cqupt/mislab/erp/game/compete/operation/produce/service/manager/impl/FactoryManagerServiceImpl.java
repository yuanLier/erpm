package edu.cqupt.mislab.erp.game.compete.operation.produce.service.manager.impl;

import edu.cqupt.mislab.erp.commons.aspect.BadModificationException;
import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.dto.FactoryBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.manager.FactoryManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-05 22:21
 * @description
 */
@Service
public class FactoryManagerServiceImpl implements FactoryManagerService {

    @Autowired
    private FactoryBasicInfoRepository factoryBasicInfoRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FactoryBasicVo addFactoryBasicInfo(FactoryBasicDto factoryBasicDto) {

        // 将接受到的dto中的数据复制给factoryBasicInfo
        FactoryBasicInfo factoryBasicInfo = new FactoryBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(factoryBasicDto,factoryBasicInfo);

        // 启用该条设置
        factoryBasicInfo.setEnable(true);

        // 保存修改并刷新
        factoryBasicInfo = factoryBasicInfoRepository.saveAndFlush(factoryBasicInfo);

        // 将获取了新id的info数据复制给factoryBasicVo
        FactoryBasicVo factoryBasicVo = new FactoryBasicVo();
        BeanCopyUtil.copyPropertiesSimple(factoryBasicInfo, factoryBasicVo);

        // 返回vo
        return factoryBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FactoryBasicVo updateFactoryBasicInfo(Long factoryBasicId, FactoryBasicDto factoryBasicDto) {

        // 获取之前的厂房信息并设置为不启用
        FactoryBasicInfo factoryBasicInfo = factoryBasicInfoRepository.findOne(factoryBasicId);
        if(!factoryBasicInfo.isEnable()) {
            throw new BadModificationException();
        }
        factoryBasicInfo.setEnable(false);

        factoryBasicInfoRepository.save(factoryBasicInfo);

        // 重新生成一条数据
        FactoryBasicInfo newFactoryBasicInfo = new FactoryBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(factoryBasicDto,newFactoryBasicInfo);
        // 设置可用
        newFactoryBasicInfo.setEnable(true);

        newFactoryBasicInfo = factoryBasicInfoRepository.saveAndFlush(newFactoryBasicInfo);

        FactoryBasicVo factoryBasicVo = new FactoryBasicVo();
        BeanCopyUtil.copyPropertiesSimple(newFactoryBasicInfo, factoryBasicVo);

        return factoryBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FactoryBasicVo closeFactoryBasicInfo(Long factoryBasicId) {

        // 获取这个厂房
        FactoryBasicInfo factoryBasicInfo = factoryBasicInfoRepository.findOne(factoryBasicId);

        // 设置为不启用
        factoryBasicInfo.setEnable(false);

        // 保存修改
        factoryBasicInfoRepository.save(factoryBasicInfo);

        FactoryBasicVo factoryBasicVo = new FactoryBasicVo();
        BeanCopyUtil.copyPropertiesSimple(factoryBasicInfo, factoryBasicVo);

        return factoryBasicVo;
    }

    @Override
    public List<FactoryBasicVo> getAllFactoryBasicVoOfStatus(boolean enable) {

        List<FactoryBasicInfo> factoryBasicInfoList = factoryBasicInfoRepository.findByEnable(enable);

        List<FactoryBasicVo> factoryBasicVoList = new ArrayList<>();
        for (FactoryBasicInfo factoryBasicInfo : factoryBasicInfoList) {
            FactoryBasicVo factoryBasicVo = new FactoryBasicVo();
            BeanCopyUtil.copyPropertiesSimple(factoryBasicInfo, factoryBasicVo);

            factoryBasicVoList.add(factoryBasicVo);
        }

        return factoryBasicVoList;
    }
}
