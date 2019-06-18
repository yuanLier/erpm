package edu.cqupt.mislab.erp.game.compete.operation.order.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/5 13:13
 * @Description: 用于记录每一场比赛的订单当前选择的信息
 **/

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
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "那一个比赛")
    private GameBasicInfo gameBasicInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    @Comment(comment = "那一个广告的选择顺序")
    private EnterpriseAdInfo concurrentEnterprise;

    @Min(0)
    @Basic(optional = false)
    @Comment(comment = "该比赛已经选择已经多少轮")
    private Integer frequency;

    @Basic(optional = false)
    @Comment(comment = "当前的订单是否选取完毕")
    private Boolean finished;

    @Min(1)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "那一年的订单选择信息")
    private Integer year;

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