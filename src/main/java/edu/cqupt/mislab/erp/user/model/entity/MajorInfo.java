package edu.cqupt.mislab.erp.user.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class MajorInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private CollegeInfo college;//学院信息

    @Column(unique = true,nullable = false)
    private String major;//专业名称
}
