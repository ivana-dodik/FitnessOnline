package org.unibl.etf.ip.fitnessonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.fitnessonline.models.entities.ActivityLogEntity;

import java.sql.Timestamp;
import java.util.List;

public interface ActivityLogsRepository extends JpaRepository<ActivityLogEntity, Integer> {
    List<ActivityLogEntity> findByUserId(Integer userId);
    List<ActivityLogEntity> findByUserIdAndLogDateBetween(Integer userId, Timestamp before, Timestamp after);
    List<ActivityLogEntity> findByUserIdAndLogDateBefore(Integer userId, Timestamp before);
    List<ActivityLogEntity> findByUserIdAndLogDateAfter(Integer userId, Timestamp before);
}
