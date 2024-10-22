package org.unibl.etf.ip.fitnessonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.fitnessonline.models.entities.ImageEntity;

import java.util.List;

public interface ImagesRepository extends JpaRepository<ImageEntity, Integer> {
    List<ImageEntity> findAllByProgramId(Integer id);
}
