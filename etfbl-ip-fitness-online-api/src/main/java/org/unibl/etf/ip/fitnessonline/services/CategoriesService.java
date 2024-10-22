package org.unibl.etf.ip.fitnessonline.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.exception.ResourceNotFoundException;
import org.unibl.etf.ip.fitnessonline.models.entities.CategoryEntity;
import org.unibl.etf.ip.fitnessonline.repositories.CategoriesRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public List<CategoryEntity> getAllNotDeleted() {
        return categoriesRepository.findAllByDeletedFalse();
    }

    public CategoryEntity getById(Integer id) {
        return categoriesRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID: " + id + " does not exist"));
    }
}
