package edu.cqupt.mislab.erp.game.manage.model.entity;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @Basic(optional = false)
    private Integer totalYear;//一场比赛年数，默认6年

    @Basic(optional = false)
    private Integer period;//一年的周期数，默认4期

    @Basic(optional = false)
    private Integer maxMemberNumber;//每一个企业所允许的最多的成员个数，默认6个

    @Basic(optional = false)
    private Integer maxEnterpriseNumber;//每一场比赛所允许的最大的企业个数，默认20个

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    private Date timeStamp;//进行版本控制，记录该记录的更改时间
}