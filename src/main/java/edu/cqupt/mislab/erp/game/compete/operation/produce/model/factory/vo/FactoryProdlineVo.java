package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDisplayVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-10 15:24
 * @description
 */
@Data
@ApiModel("厂房及该厂房中全部生产线信息")
public class FactoryProdlineVo {

    @ApiModelProperty("代理主键，值同FactoryDevelopInfo")
    private Long id;

    @ApiModelProperty("该厂房的基本信息视图")
    private FactoryDisplayVo factoryDisplayVo;

    @ApiModelProperty("该厂房中生产线基本信息视图集合")
    private List<ProdlineDisplayVo> prodlineDisplayVoList;

}