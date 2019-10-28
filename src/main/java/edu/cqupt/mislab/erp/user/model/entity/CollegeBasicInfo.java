package edu.cqupt.mislab.erp.user.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chuyunfei
 * @description 学院基本信息表
 * @date 20:51 2019/4/22
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class CollegeBasicInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Column(unique = true,nullable = false,updatable = false)
    @Comment(comment = "学院名称，唯一")
    private String college;
}
