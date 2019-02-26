package edu.cqupt.mislab.erp.game.compete.operation.order.model.entity;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class OrderPredictionInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    private GameBasicInfo gameBasicInfo;//哪一场比赛

    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    private ProductBasicInfo productBasicInfo;//哪一个产品

    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    private MarketBasicInfo marketBasicInfo;//哪一个市场

    @Basic(optional = false)
    private Double price;//产品价格

    @Basic(optional = false)
    private Integer mount;//市场需求量
}
