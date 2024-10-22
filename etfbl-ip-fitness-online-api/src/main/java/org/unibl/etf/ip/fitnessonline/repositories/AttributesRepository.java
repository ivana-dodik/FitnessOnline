package org.unibl.etf.ip.fitnessonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.fitnessonline.models.entities.AttributeEntity;

import java.util.List;

public interface AttributesRepository extends JpaRepository<AttributeEntity, Integer> {
    List<AttributeEntity> getAllByCategoryIdAndDeletedFalse(Integer categoryId);
}
