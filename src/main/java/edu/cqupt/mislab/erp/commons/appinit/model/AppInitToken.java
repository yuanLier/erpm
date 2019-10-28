package edu.cqupt.mislab.erp.commons.appinit.model;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author： chuyunfei
 * @date：2019/3/13
 */
@Data
@Entity
@Table
public class AppInitToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Basic(optional = false)
    private String token;
}
