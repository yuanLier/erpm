package edu.cqupt.mislab.erp.game.manage.model.entity;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @Basic(optional = false)
    private String gameName;//比赛的名称，比赛名称可以重复

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(updatable = false,nullable = false)
    private UserStudentInfo gameCreator;//比赛的创建者，拥有可以开始比赛的权利

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(updatable = false,nullable = false)
    private GameInitInfo gameInitInfo;

    @Column(nullable = false,updatable = false)
    private Integer gameMaxEnterpriseNumber;//这场比赛允许参加的最大企业数目，用于限制企业创建

    @Basic(optional = false)
    private Integer gameCurrentYear;//当前游戏处于哪一个年，用于比赛数据的推进

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    private GameStatus gameStatus;//比赛的状态

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date gameCreateTime;//创建这个比赛的时间，可以用来进行比赛管理信息排序

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "gameInfo")
    private Set<EnterpriseBasicInfo> enterpriseBasicInfos = new HashSet<>();//比赛里面的企业

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

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "基本VO对象")
    public static class GameBasicInfoVo {

        @ApiModelProperty("代理主键")
        Long id;

        @ApiModelProperty("比赛的名称，比赛名称可以重复")
        String gameName;

        @ApiModelProperty("比赛的创建者，拥有可以开始比赛的权利")
        Long gameCreatorId;

        @ApiModelProperty("创建者姓名")
        String gameCreatorName;

        @ApiModelProperty("这场比赛允许参加的最大企业数目，用于限制企业创建")
        Integer gameMaxEnterpriseNumber;

        @ApiModelProperty("未开始时是已经参加的企业个数，开始后是真正参加的企业个数")
        Integer gameEnterpriseActualNumber;

        @ApiModelProperty("一场比赛中还存活的企业个数，用于订单处理")
        Integer gameEnterpriseAliveNumber;

        @ApiModelProperty("比赛最多需要进行的年数，默认为六年")
        Integer gameYears;

        @ApiModelProperty("每一年的周期数量，默认为4个周期")
        Integer gamePeriodsOfYear;

        @ApiModelProperty("当前游戏处于哪一个年，用于比赛数据的推进")
        Integer gameCurrentYear;

        @ApiModelProperty("比赛的状态：0标识创建状态，1标识正在初始化状态，2标识初始化完成并正常开始运营状态，3标识完结状态，所有的企业都已经破产或者推进完所有的周期")
        Integer gameStatus;

        @ApiModelProperty("创建这个比赛的时间，可以用来进行比赛管理信息排序")
        Date gameCreateTime;
    }
}