package edu.cqupt.mislab.erp.game.manage.service;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.manage.model.dto.GameCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.GamesSearchDto;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import org.springframework.data.domain.Example;

public interface GameManageService {

    //创建一个新的比赛
    WebResponseVo<GameDetailInfoVo> createNewGame(GameCreateDto createDto);

    //删除一个比赛
    WebResponseVo<Object> deleteOneGame(Long gameId,Long userId,String password);

    //查询指定的比赛数据
    GamesSearchDto getGameDetailVosBySearchDto(GamesSearchDto gamesSearchDto);

    Example<GameBasicInfo> getGameBasicInfoExampleBySearchDto(GamesSearchDto searchDto);//用于比赛的搜索接口

    //开启一个比赛
    WebResponseVo<String> beginOneGame(Long userId,Long gameId);
}
