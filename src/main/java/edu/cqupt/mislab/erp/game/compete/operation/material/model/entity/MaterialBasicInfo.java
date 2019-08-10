package edu.cqupt.mislab.erp.game.compete.operation.material.model.entity;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMax;
import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/4 21:33
 * @Description: 产品原材料的基本信息数据表，当当前数据表无可用数据时将无法进行比赛初始化
 **/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class MaterialBasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @NotNull
    @Column(nullable = false,updatable = false)
    @Comment(comment = "原料的名称，所有相同原料名称数据中，enable=false标识历史数据，反之为最新数据，每一个name最多一个enable=true")
    private String materialName;

    @DoubleMin(0.01)
    @Column(nullable = false,updatable = false,columnDefinition = "double(10,2) default 1.00")
    @Comment(comment = "原料的购买价格,该值必须大于0")
    private double materialPrice;

    @DoubleMin(0.01)
    @DoubleMax(1.00)
    @Column(nullable = false,updatable = false,columnDefinition = "double(10,2) default 0.85")
    @Comment(comment = "原料的售卖价格占购买价格的比例，即购买价格*sellRate=售卖价格")
    private double sellRate;

    @Min(1)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "指原料从采购开始到原料运到仓库，需要等待的周期数，该值必须大于0")
    private int materialDelayTime;

    @NotNull
    @Basic(optional = false)
    @Comment(comment = "该数据是否被启用，当前最新数据是启用，所有的历史数据均为未启用，必须保证同一个材料信息最多只有一个Enable = true")
    private boolean enable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialBasicInfo that = (MaterialBasicInfo) o;
        return Double.compare(that.materialPrice, materialPrice) == 0 &&
                Double.compare(that.sellRate, sellRate) == 0 &&
                materialDelayTime == that.materialDelayTime &&
                enable == that.enable &&
                Objects.equals(id, that.id) &&
                Objects.equals(materialName, that.materialName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, materialName, materialPrice, sellRate, materialDelayTime, enable);
    }
}
