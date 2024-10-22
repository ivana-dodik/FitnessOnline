package org.unibl.etf.ip.fitnessonline.controllers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.fitnessonline.models.dto.ImageUrlDto;
import org.unibl.etf.ip.fitnessonline.services.ImagesService;
import org.unibl.etf.ip.fitnessonline.services.ResourcesService;
import org.unibl.etf.ip.fitnessonline.services.UsersService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/resources")
@CrossOrigin(origins = "*")
public class ResourcesController {

    private final ResourcesService resourcesService;

    private final ImagesService imagesService;

    private final UsersService usersService;

    @PostMapping(value = "/programs/{id}")
    @Transactional
    public ResponseEntity<Void> handleProgramImageUpload(@RequestParam("files") List<MultipartFile> files,
                                                         @PathVariable Integer id) {
        for (MultipartFile multipartFile : files) {
            resourcesService.saveProgramImage(multipartFile, id);
        }

        return ResponseEntity.ok(null);
    }

    @PostMapping(value = "/users/{id}")
    @Transactional
    public ResponseEntity<ImageUrlDto> handleUserAvatarUpload(@RequestParam("files") MultipartFile file,
                                                              @PathVariable Integer id) {

        return ResponseEntity.ok(resourcesService.saveUserAvatar(file, id));
    }

    @GetMapping("/programs/{id}/thumbnail")
    public ResponseEntity<ImageUrlDto> getThumbnailForProgram(@PathVariable Integer id) {
        return ResponseEntity.ok(imagesService.getThumbnailOrDefaultForProgramId(id));
    }

    @GetMapping("/users/{id}/avatar")
    public ResponseEntity<ImageUrlDto> getAvatarForUser(@PathVariable Integer id) {
        return ResponseEntity.ok(usersService.getAvatarForUser(id));
    }
}
