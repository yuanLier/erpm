package edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity;


import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class IsoDevelopInfo implements Serializable {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/4 20:35
     * @Description: 比赛运行过程中用于存储某一个企业的具体ISO认证信息的数据表，该数据表在比赛初始化时通过IsoBasicInfo生成
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "ISO认证的基本信息，该数据对于基本数据表里面的一条最新或历史信息")
    private IsoBasicInfo isoBasicInfo;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个企业的ISO认证信息，每一个企业有一或多条认证信息")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @Basic
    @Comment(comment = "认证开始的周期，这个值在未开发ISO认证信息状态时为null，默认为已研发的值为1")
    private Integer developBeginPeriod ;

    @Basic
    @Comment(comment = "认证完成的周期，这个值在未开发/开发中ISO认证信息状态时为null，默认为已研发的值为1")
    private Integer developEndPeriod ;

    @Basic
    @Comment(comment = "已经认证了多少个周期，这个值在未开发ISO认证信息状态时为null，默认为已研发的值为0")
    private Integer researchedPeriod ;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Comment(comment = "当前认证状态（默认同iso基本认证中的初始认证状态，需要手动的在比赛初始化时进行转换）")
    private IsoStatusEnum isoStatus;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        IsoDevelopInfo that = (IsoDevelopInfo) o;
        return Objects.equal(id,that.id)&&Objects.equal(developBeginPeriod,that.developBeginPeriod)&&Objects.equal(developEndPeriod,that.developEndPeriod)&&Objects.equal(researchedPeriod,that.researchedPeriod)&&isoStatus == that.isoStatus;
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,developBeginPeriod,developEndPeriod,researchedPeriod,isoStatus);
    }
}
