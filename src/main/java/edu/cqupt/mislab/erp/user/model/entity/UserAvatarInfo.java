package edu.cqupt.mislab.erp.user.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chuyunfei
 * @description 用户头像信息表
 * @date 20:43 2019/4/22
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class UserAvatarInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Column(unique = true,nullable = false,updatable = false)
    @Comment(comment = "头像位置信息")
    private String avatarLocation;
}