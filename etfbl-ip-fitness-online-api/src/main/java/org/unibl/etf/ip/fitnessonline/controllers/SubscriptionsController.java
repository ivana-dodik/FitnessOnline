package org.unibl.etf.ip.fitnessonline.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.fitnessonline.services.AuthenticationService;
import org.unibl.etf.ip.fitnessonline.services.CategoriesService;
import org.unibl.etf.ip.fitnessonline.services.SubscriptionsService;

@RestController
@AllArgsConstructor
@RequestMapping("/subscriptions")
@CrossOrigin(origins = "*")
public class SubscriptionsController {

    private final SubscriptionsService subscriptionsService;
    private final AuthenticationService authenticationService;
    private final CategoriesService categoriesService;

    /*
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeToCategory(@RequestParam Integer categoryId,
                                                      @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                      @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity user = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        CategoryEntity category = categoriesService.getById(categoryId);
        subscriptionsService.subscribeToCategory(user, category);
        return ResponseEntity.ok("Subscription successful");
    }*/
    /*
    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribeFromCategory(@RequestParam Integer categoryId,
                                                          @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                          @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity user = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        CategoryEntity category = categoriesService.getById(categoryId);
        subscriptionsService.unsubscribeFromCategory(user, category);
        return ResponseEntity.ok("Unsubscription successful");
    }
    */


}
