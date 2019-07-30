package edu.cqupt.mislab.erp.game.manage.service;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.manage.model.dto.GameCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.GamesSearchDto;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatusEnum;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailPageVo;
import org.springframework.data.domain.Example;

/**
 * @author chuyunfei
 * @description 比赛管理
 * @date 21:48 2019/4/26
 **/

public interface GameManageService {

    /**
     * @author chuyunfei
     * @description 创建一个新的比赛
     * @date 21:46 2019/4/26
     **/
    WebResponseVo<GameDetailInfoVo> createNewGame(GameCreateDto createDto);

    /**
     * @author chuyunfei
     * @description 删除一个比赛
     * @date 21:46 2019/4/26
     **/
    WebResponseVo<Object> deleteOneGame(Long gameId,Long userId,String password);

    /**
     * @author chuyunfei
     * @description 查询指定的比赛数据
     * @date 21:46 2019/4/26
     **/
    GamesSearchDto getGameDetailVosBySearchDto(GamesSearchDto gamesSearchDto);

    /**
     * @author chuyunfei
     * @description 用于比赛的搜索接口
     * @date 21:48 2019/4/26
     **/
    Example<GameBasicInfo> getGameBasicInfoExampleBySearchDto(GamesSearchDto searchDto);

    /**
     * @author chuyunfei
     * @description 开启一个比赛
     * @date 21:46 2019/4/26
     **/
    WebResponseVo<String> beginOneGame(Long userId,Long gameId);
    
    /**
     * @author yuanyiwen
     * @description 获取一个用户处于某种状态的全部企业
     * @date 13:38 2019/7/28
     **/
    GameDetailPageVo getGamesOfUser(Long userId, GameStatusEnum gameStatus, Integer currentPage, Integer amountOfPage);
}
