package edu.cqupt.mislab.erp.game.manage.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Min;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class GameInitBasicInfo implements Serializable {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/5 19:02
     * @Description: 比赛的基本初始化话信息
     **/
    
    /* 
     * @Author: chuyunfei
     * @Date: 2019/3/12 17:45
     * @Description: 比赛的基本初始化数据，参考：doc/业务逻辑要求列表文件.md:1
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Min(1)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "一场比赛持续年数")
    private Integer totalYear;

    @Min(1)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "一年的周期数")
    private Integer periodOfOneYear;

    @Min(1)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "一个企业所允许的最多成员个数")
    private Integer maxEnterpriseMemberNumber;

    @Min(1)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "每一场比赛所允许的最大企业个数")
    private Integer maxEnterpriseNumber;

    @Basic(optional = false)
    @Comment(comment = "该数据是否被启用，业务逻辑必须保证只有一条数据被启用，该值可以修改")
    private Boolean settingEnable;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "用于记录该条设置被添加或迭代修改的时间")
    @Temporal(TemporalType.TIMESTAMP)
    private Date settingCreateTimeStamp;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        GameInitBasicInfo that = (GameInitBasicInfo) o;
        return Objects.equal(id,that.id)&&Objects.equal(totalYear,that.totalYear)&&Objects.equal(periodOfOneYear,that.periodOfOneYear)&&Objects.equal(maxEnterpriseMemberNumber,that.maxEnterpriseMemberNumber)&&Objects.equal(maxEnterpriseNumber,that.maxEnterpriseNumber)&&Objects.equal(settingEnable,that.settingEnable)&&Objects.equal(settingCreateTimeStamp,that.settingCreateTimeStamp);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,totalYear,periodOfOneYear,maxEnterpriseMemberNumber,maxEnterpriseNumber,settingEnable,settingCreateTimeStamp);
    }
}