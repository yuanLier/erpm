package edu.cqupt.mislab.erp.user.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chuyunfei
 * @description 教师基本信息表
 * @date 20:26 2019/4/22
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class UserTeacherInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //todo 增加教师端功能
}
