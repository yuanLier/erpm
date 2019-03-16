package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineTypeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-15 9:04
 * @description
 */
@Data
@ApiModel("厂房及厂房中满足条件的生产线类型选择视图")
public class FactoryProdlineTypeVo {
    @ApiModelProperty("代理主键，值同FactoryDevelopInfo")
    private Long id;

    @ApiModelProperty("用户拥有的工厂类型")
    private String factoryType;

    @ApiModelProperty("该工厂中满足条件的生产线集合")
    private List<ProdlineTypeVo> prodlineTypeVoList;
}
