package org.unibl.etf.ip.fitnessonline.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.exception.ResourceNotFoundException;
import org.unibl.etf.ip.fitnessonline.exception.UnauthenticatedException;
import org.unibl.etf.ip.fitnessonline.mapper.UsersMapper;
import org.unibl.etf.ip.fitnessonline.models.dto.*;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final EmailService emailService;
    @Qualifier("applicationTaskExecutor")
    private final TaskExecutor taskExecutor;
    private final CaptchaService captchaService;

    public static String generateShortId() {
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();

        // Convert UUID to string and take the first 4 characters
        String uuidString = uuid.toString().replace("-", "");
        String shortId = uuidString.substring(0, 4);

        return shortId;
    }

    public UserEntity getUserById(Integer id) {
        Optional<UserEntity> userOptional = usersRepository.findById(id);
        return userOptional.orElse(null);
    }

    public UserEntity getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public boolean isActivated(String username) {
        UserEntity user = usersRepository.findByUsername(username);
        return user != null && user.getActivated();
    }

    public UserEntity getById(Integer id) {
        return usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User with ID: " + id + " does not exist."
        ));
    }

    public InstructorDetailsDto getUserDetailsById(Integer id) {
        UserEntity userEntity = getById(id);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userEntity, InstructorDetailsDto.class);
    }

    public UserEntity create(AddUserDto addUserDto) {
        /*if (!captchaService.verifyCaptcha(addUserDto.getCaptchaResponse())) {
            throw new RuntimeException("Unsuccessful captcha verification. Please try again.");
        }*/

        try {
            int userAnswer = Integer.parseInt(addUserDto.getCaptchaResponse());
            int answer = addUserDto.getAnswer();
            if (answer != userAnswer) {
                throw new IllegalArgumentException("Captcha verification failed. Please try again.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //throw new IllegalArgumentException("Invalid captcha response format. Please enter a numeric value.");
        }

        if (usersRepository.getByUsername(addUserDto.getUsername()).isEmpty()) {
            ModelMapper modelMapper = UsersMapper.configureAddUserDtoToUserEntityMapper();
            UserEntity userEntity = modelMapper.map(addUserDto, UserEntity.class);
            userEntity.setId(null);
            String token = generateShortId();
            userEntity.setActivationToken(token);
            //String link = "http://localhost:9000/confirm-profile?userId=" + userEntity.getId();
            String link = "http://localhost:4200/registration/validate?token=" + token;
            UserEntity createdUser = usersRepository.saveAndFlush(userEntity);
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    emailService.sendSimpleMessage(addUserDto.getEmail(),
                            "Confirmation of registration for Fitness Online application",
                            "Confirm the registration by clicking on the following link: " + link);
                }
            });
            return createdUser;
        } else {
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBbb");
            return null;
        }
    }

    public UserEntity editUserProfile(UserEntity userEntity, EditUserProfileDto editUserProfileDto) {
        userEntity.setFirstName(editUserProfileDto.getFirstName());
        userEntity.setLastName(editUserProfileDto.getLastName());
        userEntity.setCity(editUserProfileDto.getCity());
        userEntity.setEmail(editUserProfileDto.getEmail());

        return usersRepository.saveAndFlush(userEntity);
    }

    public LoginUserDto changeUserPassword(UserEntity userEntity, EditUserPasswordDto editUserPassword) {
        try {
            ModelMapper modelMapper = UsersMapper.configureUserEntityToLoginUserDtoMapper();
            userEntity.setPassword(editUserPassword.getNewPassword());
            return modelMapper.map(usersRepository.saveAndFlush(userEntity), LoginUserDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    public UserEntity login(LoginUserDto loginUserDto) {
        return usersRepository.getByUsernameAndPasswordAndDeletedIsFalse(loginUserDto.getUsername(), loginUserDto.getPassword())
                .orElseThrow(() -> new UnauthenticatedException("Invalid credentialns"));
    }

    public UserEntity getUserInfoForValidation(LoginUserDto loginUserDto) {
        return usersRepository.getByUsernameAndPasswordAndDeletedIsFalse(loginUserDto.getUsername(), loginUserDto.getPassword())
                .orElseThrow(() -> new UnauthenticatedException("Invalid credentials"));
    }

    public UserEntity requestNewValidationMail(UserEntity userEntity) {
        String token = generateShortId();
        //String link = "http://localhost:9000/confirm-profile?userId=" + userEntity.getId();
        String link = "http://localhost:4200/login/validate?token=" + token;
        userEntity.setActivationToken(token);
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                emailService.sendSimpleMessage(userEntity.getEmail(),
                        "Confirmation of registration for Fitness Online application",
                        "Confirm the registration by clicking on the following link: " + link);
            }
        });
        return usersRepository.saveAndFlush(userEntity);
    }

    public ImageUrlDto getAvatarForUser(Integer id) {
        UserEntity userEntity = getById(id);
        return new ImageUrlDto(userEntity.getAvatarUrl());
    }

    public LoginUserDto validate(UserEntity userEntity, String token) {
        System.out.println("VVVVVVVVVvvv");
        String userToken = userEntity.getActivationToken();
        if (userToken == null) return null;

        if (!token.equals(userToken)) {
            return null;
        }
        System.out.println("222222222222222222222222");
        userEntity.setActivated(true);
        userEntity.setActivationToken(null);

        UserEntity updatedUserEntity = usersRepository.saveAndFlush(userEntity);

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setUsername(updatedUserEntity.getUsername());
        loginUserDto.setPassword(updatedUserEntity.getPassword());

        return loginUserDto;
    }

    public UserEntity saveOrUpdate(UserEntity userEntity) {

        return usersRepository.saveAndFlush(userEntity);
    }

    public List<UserDto> getAllUsers(UserEntity user) {
        List<UserEntity> users = usersRepository.findAll();
        return users.stream()
                .filter(u -> !u.getUsername().equals(user.getUsername()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserDto convertToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUserId(userEntity.getId());
        userDto.setUsername(userEntity.getUsername());
        //System.out.println("USER: " + userDto.getUserId() + "  " + userDto.getUsername());
        return userDto;
    }
}
