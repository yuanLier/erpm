package edu.cqupt.mislab.erp.game.compete.operation.iso.dao;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IsoBasicInfoRepository extends JpaRepository<IsoBasicInfo, Long>, JpaSpecificationExecutor {

    /**
     * @Author: chuyunfei
     * @Description: 获取应用里面全部的ISO认证信息
     * @Date: 2019/3/1 19:53
     **/
    @Query(nativeQuery = true,value = "select * from iso_basic_info where id in (select max(id) from iso_basic_info group by iso_name)")
    List<IsoBasicInfo> findAllNewestApplicationIsoBasicInfos();
}
