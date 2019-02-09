package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.game.manage.model.entity.GameInitInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GameInitInfoRepository extends JpaSpecificationExecutor<GameInitInfo>, JpaRepository<GameInitInfo, Long> {

    //获取最后的一条记录，也就是最新的那条记录
    GameInitInfo findTop1ByIdIsNotNullOrderByIdDesc();
}