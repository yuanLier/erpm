package edu.cqupt.mislab.erp.game.compete.operation.order.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-06-23 14:11
 * @description
 */
@Data
@ApiModel("企业所投放的广告信息传输数据")
public class EnterpriseAdDto {

    @ApiModelProperty(value = "哪一个产品")
    private Long productBasicInfoId;

    @ApiModelProperty(value = "哪一个市场")
    private Long marketBasicInfoId;

    @ApiModelProperty(value = "投了多少钱")
    private Double money;
}
