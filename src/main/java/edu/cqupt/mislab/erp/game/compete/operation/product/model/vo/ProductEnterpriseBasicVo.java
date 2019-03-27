package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Basic;

/**
 * author： chuyunfei date：2019/3/7
 */
@Data
public class ProductEnterpriseBasicVo {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/7 12:12
     * @Description: 企业产品信息视图
     **/

    @ApiModelProperty("代理主键，是企业产品研发信息的ID")
    private Long id;

    @ApiModelProperty("产品的名称")
    private String productName;

    @ApiModelProperty("产品研发的周期数，该值大于0")
    private int productResearchPeriod;

    @ApiModelProperty("在产品研发过程中，每个周期需要支付的费用，该值大于0")
    private double productResearchCost;

    @ApiModelProperty("开始研发的周期数，当默认为研发完毕的值为1")
    private Integer beginPeriod;

    @ApiModelProperty("研发完毕的周期数，当默认为研发完毕的值为1")
    private Integer endPeriod;

    @ApiModelProperty("已经研发了多少个周期数，当默认为研发完毕的值为0")
    private Integer developedPeriod;

    @ApiModelProperty("产品开发状态")
    private ProductDevelopStatus productDevelopStatus;
}
