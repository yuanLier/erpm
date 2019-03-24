package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineBasicInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-09 16:36
 * @description
 */
public interface ProdlineBasicInfoRepository extends BasicRepository<ProdlineBasicInfo, Long>, JpaSpecificationExecutor {

    /**
     * @author yuanyiwen
     * @description 选取当前版本下的全部生产线
     * @date 20:50 2019/3/23
     **/
    @Query(value = "select pb from ProdlineBasicInfo pb where pb.enable = true group by pb.prodlineType", nativeQuery = true)
    List<ProdlineBasicInfo> findNewestProdlineBasicInfos();

//    @Query("from ProdlineBasicInfo pb where pb.enable = true group by pb.prodlineType")
//    List<ProdlineBasicInfo> findNewestProdlineBasicInfos();

}
