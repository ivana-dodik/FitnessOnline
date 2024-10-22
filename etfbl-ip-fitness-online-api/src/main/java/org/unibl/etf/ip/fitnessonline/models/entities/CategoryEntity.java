package org.unibl.etf.ip.fitnessonline.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "parent_category_id", nullable = true)
    private Integer parentCategoryId;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "deleted", nullable = true)
    private Boolean deleted;

}
