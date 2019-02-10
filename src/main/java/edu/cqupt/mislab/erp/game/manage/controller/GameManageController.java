package edu.cqupt.mislab.erp.game.manage.controller;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.dto.GameCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.GamesSearchDto;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.service.GameManageService;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.ResponseUtil.*;

@Api
@Validated
@RestController
@RequestMapping("/game/manage")
public class GameManageController {

    @Autowired
    private GameManageService gameManageService;

    @ApiOperation("创建一个新的比赛")
    @PostMapping("/create")
    public ResponseVo<GameDetailInfoVo> createNewGame(@Valid @RequestBody GameCreateDto createDto){

        return gameManageService.createNewGame(createDto);
    }

    @ApiOperation("删除一个比赛，只有创建者可以删除处于创建状态的比赛")
    @DeleteMapping("/delete")
    public ResponseVo<String> deleteOneGame(
            @Exist(repository = GameBasicInfoRepository.class) @RequestParam Long gameId
            ,@Exist(repository = UserStudentRepository.class) @RequestParam Long userId){

        return gameManageService.deleteOneGame(gameId,userId);
    }

    @ApiOperation("开始一个比赛")
    @PostMapping("/begin")
    public ResponseVo<String> beginOneGame(
            @Exist(repository = GameBasicInfoRepository.class) @RequestParam Long gameId
            ,@Exist(repository = UserStudentRepository.class) @RequestParam Long userId
    ){

        return gameManageService.beginOneGame(userId,gameId);
    }

    @ApiOperation("查询指定条件的全部比赛")
    @PostMapping("/gameInfos/search")
    public ResponseVo<GamesSearchDto> getOneUserGameInfos(@Valid @RequestBody GamesSearchDto searchDto){

        return toSuccessResponseVo(gameManageService.getGameDetailVosBySearchDto(searchDto));
    }
}