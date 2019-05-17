package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chuyunfei
 * @description
 **/

@Data
@ApiModel("产品数据展示视图")
public class ProductDisplayVo {

    @ApiModelProperty("代理主键，值同ProductDevelopInfo的主键")
    private Long id;

    @ApiModelProperty("产品研发名称")
    private String productName;

    @ApiModelProperty("研发总期数")
    private Integer productResearchPeriod;

    @ApiModelProperty("每期研发费用")
    private Double productResearchCost;

    @ApiModelProperty("已经研发的周期数")
    private Integer developedPeriod;

    @ApiModelProperty("当前研发状态")
    private ProductDevelopStatusEnum productDevelopStatus;
}
