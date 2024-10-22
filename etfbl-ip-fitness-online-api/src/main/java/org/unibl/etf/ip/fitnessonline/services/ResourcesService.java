package org.unibl.etf.ip.fitnessonline.services;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.fitnessonline.models.dto.ImageUrlDto;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class ResourcesService {

    private static final String ROOT_DIR = new JFileChooser().getFileSystemView().getDefaultDirectory().toString()
            + File.separatorChar + "FitnessOnlineResources" + File.separatorChar;
    private static final String IMAGES_DIR = ROOT_DIR + "images" + File.separatorChar;
    private static final String PROGRAMS_DIR = IMAGES_DIR + "programs" + File.separatorChar;
    private static final String USERS_DIR = IMAGES_DIR + "users" + File.separatorChar;

    static {
        Path path = Paths.get(ROOT_DIR);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println("Error while creating ROOT dir.");
            }
        }

        Path imagesDir = Paths.get(IMAGES_DIR);
        if (!Files.exists(imagesDir)) {
            try {
                Files.createDirectory(imagesDir);
            } catch (IOException e) {
                System.out.println("Error while creating IMAGES dir.");
            }
        }

        try {
            Path noAvatarFilePath = Paths.get(IMAGES_DIR + "no-avatar.png");
            Resource resource = new ClassPathResource("static/no-avatar.png");
            File noAvatarFile = resource.getFile();
            if (Files.exists(noAvatarFilePath)) {
                Files.delete(noAvatarFilePath);
            }
            InputStream fis = new FileInputStream(noAvatarFile);
            Files.copy(fis, noAvatarFilePath);
        } catch (IOException e) {
            System.out.println("Error while saving NO-AVATAR image");
        }

        try {
            Path noImagesFilePath = Paths.get(IMAGES_DIR + "no-images.png");
            Resource resource = new ClassPathResource("static/no-images.png");
            File noImagesFile = resource.getFile();
            if (Files.exists(noImagesFilePath)) {
                Files.delete(noImagesFilePath);
            }
            InputStream fis = new FileInputStream(noImagesFile);
            Files.copy(fis, noImagesFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while saving NO-IMAGES image");
        }

        Path programsPath = Paths.get(PROGRAMS_DIR);
        if (!Files.exists(programsPath)) {
            try {
                Files.createDirectory(programsPath);
            } catch (IOException e) {
                System.out.println("Error while creating PROGRAMS dir.");
            }
        }

        Path usersPath = Paths.get(USERS_DIR);
        if (!Files.exists(usersPath)) {
            try {
                Files.createDirectory(usersPath);
            } catch (IOException e) {
                System.out.println("Error while creating PROGRAMS dir.");
            }
        }
    }

    private final ImagesService imagesService;

    private final UsersService usersService;

    private final LogsService logsService;

    public void saveProgramImage(MultipartFile file, Integer id) {
        try {
            imagesService.addProgramImage(file.getOriginalFilename(), id);
            Path path = Paths.get(PROGRAMS_DIR + id);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            Path filePath = Paths.get(PROGRAMS_DIR + id + File.separatorChar + file.getOriginalFilename());
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

            Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()));
            logsService.addLog("Saved file: " + path.toString());

        } catch (Exception e) {
            logsService.addLog("Error while saving file: " + e.getMessage());
        }
    }

    public ImageUrlDto saveUserAvatar(MultipartFile file, Integer id) {
        try {
            Path path = Paths.get(USERS_DIR + id);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            Path filePath = Paths.get(USERS_DIR + id + File.separatorChar + file.getOriginalFilename());
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

            Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()));
            UserEntity userEntity = usersService.getById(id);
            String avatarUrl = "/images/users/" + id + "/" + file.getOriginalFilename();
            userEntity.setAvatarUrl(avatarUrl);
            usersService.saveOrUpdate(userEntity);
            logsService.addLog("Saved file: " + path.toString());
            return new ImageUrlDto(avatarUrl);
        } catch (Exception e) {
            logsService.addLog("Error while saving file: " + e.getMessage());
            return new ImageUrlDto("/images/no-avatar.png");
        }
    }
}
