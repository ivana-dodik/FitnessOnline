package org.unibl.etf.ip.fitnessonline.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.fitnessonline.models.entities.AttributeEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.CategoryEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.services.AttributesService;
import org.unibl.etf.ip.fitnessonline.services.AuthenticationService;
import org.unibl.etf.ip.fitnessonline.services.CategoriesService;
import org.unibl.etf.ip.fitnessonline.services.SubscriptionsService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class CategoriesController {

    private final CategoriesService categoriesService;
    private final AttributesService attributesService;
    private final AuthenticationService authenticationService;
    private final SubscriptionsService subscriptionsService;

    @GetMapping
    public List<CategoryEntity> getAllNotDeleted() {
        return categoriesService.getAllNotDeleted();
    }

    @GetMapping("/{id}")
    public CategoryEntity getById(@PathVariable @Valid Integer id) {
        return categoriesService.getById(id);
    }

    @GetMapping("/{id}/attributes")
    public List<AttributeEntity> getAttributesByCategoryId(@PathVariable @Valid Integer id) {
        return attributesService.getByCategoryId(id);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribeToCategory(@RequestBody Integer categoryId,
                                                 @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                 @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity user = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        //CategoryEntity category = categoriesService.getById(categoryId);
        subscriptionsService.subscribe(user, categoryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribeFromCategory(@RequestBody Integer categoryId,
                                                  @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                  @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity user = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        //CategoryEntity category = categoriesService.getById(categoryId);
        subscriptionsService.unsubscribe(user, categoryId);
        return ResponseEntity.ok().build();
    }
}
