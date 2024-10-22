package org.unibl.etf.ip.fitnessonline.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.models.entities.LogEntity;
import org.unibl.etf.ip.fitnessonline.repositories.LogsRepository;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@AllArgsConstructor
public class LogsService {
    private final LogsRepository logsRepository;

    public void addLog(String message) {
        LogEntity logEntity = new LogEntity();
        logEntity.setId(null);
        logEntity.setDateTime(Timestamp.from(Instant.now()));
        logEntity.setContent(message);

        logsRepository.saveAndFlush(logEntity);
    }

}
