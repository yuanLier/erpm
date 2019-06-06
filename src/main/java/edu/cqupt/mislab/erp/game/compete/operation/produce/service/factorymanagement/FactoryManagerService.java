package edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement;


import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.dto.FactoryBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryBasicVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-05 22:16
 * @description
 */
public interface FactoryManagerService {

    /**
     * 增加一个厂房基本信息
     * @param factoryBasicDto
     * @return
     */
    FactoryBasicVo addFactoryBasicInfo(FactoryBasicDto factoryBasicDto);


    /**
     * 修改厂房基本信息
     * @param factoryBasicId
     * @param factoryBasicDto
     * @return
     */
    FactoryBasicVo updateFactoryBasicInfo(Long factoryBasicId, FactoryBasicDto factoryBasicDto);


    /**
     * 关闭一个厂房基本信息
     * @param factoryBasicId
     * @return
     */
    FactoryBasicVo closeFactoryBasicInfo(Long factoryBasicId);


    /**
     * 获取处于某一状态（可用or不可用）的厂房基本信息
     * @param enable 是否可用
     * @return
     */
    List<FactoryBasicVo> getAllFactoryBasicVoOfStatus(boolean enable);

}
