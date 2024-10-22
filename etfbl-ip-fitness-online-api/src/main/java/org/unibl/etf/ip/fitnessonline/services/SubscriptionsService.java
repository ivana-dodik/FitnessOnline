package org.unibl.etf.ip.fitnessonline.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.models.dto.ProgramDetailsDto;
import org.unibl.etf.ip.fitnessonline.models.entities.CategoryEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.ProgramEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.SubscriptionEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.repositories.CategoriesRepository;
import org.unibl.etf.ip.fitnessonline.repositories.ProgramsRepository;
import org.unibl.etf.ip.fitnessonline.repositories.SubscriptionRepository;
import org.unibl.etf.ip.fitnessonline.repositories.UsersRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class SubscriptionsService {
    private final SubscriptionRepository subscriptionRepository;
    private final ProgramsRepository programsRepository;
    private final UsersRepository usersRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProgramsService programsService;
    private final EmailService emailService;

    public void subscribeToCategory(UserEntity user, CategoryEntity category) {
        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setUserId(user.getId());
        subscription.setCategoryId(category.getId());
        subscriptionRepository.saveAndFlush(subscription);
    }

    public void subscribe(UserEntity user, Integer categoryId) {
        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setUserId(user.getId());
        subscription.setCategoryId(categoryId);
        subscriptionRepository.saveAndFlush(subscription);
    }

    public void unsubscribeFromCategory(UserEntity user, CategoryEntity category) {
        subscriptionRepository.deleteByUserIdAndCategoryId(user.getId(), category.getId());
    }

    public void unsubscribe(UserEntity user, Integer categoryId) {
        subscriptionRepository.deleteByUserIdAndCategoryId(user.getId(), categoryId);
    }

    public List<UserEntity> getUniqueUsersFromSubscriptions() {
        List<Integer> userIds = subscriptionRepository.findUserId();
        //List<Integer> userIds = subscriptions.stream().map(SubscriptionEntity::getUserId).collect(Collectors.toList());
        return usersRepository.findAllById(userIds);
    }

    public List<List<CategoryEntity>> getSubscribedCategoriesForUsers() {
        List<UserEntity> users = getUniqueUsersFromSubscriptions();
        List<List<CategoryEntity>> subscribedCategoriesLists = new ArrayList<>();

        for (UserEntity user : users) {
            List<SubscriptionEntity> subscriptions = subscriptionRepository.findByUserId(user.getId());
            List<CategoryEntity> subscribedCategories = new ArrayList<>();
            for (SubscriptionEntity subscription : subscriptions) {
                CategoryEntity category = categoriesRepository.findById(subscription.getCategoryId()).orElse(null);
                if (category != null) {
                    subscribedCategories.add(category);
                }
            }
            subscribedCategoriesLists.add(subscribedCategories);
        }

        return subscribedCategoriesLists;
    }

    public List<List<CategoryEntity>> getSubscribedCategoriesForUsers(List<UserEntity> users) {
        List<List<CategoryEntity>> subscribedCategoriesLists = new ArrayList<>();

        for (UserEntity user : users) {
            List<Integer> categoryIds = subscriptionRepository.findCategoryIdsByUserId(user.getId());
            List<CategoryEntity> subscribedCategories = categoryIds.stream()
                    .map(categoryId -> {
                        CategoryEntity category = new CategoryEntity();
                        category.setId(categoryId);
                        return category;
                    })
                    .collect(Collectors.toList());
            subscribedCategoriesLists.add(subscribedCategories);
        }

        return subscribedCategoriesLists;
    }

    public List<ProgramEntity> getProgramsByCategoryIdCreatedLast24Hours(Integer categoryId) {
        Timestamp yesterday = Timestamp.valueOf(LocalDateTime.now().minusDays(1));
        return programsRepository.findByCategoryIdAndTimeCreatedAfter(categoryId, yesterday);
    }

    // Run once a day at midnight
    @Scheduled(cron = "0 0 12 * * *")
//    @Scheduled(fixedRate = 1000000)
    public void sendProgramsToUsers() {
        List<UserEntity> users = getUniqueUsersFromSubscriptions();
        List<List<CategoryEntity>> subscribedCategoriesLists = getSubscribedCategoriesForUsers();
        for (UserEntity user : users) {
            String emailTo = user.getEmail();
            StringBuilder emailText = new StringBuilder();
            for (List<CategoryEntity> categories : subscribedCategoriesLists) {
                for (CategoryEntity category : categories) {
                    emailText.append("New programs created for the category ").append(category.getName()).append(" are: \n\n");
                    List<ProgramEntity> newProgramsForCategory = getProgramsByCategoryIdCreatedLast24Hours(category.getId());
                    if (newProgramsForCategory.isEmpty()) {
                        emailText.append("No new programs were created in the last 24h.");
                    } else {
                        for (ProgramEntity program : newProgramsForCategory) {
                            ProgramDetailsDto programDetailsDto = programsService.getProgramByIdWithAttributes(program.getId());
                            emailText.append(programDetailsDto.toString()).append("\n\n");
                        }
                    }
                }
            }
            emailService.sendSimpleMessage(emailTo, "Daily category update", emailText.toString());
        }
    }

    public List<SubscriptionEntity> getSubscriptionsByUserId(Integer userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    public List<CategoryEntity> getAllSubscribedCategories(Integer userId) {
        List<SubscriptionEntity> subscriptions = subscriptionRepository.findAllByUserId(userId);
        List<CategoryEntity> subscribedCategories = new ArrayList<>();
        for (SubscriptionEntity subscription : subscriptions) {
            CategoryEntity category = categoriesRepository.findById(subscription.getCategoryId()).orElse(null);
            if (category != null) {
                subscribedCategories.add(category);
            }
        }
        return subscribedCategories;
    }

    public List<CategoryEntity> getAllUnsubscribedCategories(Integer userId) {
        // Fetch all subscriptions of the user
        List<SubscriptionEntity> subscriptions = subscriptionRepository.findAllByUserId(userId);

        // Fetch all categories
        List<CategoryEntity> allCategories = categoriesRepository.findAllByDeletedFalse();

        // Create a set to store subscribed category ids
        Set<Integer> subscribedCategoryIds = new HashSet<>();
        for (SubscriptionEntity subscription : subscriptions) {
            subscribedCategoryIds.add(subscription.getCategoryId());
        }

        // Filter out subscribed categories
        List<CategoryEntity> unsubscribedCategories = new ArrayList<>();
        for (CategoryEntity category : allCategories) {
            if (!subscribedCategoryIds.contains(category.getId())) {
                unsubscribedCategories.add(category);
            }
        }

        return unsubscribedCategories;
    }


    public List<CategoryEntity> getSubscribedCategoriesByUserId(Integer userId) {
        List<SubscriptionEntity> subscriptions = subscriptionRepository.findByUserId(userId);
        List<Integer> categoryIds = subscriptions.stream()
                .map(SubscriptionEntity::getCategoryId)
                .collect(Collectors.toList());
        return categoriesRepository.findAllById(categoryIds);
    }
}
