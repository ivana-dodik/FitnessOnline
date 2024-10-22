package org.unibl.etf.ip.fitnessonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.fitnessonline.models.entities.AdvisorAccountEntity;

public interface AdvisorsAccountsRepository extends JpaRepository<AdvisorAccountEntity, Integer> {
}
