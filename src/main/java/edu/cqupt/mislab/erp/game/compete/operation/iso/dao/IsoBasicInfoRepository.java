package edu.cqupt.mislab.erp.game.compete.operation.iso.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author chuyunfei
 * @description
 **/

public interface IsoBasicInfoRepository extends BasicRepository<IsoBasicInfo, Long> {

    /**
     * @Author: chuyunfei
     * @Description: 获取应用里面全部的ISO认证信息
     * @Date: 2019/3/1 19:53
     **/
    @Query("from IsoBasicInfo isoBasicInfo where isoBasicInfo.enable = true group by isoBasicInfo.isoName")
    List<IsoBasicInfo> findAllNewestApplicationIsoBasicInfos();


    /**
     * @author yuanyiwen
     * @description 获取处于某种状态（可用or不可用）下的iso基本信息
     * @date 18:00 2019/5/18
     **/
    List<IsoBasicInfo> findByEnable(boolean enable);

}
