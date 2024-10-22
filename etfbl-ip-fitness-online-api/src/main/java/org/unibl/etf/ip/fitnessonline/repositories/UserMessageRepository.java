package org.unibl.etf.ip.fitnessonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.UserMessageEntity;

import java.util.List;

public interface UserMessageRepository extends JpaRepository<UserMessageEntity, Integer> {
    List<UserMessageEntity> findAllByReceiverId(Integer id);

    @Query("SELECT um FROM UserMessageEntity um " +
            "WHERE um.dateTime IN (" +
            "    SELECT MAX(um2.dateTime) " +
            "    FROM UserMessageEntity um2 " +
            "    WHERE um2.receiverId = :receiverId " +
            "    GROUP BY um2.senderId" +
            ") " +
            "AND um.receiverId = :receiverId")
    List<UserMessageEntity> findLatestMessages(@Param("receiverId") int receiverId);

    List<UserMessageEntity> findAllBySenderIdAndReceiverId(Integer senderId, Integer receiverId);
}
