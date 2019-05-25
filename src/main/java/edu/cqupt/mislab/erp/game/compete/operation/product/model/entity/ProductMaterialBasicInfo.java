package edu.cqupt.mislab.erp.game.compete.operation.product.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import lombok.*;

import javax.persistence.*;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/4 22:00
 * @Description: 用于记录每个产品所需要的材料组成信息，若存在某个启用产品无原料组成表，将无法进行比赛的初始化
 **/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ProductMaterialBasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个产品")
    private ProductBasicInfo productBasicInfo;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false)
    @Comment(comment = "哪一个原料，要注意这个原料信息是可以被修改的")
    private MaterialBasicInfo materialBasicInfo;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "需要该种材料多少个")
    private int number;

    @Basic(optional = false)
    @Comment(comment = "该数据是否被启用，当前最新数据是启用，所有的历史数据均为未启用，必须保证同一个信息最多只有一个Enable = true")
    private boolean enable;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        ProductMaterialBasicInfo that = (ProductMaterialBasicInfo) o;
        return number == that.number&&enable == that.enable&&Objects.equal(id,that.id);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,number,enable);
    }
}
