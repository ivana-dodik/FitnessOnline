package org.unibl.etf.ip.fitnessonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.fitnessonline.models.entities.CommentEntity;

import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> getAllByProgramId(Integer programId);
}
