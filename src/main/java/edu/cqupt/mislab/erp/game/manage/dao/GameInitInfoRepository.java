package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameInitInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameInitInfoRepository extends JpaSpecificationExecutor<GameInitInfo>, BasicRepository<GameInitInfo, Long> {

    //获取最后的一条记录，也就是最新的那条记录
    @Deprecated
    GameInitInfo findTop1ByIdIsNotNullOrderByIdDesc();

    //获取最行的一条数据，为了兼容还是写成这个样子，需要业务保障
    @Query("from GameInitInfo g where g.enable = true")
    List<GameInitInfo> findNewestGameInitInfo();
}