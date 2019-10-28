package edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductBasicVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author yuanyiwen
 * @create 2019-05-25 21:55
 * @description
 */
@Data
@ApiModel("材料库存历史数据视图")
public class ProductStockHistoryVo {

    @ApiModelProperty("哪个企业")
    private Long enterpriseId;

    @ApiModelProperty("哪个周期")
    private Integer period;

    @ApiModelProperty("截止到该周期，企业的材料库存总量，用于总体展示")
    private Integer totalNumber;

    @ApiModelProperty("截止到该周期，企业各个材料的库存量，用于各个周期节点的详情展示")
    private Map<ProductBasicVo, Integer> productBasicVoMap;
}
