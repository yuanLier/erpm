package edu.cqupt.mislab.erp.game.compete.operation.iso.dao;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IsoBasicInfoRepository extends JpaRepository<IsoBasicInfo, Long> {

    /**
     * @Author: chuyunfei
     * @Description: 获取应用里面全部的ISO认证信息
     * @Date: 2019/3/1 19:53
     **/
    @Query("from IsoBasicInfo isoBasicInfo where isoBasicInfo.enable = true group by isoBasicInfo.isoName")
    List<IsoBasicInfo> findAllNewestApplicationIsoBasicInfos();
}
