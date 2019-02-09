package edu.cqupt.mislab.erp.game.manage.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatus;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.dao.UserTeacherRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@ApiModel("比赛信息搜索")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GamesSearchDto {

    @Exist(repository = GameBasicInfoRepository.class)
    @ApiModelProperty("查询一条比赛数据")
    private Long gameId;

    @ApiModelProperty("比赛名称")
    private String gameName;

    @Exist(repository = UserTeacherRepository.class)
    @ApiModelProperty("查询一个教师的所有学生比赛信息")
    private Long teacherId;

    @Exist(repository = UserStudentRepository.class)
    @ApiModelProperty("查询一个学生的所有比赛信息")
    private Long studentId;

    @ApiModelProperty("查询指定状态的比赛信息")
    private GameStatus gameStatus;

    @Null
    @ApiModelProperty("总共有多少条数据，该数据由后台返回")
    private Long totalMessage;

    @Min(1L)
    @NotNull
    @ApiModelProperty(value = "一页最多有多少条数据，这个数据由前端传到后台，最小为1",required = true)
    private Integer pageSize;

    @Min(1L)
    @NotNull
    @ApiModelProperty(value = "当前请求是那一页，这个数据由前端传到后台，最小为1",required = true)
    private Integer concurrentPage;

    @Null
    @ApiModelProperty("当前页的数据，该数据由后台返回")
    private List<GameDetailInfoVo> pageData;
}
