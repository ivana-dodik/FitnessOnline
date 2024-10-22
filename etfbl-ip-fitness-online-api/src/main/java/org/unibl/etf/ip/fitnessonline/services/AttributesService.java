package org.unibl.etf.ip.fitnessonline.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.exception.ResourceNotFoundException;
import org.unibl.etf.ip.fitnessonline.models.entities.AttributeEntity;
import org.unibl.etf.ip.fitnessonline.repositories.AttributesRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AttributesService {

    private final AttributesRepository attributesRepository;

    public AttributeEntity getById(Integer id) {
        return attributesRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attribute with ID: " + id + " does not exist"));
    }

    public List<AttributeEntity> getByCategoryId(Integer categoryId) {
        return attributesRepository.getAllByCategoryIdAndDeletedFalse(categoryId);
    }
}
