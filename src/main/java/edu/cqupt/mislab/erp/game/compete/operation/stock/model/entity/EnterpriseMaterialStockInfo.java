package edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class EnterpriseMaterialStockInfo {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/5 11:45
     * @Description: 用于存储企业的历史原料信息
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个企业")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一种原料")
    private MaterialBasicInfo materialBasicInfo;

    @Basic(optional = false)
    @Comment(comment = "材料的数量，这个数值大于等于0")
    private int materialNumber;

    @Basic(optional = false)
    @Comment(comment = "哪一个周期的数据")
    private int period;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        EnterpriseMaterialStockInfo that = (EnterpriseMaterialStockInfo) o;
        return materialNumber == that.materialNumber&&period == that.period&&Objects.equal(id,that.id)&&Objects.equal(enterpriseBasicInfo,that.enterpriseBasicInfo)&&Objects.equal(materialBasicInfo,that.materialBasicInfo);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,enterpriseBasicInfo,materialBasicInfo,materialNumber,period);
    }
}
