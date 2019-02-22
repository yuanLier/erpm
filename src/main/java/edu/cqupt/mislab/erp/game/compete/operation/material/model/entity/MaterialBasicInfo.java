package edu.cqupt.mislab.erp.game.compete.operation.material.model.entity;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @Column(unique = true,nullable = false,updatable = false)
    private String materialName;//原料的名称

    @Column(nullable = false,updatable = false)
    private Double materialPrice;//原料的价格

    @Column(nullable = false,updatable = false)
    private Integer materialDelayTime;//指原料从采购开始到原料运到仓库，需要等待的周期数

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;//时间戳

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        MaterialBasicInfo that = (MaterialBasicInfo) o;

        return new EqualsBuilder().append(id,that.id).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).toHashCode();
    }
}
