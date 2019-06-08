package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author yuanyiwen
 * @create 2019-03-08 17:14
 * @description 生产线基本信息表
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class GameProdlineBasicInfo implements Serializable {

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
    @Comment(comment = "生产线的基本信息")
    private ProdlineBasicInfo prodlineBasicInfo;
}
