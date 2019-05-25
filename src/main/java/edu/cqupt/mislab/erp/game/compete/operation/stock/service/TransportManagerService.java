package edu.cqupt.mislab.erp.game.compete.operation.stock.service;

import edu.cqupt.mislab.erp.game.compete.operation.stock.model.dto.TransportBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.TransportBasicVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-25 18:13
 * @description 其实是运输方式的管理端
 */
public interface TransportManagerService {

    /**
     * 增加一个运输方式
     * @param transportBasicDto
     * @return
     */
    TransportBasicVo addTransportBasicInfo(TransportBasicDto transportBasicDto);


    /**
     * 修改运输方式基本信息
     * @param transportBasicId
     * @param transportBasicDto
     * @return
     */
    TransportBasicVo updateTransportBasicInfo(Long transportBasicId, TransportBasicDto transportBasicDto);


    /**
     * 关闭一个运输方式
     * @param transportBasicId
     * @return
     */
    TransportBasicVo closeTransportBasicInfo(Long transportBasicId);


    /**
     * 获取处于某一状态（可用or不可用）的运输方式基本信息
     * @param enable 是否可用
     * @return
     */
    List<TransportBasicVo> getAllTransportBasicVoOfStatus(boolean enable);

}
