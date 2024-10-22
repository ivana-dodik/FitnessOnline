package org.unibl.etf.ip.fitnessonline.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.mapper.ActivityLogMapper;
import org.unibl.etf.ip.fitnessonline.models.dto.ActivityLogDto;
import org.unibl.etf.ip.fitnessonline.models.dto.AddActivityLogDto;
import org.unibl.etf.ip.fitnessonline.models.entities.ActivityLogEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.repositories.ActivityLogsRepository;
import org.unibl.etf.ip.fitnessonline.repositories.UsersRepository;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActivityLogsService {
    private final ActivityLogsRepository activityLogsRepository;
    private final UsersRepository usersRepository;

    public ActivityLogEntity addActivityLog(AddActivityLogDto addActivityLogDto, UserEntity userEntity) {
        ModelMapper modelMapper = ActivityLogMapper.configureAddActivityLogDtoToActivityLogEntityMapper();
        ActivityLogEntity activityLogEntity = modelMapper.map(addActivityLogDto, ActivityLogEntity.class);
        activityLogEntity.setId(null);
        activityLogEntity.setUserId(userEntity.getId());
        return activityLogsRepository.saveAndFlush(activityLogEntity);
    }

    public Map<LocalDate, Double> calculateAverageResultsByDay(List<ActivityLogEntity> activityLogEntities) {
        return activityLogEntities.stream()
                .collect(Collectors.groupingBy(
                        entity -> entity.getLogDate().toLocalDateTime().toLocalDate(),
                        Collectors.averagingDouble(ActivityLogEntity::getResults)
                ));
    }

    public List<ActivityLogDto> getActivityLogsByUserId(Integer userId) {
        List<ActivityLogEntity> activityLogEntities = activityLogsRepository.findByUserId(userId);

        Optional<UserEntity> userEntityOptional = usersRepository.findById(userId);

        if (userEntityOptional.isPresent()) {
            var user = userEntityOptional.get();

            return activityLogEntities.stream()
                    .map(ale -> new ActivityLogDto(
                            user.getUsername(),
                            ale.getLogDate().toString(),
                            ale.getExerciseType(),
                            ale.getDurationMinutes(),
                            ale.getIntensity(),
                            ale.getResults().toString()
                    ))
                    .toList();
        } else {
            return List.of();
        }
    }

    public static String convertToTimestampString(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");

        LocalDate localDate = LocalDate.parse(date, inputFormatter);
        return localDate.format(outputFormatter);
    }

    public Map<LocalDate, Double> getActivityLogsByUserIdBetweenDates(Integer userId, String before, String after) {
        List<ActivityLogEntity> activityLogEntities;
        if (before != null && after != null) {
            activityLogEntities = activityLogsRepository.findByUserIdAndLogDateBetween(userId, Timestamp.valueOf(convertToTimestampString(before)), Timestamp.valueOf(convertToTimestampString(after)));
        } else if (before == null && after != null) {
            activityLogEntities = activityLogsRepository.findByUserIdAndLogDateAfter(userId, Timestamp.valueOf(convertToTimestampString(after)));
        } else if (before != null && after == null) {
            activityLogEntities = activityLogsRepository.findByUserIdAndLogDateBefore(userId, Timestamp.valueOf(convertToTimestampString(before)));
        } else {
            activityLogEntities = activityLogsRepository.findByUserId(userId);
        }

        if (usersRepository.existsById(userId)) {
            return calculateAverageResultsByDay(activityLogEntities);
        } else {
            return Map.of();
        }
    }

}
