package edu.cqupt.mislab.erp.game.compete.operation.order.model.entity;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class GameOrderChooseInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private EnterpriseAdInfo concurrentEnterprise;//那一个广告的选择顺序

    @Basic(optional = false)
    private Integer frequency;//该比赛已经选择已经多少轮

    @Basic(optional = false)
    private Boolean finished;//当前的订单是否选取完毕

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        GameOrderChooseInfo that = (GameOrderChooseInfo) o;

        return new EqualsBuilder().append(id,that.id).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).toHashCode();
    }
}