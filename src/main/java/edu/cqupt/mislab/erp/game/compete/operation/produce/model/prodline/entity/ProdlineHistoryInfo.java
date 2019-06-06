package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yuanyiwen
 * @create 2019-06-04 21:37
 * @description
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ProdlineHistoryInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪个企业")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @Min(1)
    @Comment(comment = "哪个周期")
    private int period;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "是对哪个生产线进行的操作")
    private GameProdlineBasicInfo prodlineBasicInfo;

    @NotNull
    @Comment(comment = "具体操作")
    private String operation;

    @Basic
    @Comment(comment = "对生产线操作所造成的厂房数量变动")
    private int mount;
}
