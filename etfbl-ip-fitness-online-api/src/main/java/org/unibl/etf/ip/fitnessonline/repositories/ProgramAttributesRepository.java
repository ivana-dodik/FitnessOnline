package org.unibl.etf.ip.fitnessonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.fitnessonline.models.entities.ProgramAttributeEntity;

import java.util.List;

public interface ProgramAttributesRepository extends JpaRepository<ProgramAttributeEntity, Integer> {
    List<ProgramAttributeEntity> findByProgramId(Integer programId);
}
