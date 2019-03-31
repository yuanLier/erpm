package edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author yuanyiwen
 * @create 2019-03-31 22:09
 * @description 用于描述材料订购时运输方式的基本信息表
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class TransportBasicInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "运输方式的名称")
    private String transportName;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "运达所需要的周期数")
    private Integer transportPeriod;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "每周期所需费用")
    private Double transportPrice;

    @Basic(optional = false)
    @Comment(comment = "是否启用")
    private boolean enable;

}
