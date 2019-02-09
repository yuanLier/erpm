package edu.cqupt.mislab.erp.game.manage.service;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.game.manage.model.dto.GameCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.GamesSearchDto;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import org.springframework.data.domain.Example;

public interface GameManageService {

    ResponseVo<GameDetailInfoVo> createNewGame(GameCreateDto createDto);//创建一个新的比赛

    ResponseVo<String> deleteOneGame(Long gameId,Long userId);//删除一个比赛

    GamesSearchDto getGameDetailVosBySearchDto(GamesSearchDto gamesSearchDto);//查询指定的比赛数据


    GameDetailInfoVo getOneGameDetailVo(Long gameId);

    Example<GameBasicInfo> getGameBasicInfoExampleBySearchDto(GamesSearchDto searchDto);




}
