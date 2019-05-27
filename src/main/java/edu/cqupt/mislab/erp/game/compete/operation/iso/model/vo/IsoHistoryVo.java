package edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-11 22:12
 * @description
 */
@Data
@ApiModel("iso历史数据展示视图")
public class IsoHistoryVo {

    @ApiModelProperty("哪个企业")
    private Long enterpriseId;

    @ApiModelProperty("哪个周期")
    private Integer period;

    @ApiModelProperty("截止到该周期，企业拥有的iso（即认证完成的iso）")
    private List<IsoBasicVo> isoBasicVoList;
}
