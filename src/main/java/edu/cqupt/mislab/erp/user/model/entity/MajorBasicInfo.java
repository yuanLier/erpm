package edu.cqupt.mislab.erp.user.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chuyunfei
 * @description 专业基本信息表
 * @date 20:41 2019/4/22
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class MajorBasicInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false)
    @Comment(comment = "学院，一个专业的学院是不可以改变的，也是非空的")
    private CollegeBasicInfo college;

    @Column(unique = true,nullable = false)
    @Comment(comment = "专业名称是唯一的")
    private String major;
}
