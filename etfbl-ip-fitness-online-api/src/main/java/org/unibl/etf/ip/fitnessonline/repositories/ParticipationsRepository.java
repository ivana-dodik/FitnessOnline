package org.unibl.etf.ip.fitnessonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.fitnessonline.models.entities.ParticipationEntity;

import java.util.List;

public interface ParticipationsRepository extends JpaRepository<ParticipationEntity, Integer> {
    List<ParticipationEntity> getAllByUserId(Integer id);

    boolean existsByUserIdAndProgramId(Integer userId, Integer programId);
}