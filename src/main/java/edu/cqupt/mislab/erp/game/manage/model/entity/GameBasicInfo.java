package edu.cqupt.mislab.erp.game.manage.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class GameBasicInfo implements Serializable {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/5 19:08
     * @Description: 比赛的基本数据信息表
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "比赛的名称，比赛名称可以重复")
    private String gameName;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(updatable = false,nullable = false)
    @Comment(comment = "比赛的创建者，拥有可以开始比赛的权利")
    private UserStudentInfo gameCreator;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(updatable = false,nullable = false)
    @Comment(comment = "该比赛的比赛基本数据初始化信息")
    private GameInitInfo gameInitInfo;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "这场比赛允许参加的最大企业数目，用于限制企业创建")
    private Integer gameMaxEnterpriseNumber;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "当前游戏处于哪一个年，用于比赛数据的推进")
    private Integer gameCurrentYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment(comment = "比赛的状态")
    private GameStatus gameStatus;

    @Column(nullable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Comment(comment = "创建这个比赛的时间，可以用来进行比赛管理信息排序")
    private Date gameCreateTime;

    @OneToMany(mappedBy = "gameInfo",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<EnterpriseBasicInfo> enterpriseBasicInfos;//比赛企业

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        GameBasicInfo that = (GameBasicInfo) o;

        return new EqualsBuilder().append(id,that.id).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).toHashCode();
    }
}