package org.unibl.etf.ip.fitnessonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.fitnessonline.models.entities.CategoryEntity;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<CategoryEntity, Integer> {
    List<CategoryEntity> findAllByDeletedFalse();
}
