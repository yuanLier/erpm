package edu.cqupt.mislab.erp.user.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class UserStudentInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @Column(unique = true,nullable = false,updatable = false)
    private String studentAccount;//学号，作为系统账号

    @Basic(optional = false)
    private String studentPassword;//账号密码

    @Column(nullable = false,updatable = false)
    private String studentName;//真实姓名

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private UserGender gender;//性别

    @OneToOne
    @JoinColumn
    private MajorInfo majorInfo;//专业信息

    @Basic(optional = false)
    private String studentClass;//班级，教师班级

    private String email;//电子邮箱

    private String phone;//电话

    @ManyToOne
    @JoinColumn
    private UserAvatarInfo userAvatarInfo;//头像位置信息

    @Basic(optional = false)
    private boolean accountEnable;//账户是否可用

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private UserTeacherInfo userTeacherInfo;//教师信息
}