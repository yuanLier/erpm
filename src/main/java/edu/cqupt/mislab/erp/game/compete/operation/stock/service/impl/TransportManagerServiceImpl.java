package edu.cqupt.mislab.erp.game.compete.operation.stock.service.impl;

import edu.cqupt.mislab.erp.commons.aspect.BadModificationException;
import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.TransportBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.dto.TransportBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.TransportBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.TransportBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.service.TransportManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-25 18:28
 * @description
 */
@Service
public class TransportManagerServiceImpl implements TransportManagerService {


    @Autowired
    private TransportBasicInfoRepository transportBasicInfoRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransportBasicVo addTransportBasicInfo(TransportBasicDto transportBasicDto) {

        // 将接受到的dto中的数据复制给transportBasicInfo
        TransportBasicInfo transportBasicInfo = new TransportBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(transportBasicDto,transportBasicInfo);

        // 启用该条设置
        transportBasicInfo.setEnable(true);

        // 保存修改并刷新
        transportBasicInfo = transportBasicInfoRepository.saveAndFlush(transportBasicInfo);

        // 将获取了新id的info数据复制给transportBasicVo
        TransportBasicVo transportBasicVo = new TransportBasicVo();
        BeanCopyUtil.copyPropertiesSimple(transportBasicInfo, transportBasicVo);

        // 返回vo
        return transportBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransportBasicVo updateTransportBasicInfo(Long transportBasicId, TransportBasicDto transportBasicDto) {

        // 获取之前的运输方式信息并设置为不启用
        TransportBasicInfo transportBasicInfo = transportBasicInfoRepository.findOne(transportBasicId);
        if(!transportBasicInfo.isEnable()) {
            throw new BadModificationException();
        }
        transportBasicInfo.setEnable(false);

        transportBasicInfoRepository.save(transportBasicInfo);

        // 重新生成一条数据
        TransportBasicInfo newTransportBasicInfo = new TransportBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(transportBasicDto,newTransportBasicInfo);
        // 设置可用
        newTransportBasicInfo.setEnable(true);

        newTransportBasicInfo = transportBasicInfoRepository.saveAndFlush(newTransportBasicInfo);

        TransportBasicVo transportBasicVo = new TransportBasicVo();
        BeanCopyUtil.copyPropertiesSimple(newTransportBasicInfo, transportBasicVo);

        return transportBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransportBasicVo closeTransportBasicInfo(Long transportBasicId) {

        // 获取这个运输方式
        TransportBasicInfo transportBasicInfo = transportBasicInfoRepository.findOne(transportBasicId);

        // 设置为不启用
        transportBasicInfo.setEnable(false);

        // 保存修改
        transportBasicInfoRepository.save(transportBasicInfo);

        TransportBasicVo transportBasicVo = new TransportBasicVo();
        BeanCopyUtil.copyPropertiesSimple(transportBasicInfo, transportBasicVo);

        return transportBasicVo;
    }

    @Override
    public List<TransportBasicVo> getAllTransportBasicVoOfStatus(boolean enable) {

        List<TransportBasicInfo> transportBasicInfoList = transportBasicInfoRepository.findByEnable(enable);

        List<TransportBasicVo> transportBasicVoList = new ArrayList<>();
        for (TransportBasicInfo transportBasicInfo : transportBasicInfoList) {
            TransportBasicVo transportBasicVo = new TransportBasicVo();
            BeanCopyUtil.copyPropertiesSimple(transportBasicInfo, transportBasicVo);

            transportBasicVoList.add(transportBasicVo);
        }

        return transportBasicVoList;
    }
}
