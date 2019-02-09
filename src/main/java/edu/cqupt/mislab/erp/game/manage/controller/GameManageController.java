package edu.cqupt.mislab.erp.game.manage.controller;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.game.manage.model.dto.GameCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.GamesSearchDto;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.service.GameManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.ResponseUtil.*;

@Api
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

    @ApiOperation("删除一个比赛")
    @DeleteMapping("/delete")
    public ResponseVo<Boolean> deleteOneGame(@RequestParam Long gameId){

        if(gameId < 1){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"传入的比赛ID出错");
        }

        gameManageService.deleteOneGame(gameId);

        return toSuccessResponseVo(true);
    }

    @ApiOperation("开始一个比赛")
    @PostMapping("/begin")
    public void beginOneGame(){ }

    @ApiOperation("查询指定条件的全部比赛")
    @PostMapping("/gameInfos/search")
    public ResponseVo<GamesSearchDto> getOneUserGameInfos(@Valid @RequestBody GamesSearchDto searchDto){

        return toSuccessResponseVo(gameManageService.getGameDetailVosBySearchDto(searchDto));
    }
}