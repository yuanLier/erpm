package edu.cqupt.mislab.erp.game.compete.operation.iso.dao;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IsoBasicInfoRepository extends JpaSpecificationExecutor, JpaRepository<IsoBasicInfo, Long> {

    @Query(value = "select * from iso_basic_info where id in(select max(id) from iso_basic_info group by iso_name)",nativeQuery = true)
    List<IsoBasicInfo> findAllNewestIsoInfos();//选取每一个ISO认证的最新的数据
}
