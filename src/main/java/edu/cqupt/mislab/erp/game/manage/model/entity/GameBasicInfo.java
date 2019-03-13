package edu.cqupt.mislab.erp.game.manage.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.Min;

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
    @Comment(comment = "比赛的名称")
    private String gameName;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(updatable = false,nullable = false)
    @Comment(comment = "比赛的创建者，拥有可以开始比赛、删除的权利")
    private UserStudentInfo userStudentInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(updatable = false,nullable = false)
    @Comment(comment = "该比赛的比赛基本数据初始化信息")
    private GameInitBasicInfo gameInitBasicInfo;

    @Min(1)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "这场比赛允许参加的最大企业数目，用于限制企业创建")
    private Integer gameMaxEnterpriseNumber;

    @Min(1)
    @Basic(optional = false)
    @Comment(comment = "当前游戏处于哪一个年，用于比赛数据的推进")
    private Integer gameCurrentYear;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Comment(comment = "比赛的状态")
    private GameStatus gameStatus;

    @Column(nullable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Comment(comment = "创建这个比赛的时间")
    private Date gameCreateTime;

    @OneToMany(mappedBy = "gameBasicInfo",cascade = CascadeType.ALL)
    @Comment(comment = "该场比赛的所有企业数据信息")
    private List<EnterpriseBasicInfo> enterpriseBasicInfos = new ArrayList<>();

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        GameBasicInfo that = (GameBasicInfo) o;
        return Objects.equal(id,that.id)&&Objects.equal(gameName,that.gameName)&&Objects.equal(gameMaxEnterpriseNumber,that.gameMaxEnterpriseNumber)&&Objects.equal(gameCurrentYear,that.gameCurrentYear)&&gameStatus == that.gameStatus&&Objects.equal(gameCreateTime,that.gameCreateTime);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,gameName,gameMaxEnterpriseNumber,gameCurrentYear,gameStatus,gameCreateTime);
    }
}