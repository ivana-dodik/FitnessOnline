package org.unibl.etf.ip.fitnessonline.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.fitnessonline.models.dto.*;
import org.unibl.etf.ip.fitnessonline.models.entities.ProgramEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.services.AuthenticationService;
import org.unibl.etf.ip.fitnessonline.services.ProgramsService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/programs")
@CrossOrigin(origins = "*")
public class ProgramsController {
    private final ProgramsService programsService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<List<ProgramEntity>> getAllPrograms() {
        return ResponseEntity.ok(programsService.getAll());
    }

    @GetMapping("/available")
    public ResponseEntity<List<ProgramEntity>> getAllAvailableProgramss() {
        return ResponseEntity.ok(programsService.getAllAvailable());
    }

    //zavrseni
    @GetMapping("/finished")
    public ResponseEntity<List<ProgramEntity>> getFinishedForUser(@RequestHeader(value = "X-Auth-Username", required = true) String xAuthUsername,
                                                                  @RequestHeader(value = "X-Auth-Password", required = true) String xAuthPassword) {
        UserEntity userEntity = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        return ResponseEntity.ok(programsService.getAllCompletedByUserId(userEntity.getId()));
    }

    //oni koje je korisnik kreirao
    @GetMapping("/instructing")
    public ResponseEntity<List<ProgramEntity>> getCurrentlyActiveForUser(@RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                                         @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity userEntity = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        return ResponseEntity.ok(programsService.getAllActiveByUserId(userEntity.getId()));
    }

    //oni u kojima je korisnik ucestvovao
    @GetMapping("/participated")
    public ResponseEntity<List<ProgramEntity>> getParticipatedForUser(@RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                                      @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity userEntity = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        return ResponseEntity.ok(programsService.getAllParticipatedByUserId(userEntity.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramDetailsDto> getProgramById(@PathVariable Integer id) {
        return ResponseEntity.ok(programsService.getProgramByIdWithAttributes(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id,
                                           @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                           @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity userEntity = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        programsService.deleteById(userEntity, id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByProgramId(@PathVariable Integer id) {
        return ResponseEntity.ok(programsService.getCommentsByProgramId(id));
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<List<String>> getImagesByProgramId(@PathVariable Integer id) {
        return ResponseEntity.ok(programsService.getImagesByProgramId(id));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> addCommentByProgramId(@PathVariable Integer id,
                                                                  @RequestBody AddCommentDto addCommentDto,
                                                                  @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                                  @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity userEntity = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        return ResponseEntity.ok(programsService.addCommentByProgramId(id, addCommentDto, userEntity));
    }

    @PostMapping
    public ResponseEntity<ProgramEntity> addProgram(@RequestBody @Valid AddProgramDto addProgramDto,
                                                    @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                    @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity userEntity = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        return ResponseEntity.ok(programsService.create(userEntity, addProgramDto));
    }

    @PatchMapping
    public ResponseEntity<?> closeProgram(@RequestBody ProgramEntity editProgram) {
        try {
            programsService.closeProgram(editProgram.getId());
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while closing the program.");
        }
    }

}