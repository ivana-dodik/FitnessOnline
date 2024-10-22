package org.unibl.etf.ip.fitnessonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.fitnessonline.models.entities.ProgramEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ProgramsRepository extends JpaRepository<ProgramEntity, Integer> {
    List<ProgramEntity> findAllByAvailableTrueAndDeletedFalseAndInstructorId(Integer id);

    List<ProgramEntity> getAllByInstructorIdAndAvailableFalseAndDeletedFalse(Integer id);

    Optional<ProgramEntity> findByIdAndAvailableTrue(Integer id);

    List<ProgramEntity> findAllByAvailableTrueAndDeletedFalse();

    List<ProgramEntity> findAllByDeletedFalse();

    List<ProgramEntity> findAllByDeletedFalseAndInstructorId(Integer id);

    List<ProgramEntity> getAllByIdIn(List<Integer> ids);

    List<ProgramEntity> getAllByInstructorIdAndDeletedFalse(Integer id);

    List<ProgramEntity> findByCategoryId(Integer categoryId);

    List<ProgramEntity> findByCategoryIdAndTimeCreatedAfter(Integer categoryId, Timestamp date);
}
