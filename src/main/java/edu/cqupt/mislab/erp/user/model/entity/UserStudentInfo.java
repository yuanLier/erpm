package edu.cqupt.mislab.erp.user.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chuyunfei
 * @description 用户基本信息表
 * @date 20:44 2019/4/22
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class UserStudentInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Column(unique = true,nullable = false,updatable = false)
    @Comment(comment = "学号，作为系统账号")
    private String studentAccount;

    @Basic(optional = false)
    @Comment(comment = "账号密码")
    private String studentPassword;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "真实姓名")
    private String studentName;

    @Basic(optional = true)
    @Enumerated(EnumType.STRING)
    @Comment(comment = "性别")
    private UserGenderEnum gender;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    @Comment(comment = "专业信息")
    private MajorBasicInfo majorBasicInfo;

    @Column(nullable = false)
    @Comment(comment = "班级")
    private String studentClass;

    @Comment(comment = "电子邮箱")
    private String email;

    @Column(nullable = false)
    @Comment(comment = "电话")
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    @Comment(comment = "头像位置信息")
    private UserAvatarInfo userAvatarInfo;

    @Basic(optional = false)
    @Comment(comment = "账户是否可用")
    private boolean accountEnable;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(updatable = false)
    @Comment(comment = "教师信息")
    private UserTeacherInfo userTeacherInfo;
}