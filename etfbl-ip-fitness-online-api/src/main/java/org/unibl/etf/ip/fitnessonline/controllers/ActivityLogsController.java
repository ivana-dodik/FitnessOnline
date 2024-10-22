package org.unibl.etf.ip.fitnessonline.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.fitnessonline.models.dto.ActivityLogDto;
import org.unibl.etf.ip.fitnessonline.models.dto.AddActivityLogDto;
import org.unibl.etf.ip.fitnessonline.models.entities.ActivityLogEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.services.ActivityLogsService;
import org.unibl.etf.ip.fitnessonline.services.AuthenticationService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/activity-logs")
@CrossOrigin(origins = "*")
public class ActivityLogsController {
    private final ActivityLogsService activityLogsService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<ActivityLogEntity> addNewActivityLog(@RequestBody @Valid AddActivityLogDto addActivityLogDto,
                                                               @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                               @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity userEntity = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        return ResponseEntity.ok(activityLogsService.addActivityLog(addActivityLogDto, userEntity));
    }

    @GetMapping()
    public ResponseEntity<List<ActivityLogDto>> getActivityLogsByUserId(@RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                                        @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity userEntity = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        List<ActivityLogDto> activityLogs = activityLogsService.getActivityLogsByUserId(userEntity.getId());
        return ResponseEntity.ok(activityLogs);
    }

    @GetMapping("user/weight")
    public ResponseEntity<?> getWeightOverTime(@RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                               @RequestHeader(value = "X-Auth-Password") String xAuthPassword,
                                               @RequestParam(required = false) String before,
                                               @RequestParam(required = false) String after) {
        //System.out.println("BLAAAAAAA");
        UserEntity userEntity = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        //System.out.println(before + "   " + after);
        Map<LocalDate, Double> weightOverTime = activityLogsService.getActivityLogsByUserIdBetweenDates(userEntity.getId(), before, after);
        //System.out.println("aaaaaaaaaaaa");
        return ResponseEntity.ok(weightOverTime);
    }

}
