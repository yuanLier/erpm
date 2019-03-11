package edu.cqupt.mislab.erp.game.compete.operation.order.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.EnterpriseAdInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderChooseInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * author： chuyunfei date：2019/3/10
 */
@Data
@ApiModel
public class OrderChooseDisplayVo implements Serializable {

    @ApiModelProperty("所有的产品集合")
    private List<String> productNames;

    @ApiModelProperty("当前产品的名称")
    private String concurrentProductName;

    @ApiModelProperty("所有的市场名字信息")
    private List<String> marketNames;

    @ApiModelProperty("当前市场名称")
    private String concurrentMarketName;

    @ApiModelProperty("记录所有的订单选择信息")
    private List<Long> enterpriseAdIds;

    @ApiModelProperty("当前是哪一个在进行订单选择")
    private Long concurrentEnterpriseAdId;

    @ApiModelProperty("当前用户还有多少时间进行选择")
    private Integer remainTime;

}
