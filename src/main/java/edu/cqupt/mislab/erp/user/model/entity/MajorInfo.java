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

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(referencedColumnName = "id",unique = true,insertable = false,updatable = false)
    private CollegeInfo college;

    @Column(unique = true,nullable = false)
    private String major;
}
