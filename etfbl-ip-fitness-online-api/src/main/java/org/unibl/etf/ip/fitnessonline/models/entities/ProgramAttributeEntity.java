package org.unibl.etf.ip.fitnessonline.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "program_attribute")
public class ProgramAttributeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "program_id", nullable = false)
    private Integer programId;

    @Column(name = "attribute_id", nullable = false)
    private Integer attributeId;

    @Column(name = "value", nullable = false, length = 100)
    private String value;

}
