package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineBasicInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-05 21:55
 * @description
 */
public interface ProdlineBasicInfoRepository extends BasicRepository<ProdlineBasicInfo, Long>, JpaSpecificationExecutor {

    /**
     * 选取当前版本下的全部生产线
     * @return
     */
    @Query("from ProdlineBasicInfo pb where pb.enable = true group by pb.prodlineType")
    List<ProdlineBasicInfo> findNewestProdlineBasicInfos();


    /**
     * 获取处于某种状态（可用or不可用）下的生产线基本信息
     * @param enable
     * @return
     */
    List<ProdlineBasicInfo> findByEnable(boolean enable);
}
