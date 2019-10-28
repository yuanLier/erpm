package edu.cqupt.mislab.erp.game.manage.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-07-30 17:23
 * @description
 */
@Data
@ApiModel("用户参与的比赛信息的分页展示")
public class GameDetailPageVo {

    @ApiModelProperty("比赛信息展示")
    List<GameDetailInfoVo> gameDetailInfoVo;

    @ApiModelProperty("当前页码")
    Integer currentPage;

    @ApiModelProperty("满足条件的比赛总数")
    Integer totalAmount;

}
