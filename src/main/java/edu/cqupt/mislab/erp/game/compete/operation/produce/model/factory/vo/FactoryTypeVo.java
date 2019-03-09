package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-08 17:23
 * @description
 */
@Data
@ApiModel("厂房类型选择视图")
public class FactoryTypeVo {

    @ApiModelProperty("代理主键，值同FactoryDevelopInfo")
    private Long id;

    @ApiModelProperty("用户拥有的工厂类型")
    private String factoryType;
}
