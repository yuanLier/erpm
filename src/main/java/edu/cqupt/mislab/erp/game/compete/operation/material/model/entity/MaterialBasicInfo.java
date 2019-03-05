package edu.cqupt.mislab.erp.game.compete.operation.material.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class MaterialBasicInfo {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/4 21:33
     * @Description: 产品原材料的基本信息数据表，当当前数据表无可用数据时将无法进行比赛初始化
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "原料的名称，所有相同原料名称数据中，enable=false标识历史数据，反之为最新数据，每一个name最多一个enable=true")
    private String materialName;

    @Column(nullable = false,updatable = false,columnDefinition = "double(10,2) default 1.00")
    @Comment(comment = "原料的价格,该值必须大于0")
    private double materialPrice;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "指原料从采购开始到原料运到仓库，需要等待的周期数，该值必须大于0")
    private int materialDelayTime;

    @Basic(optional = false)
    @Comment(comment = "该数据是否被启用，当前最新数据是启用，所有的历史数据均为未启用，必须保证同一个材料信息最多只有一个Enable = true")
    private boolean enable;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        MaterialBasicInfo that = (MaterialBasicInfo) o;
        return Double.compare(that.materialPrice,materialPrice) == 0&&enable == that.enable&&Objects.equal(id,that.id)&&Objects.equal(materialName,that.materialName)&&Objects.equal(materialDelayTime,that.materialDelayTime);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,materialName,materialPrice,materialDelayTime,enable);
    }
}
