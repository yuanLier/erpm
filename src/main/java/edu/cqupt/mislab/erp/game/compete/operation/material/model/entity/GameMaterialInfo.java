package edu.cqupt.mislab.erp.game.compete.operation.material.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import lombok.*;

import javax.persistence.*;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/4 21:37
 * @Description: 比赛的材料信息表用于存储比赛中所用到的所有材料信息
 **/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class GameMaterialInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "那一场比赛")
    private GameBasicInfo gameBasicInfo;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "那一种原料")
    private MaterialBasicInfo materialBasicInfo;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        GameMaterialInfo that = (GameMaterialInfo) o;
        return Objects.equal(id,that.id);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id);
    }
}
