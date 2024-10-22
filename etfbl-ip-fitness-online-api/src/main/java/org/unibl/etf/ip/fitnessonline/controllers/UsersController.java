package org.unibl.etf.ip.fitnessonline.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.fitnessonline.models.dto.*;
import org.unibl.etf.ip.fitnessonline.models.entities.CategoryEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.SubscriptionEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.services.AuthenticationService;
import org.unibl.etf.ip.fitnessonline.services.SubscriptionsService;
import org.unibl.etf.ip.fitnessonline.services.UsersService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UsersController {
    private final UsersService usersService;
    private final AuthenticationService authenticationService;
    private final SubscriptionsService subscriptionsService;

    @GetMapping("/details/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
        UserEntity user = usersService.getUserById(id);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/check-activation/{username}")
    public ResponseEntity<UserEntity> checkActivation(@PathVariable String username) {
        UserEntity user = usersService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /*@GetMapping("/check-activation/{username}")
    public ResponseEntity<Boolean> checkActivation(@PathVariable String username) {
        boolean isActivated = usersService.isActivated(username);
        return ResponseEntity.ok(isActivated);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDetailsDto> getInstructorDetails(@PathVariable Integer id) {
        return ResponseEntity.ok(usersService.getUserDetailsById(id));
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody @Valid AddUserDto addUserDto) {
        //System.out.println("CCCCCCCCCCCCCCCCC");
        //return ResponseEntity.ok(usersService.create(addUserDto));
        try {
            //System.out.println("DDDDDDDDDDDDD");
            UserEntity createdUser = usersService.create(addUserDto);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            //System.out.println("WEEEEEEEEEEEEEE");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while creating user.");
        }
    }

    @PatchMapping
    public ResponseEntity<UserEntity> editUser(@RequestBody() EditUserProfileDto editUserProfileDto,
                                               @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                               @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity userEntity = new UserEntity();
        try {
            userEntity = authenticationService.getUserInfoForValidation(xAuthUsername, xAuthPassword);
        } catch (Exception e) {
            e.getMessage();
        }
        return ResponseEntity.ok(usersService.editUserProfile(userEntity, editUserProfileDto));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<LoginUserDto> changePassword(@RequestBody() EditUserPasswordDto editUserPasswordDto,
                                                       @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                       @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        try {
            UserEntity userEntity = authenticationService.getUserInfoForValidation(xAuthUsername, editUserPasswordDto.getCurrentPassword());
            return ResponseEntity.ok(usersService.changeUserPassword(userEntity, editUserPasswordDto));

        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/confirm-profile")
    public ResponseEntity<LoginUserDto> confirmProfile(@RequestBody(required = true) TokenDto activationToken,
                                                       @RequestHeader(value = "X-Auth-Username", required = false) String xAuthUsername,
                                                       @RequestHeader(value = "X-Auth-Password", required = false) String xAuthPassword) {
        UserEntity userEntity = new UserEntity();
        try {
            userEntity = authenticationService.getUserInfoForValidation(xAuthUsername, xAuthPassword);
        } catch (Exception e) {
            e.getMessage();
        }
        return ResponseEntity.ok(usersService.validate(userEntity, activationToken.activationToken()));
    }

    @GetMapping("/activation-reset")
    public ResponseEntity<UserEntity> requestNewValidationMail(@RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                               @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity userEntity = new UserEntity();
        try {
            userEntity = authenticationService.getUserInfoForValidation(xAuthUsername, xAuthPassword);
        } catch (Exception e) {
            e.getMessage();
        }
        return ResponseEntity.ok(usersService.requestNewValidationMail(userEntity));
    }

    @GetMapping("/user/categories")
    public ResponseEntity<List<CategoryEntity>> getSubscriptionsByUserId(@RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                                         @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity userEntity = new UserEntity();
        try {
            userEntity = authenticationService.getUserInfoForValidation(xAuthUsername, xAuthPassword);
        } catch (Exception e) {
            e.getMessage();
        }
        return ResponseEntity.ok(subscriptionsService.getAllSubscribedCategories(userEntity.getId()));
    }

    @GetMapping("/user/categoriesu")
    public ResponseEntity<List<CategoryEntity>> getUnsubscriptionsByUserId(@RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                                         @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity userEntity = new UserEntity();
        try {
            userEntity = authenticationService.getUserInfoForValidation(xAuthUsername, xAuthPassword);
        } catch (Exception e) {
            e.getMessage();
        }
        return ResponseEntity.ok(subscriptionsService.getAllUnsubscribedCategories(userEntity.getId()));
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                     @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        //List<UserDto> users = usersService.getAllUsers();
        //return new ResponseEntity<>(users, HttpStatus.OK);
        //return ResponseEntity.ok(usersService.getAllUsers());
        UserEntity userEntity = new UserEntity();
        try {
            userEntity = authenticationService.getUserInfoForValidation(xAuthUsername, xAuthPassword);
        } catch (Exception e) {
            e.getMessage();
        }
        List<UserDto> users = usersService.getAllUsers(userEntity);

        /*users.forEach(u -> {
            System.out.println("UUUUUSER: " + u.getUsername() + " " + u.getUserId());
        });*/

        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
