package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryBasicInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-05 22:09
 * @description
 */
public interface FactoryBasicInfoRepository extends BasicRepository<FactoryBasicInfo, Long>, JpaSpecificationExecutor {

    /**
     * 选取当前版本下的全部厂房
     * @return
     */
    @Query("from FactoryBasicInfo f where f.enable = true group by f.factoryType")
    List<FactoryBasicInfo> findNewestFactoryBasicInfos();


    /**
     * 获取处于某种状态（可用or不可用）下的厂房基本信息
     * @param enable
     * @return
     */
    List<FactoryBasicInfo> findByEnable(boolean enable);
}
