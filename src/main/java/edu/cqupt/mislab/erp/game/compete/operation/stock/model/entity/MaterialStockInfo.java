package edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
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
public class MaterialStockInfo implements Serializable {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/5 11:45
     * @Description: 用于存储企业的历史原料信息
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个企业")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一种原料")
    private MaterialBasicInfo materialBasicInfo;

    @Basic(optional = false)
    @Comment(comment = "原材料的库存数，这个数值大于等于0")
    private Integer materialNumber;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        MaterialStockInfo that = (MaterialStockInfo) o;
        return materialNumber == that.materialNumber&&Objects.equal(id,that.id)&&Objects.equal(enterpriseBasicInfo,that.enterpriseBasicInfo)&&Objects.equal(materialBasicInfo,that.materialBasicInfo);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,enterpriseBasicInfo,materialBasicInfo,materialNumber);
    }
}
