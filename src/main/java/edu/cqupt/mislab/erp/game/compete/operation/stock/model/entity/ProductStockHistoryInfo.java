package edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author yuanyiwen
 * @create 2019-05-25 21:54
 * @description
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ProductStockHistoryInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪个企业")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪种材料")
    private ProductBasicInfo productBasicInfo;

    @Min(1)
    @Comment(comment = "哪个周期")
    private int period;

    @Min(0)
    @Basic(optional = false)
    @Comment(comment = "原材料的库存数")
    private int productNumber;

}
