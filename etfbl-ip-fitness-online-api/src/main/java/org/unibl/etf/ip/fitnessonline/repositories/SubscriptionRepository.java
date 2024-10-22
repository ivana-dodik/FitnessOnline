package org.unibl.etf.ip.fitnessonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.ip.fitnessonline.models.entities.SubscriptionEntity;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {
    void deleteByUserIdAndCategoryId(Integer userId, Integer categoryId);

    @Query("select distinct userId from SubscriptionEntity")
    List<Integer> findUserId();

    List<SubscriptionEntity> findByUserId(Integer userId);

    List<Integer> findCategoryIdsByUserId(Integer userId);

    List<SubscriptionEntity> findAllByUserId(Integer userId);
}
