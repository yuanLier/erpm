package edu.cqupt.mislab.erp.game.manage.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseUtil;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.commons.validators.annotations.GameStatusValid;
import edu.cqupt.mislab.erp.commons.validators.annotations.UserStatusValid;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.dto.GameCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.GamesSearchDto;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatusEnum;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailPageVo;
import edu.cqupt.mislab.erp.game.manage.service.GameManageService;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author chuyunfei
 * @description
 * @date 22:28 2019/4/26
 **/

@Api
@Validated
@CrossOrigin
@RestController
@RequestMapping("/game/manage")
public class GameManageController {

    @Autowired private GameManageService gameManageService;

    @ApiOperation(value = "创建一个新的比赛")
    @PostMapping("/create")
    public WebResponseVo<GameDetailInfoVo> createNewGame(@Valid @RequestBody GameCreateDto createDto){

        return gameManageService.createNewGame(createDto);
    }

    @ApiOperation(value = "删除一个比赛",notes = "1、只有创建者可以删除比赛；2、只能删除处于创建状态的比赛；3、敏感操作，需要输入密码")
    @DeleteMapping("/delete")
    public WebResponseVo<Object> deleteOneGame(
            @GameStatusValid(requireStatus = GameStatusEnum.CREATE) @RequestParam Long gameId,
            @UserStatusValid(isEnable = true) @RequestParam Long userId,
            @RequestParam String password){

        return gameManageService.deleteOneGame(gameId,userId,password);
    }

    @ApiOperation("开始一个比赛")
    @PostMapping("/begin")
    public WebResponseVo<String> beginOneGame(
            @GameStatusValid(requireStatus = GameStatusEnum.CREATE) @RequestParam Long gameId,
            @UserStatusValid(isEnable = true) @RequestParam Long userId){

        return gameManageService.beginOneGame(userId,gameId);
    }

    @ApiOperation("查询指定条件的全部比赛")
    @PostMapping("/search")
    public WebResponseVo<GamesSearchDto> getOneUserGameInfos(@Valid @RequestBody GamesSearchDto searchDto){

        return WebResponseUtil.toSuccessResponseVoWithData(gameManageService.getGameDetailVosBySearchDto(searchDto));
    }

    @ApiOperation(value = "查询用户处于某种比赛状态的全部比赛", notes = "currentPage和amountOfPage分别表示当前请求的页码和每页的数据数量")
    @GetMapping("/status")
    public WebResponseVo<GameDetailPageVo> getGamesOfUser(@Exist(repository = UserStudentRepository.class)
                                                                @RequestParam Long userId,
                                                          @RequestParam GameStatusEnum gameStatus,
                                                          @RequestParam Integer currentPage,
                                                          @RequestParam Integer amountOfPage) {

        if(currentPage <= 0 || amountOfPage <= 0) {
            return WebResponseUtil.toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "页码请求应大于0");
        }

        GameDetailPageVo gameDetailPageVo = gameManageService.getGamesOfUser(userId, gameStatus, currentPage, amountOfPage);

        if(gameDetailPageVo == null) {
            return WebResponseUtil.toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "当前页码不存在！");
        }

        return WebResponseUtil.toSuccessResponseVoWithData(gameDetailPageVo);
    }

    @ApiOperation(value = "判断用户是否存在于某一比赛中", notes = "注 ：如果用户创建了比赛但并未参与该比赛中的企业，则返回值为false")
    @GetMapping("/judge")
    public WebResponseVo<Boolean> whetherUserIsInGame(@Exist(repository = GameBasicInfoRepository.class)
                                                            @RequestParam Long gameId,
                                                      @Exist(repository = UserStudentRepository.class)
                                                            @RequestParam Long userId) {

        return WebResponseUtil.toSuccessResponseVoWithData(gameManageService.whetherUserIsInGame(gameId, userId));
    }
}