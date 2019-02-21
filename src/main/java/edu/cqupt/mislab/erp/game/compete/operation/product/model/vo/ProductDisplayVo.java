package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("产品数据展示视图")
public class ProductDisplayVo {

    @ApiModelProperty("代理主键，值同ProductDevelopInfo的主键")
    private Long id;

    @ApiModelProperty("产品研发名称")
    private String ProductName;

    @ApiModelProperty("研发总期数")
    private Integer ProductResearchPeriod;

    @ApiModelProperty("每期研发费用")
    private Double ProductResearchCost;

    @ApiModelProperty("已经研发的周期数")
    private Integer researchedPeriod;

    @ApiModelProperty("当前研发状态")
    private ProductStatusEnum ProductStatus;
}
