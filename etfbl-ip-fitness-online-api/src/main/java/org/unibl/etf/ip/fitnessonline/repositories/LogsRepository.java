package org.unibl.etf.ip.fitnessonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.fitnessonline.models.entities.LogEntity;

public interface LogsRepository extends JpaRepository<LogEntity, Integer> {
}
