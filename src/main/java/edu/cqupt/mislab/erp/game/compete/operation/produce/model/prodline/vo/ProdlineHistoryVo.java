package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-04 21:43
 * @description
 */
@Data
@ApiModel("生产线历史数据展示视图")
public class ProdlineHistoryVo {

    @ApiModelProperty("哪个企业")
    private Long enterpriseId;

    @ApiModelProperty("哪个周期")
    private Integer period;

    @ApiModelProperty("当前周期下的生产线总数")
    private Long sum;

    @ApiModelProperty("历史数据变动的具体记录")
    private List<ProdlineOperationVo> prodlineOperationVoList;
}
