package edu.cqupt.mislab.erp.game.compete.operation.market.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yuanyiwen
 * @create 2019-05-15 19:51
 * @description
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class MarketHistoryInfo implements Serializable {

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

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "截止到该周期，企业拥有的某个市场")
    private MarketBasicInfo marketBasicInfo;

}