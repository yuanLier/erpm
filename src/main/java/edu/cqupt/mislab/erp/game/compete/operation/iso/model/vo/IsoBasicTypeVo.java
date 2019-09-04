package edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-09-04 11:25
 * @description
 */
@Data
@ApiModel("iso类型视图")
@AllArgsConstructor
public class IsoBasicTypeVo {

    @ApiModelProperty("代理主键，值同isoBasicId")
    private Long id;

    @ApiModelProperty("iso名称")
    private String isoName;
}
