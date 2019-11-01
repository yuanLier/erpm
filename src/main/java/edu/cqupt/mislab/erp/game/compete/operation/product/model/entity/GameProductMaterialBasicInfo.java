package edu.cqupt.mislab.erp.game.compete.operation.product.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author yuanyiwen
 * @create 2019-10-31 20:38
 * @description 比赛期间使用的产品材料基本信息表，当且仅当比赛开始时进行初始化
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class GameProductMaterialBasicInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪场比赛")
    private GameBasicInfo gameBasicInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "产品材料的基本信息")
    private ProductMaterialBasicInfo productMaterialBasicInfo;

}
