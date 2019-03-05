package edu.cqupt.mislab.erp.game.manage.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class GameInitInfo implements Serializable {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/5 19:02
     * @Description: 比赛的基本初始化话信息
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "一场比赛年数，默认6年，设置必须大于1年才行")
    private int totalYear;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "一年的周期数，默认4期")
    private int period;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "每一个企业所允许的最多的成员个数，默认6个")
    private int maxMemberNumber;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "每一场比赛所允许的最大的企业个数，默认20个")
    private int maxEnterpriseNumber;

    @Basic(optional = false)
    @Comment(comment = "该数据是否被启用，当前最新数据是启用，所有的历史数据均为未启用，必须保证同一个比赛初始化信息最多只有一个Enable = true")
    private boolean enable;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        GameInitInfo that = (GameInitInfo) o;
        return totalYear == that.totalYear&&period == that.period&&maxMemberNumber == that.maxMemberNumber&&maxEnterpriseNumber == that.maxEnterpriseNumber&&enable == that.enable&&Objects.equal(id,that.id);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,totalYear,period,maxMemberNumber,maxEnterpriseNumber,enable);
    }
}