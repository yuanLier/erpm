package edu.cqupt.mislab.erp.game.manage.model.vo;

import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("比赛信息前端展示信息模型")
public class GameDetailInfoVo {

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("比赛的名称，比赛名称可以重复")
    private String gameName;

    @ApiModelProperty("比赛创建者的名字")
    private String creatorName;

    @ApiModelProperty("该场比赛持续的年数")
    private Integer totalYear;

    @ApiModelProperty("一年的周期数")
    private Integer period;

    @ApiModelProperty("比赛的状态")
    private GameStatus gameStatus;

    @ApiModelProperty("比赛的创建时间")
    private Date gameCreateTime;

    @ApiModelProperty("该场比赛的企业个数")
    private Integer enterpriseNumber;
}
