package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-10 15:27
 * @description
 */
@Data
@ApiModel("生产线信息展示视图")
public class ProdlineDisplayVo {

    @ApiModelProperty("代理主键，值同ProdlineDevelopInfo")
    private Long id;

    @ApiModelProperty("生产线名称，即生产线类型")
    private String ProdlineType;

    @ApiModelProperty("生产状态")
    private ProdlineDevelopStatus prodlineDevelopStatus;
}
