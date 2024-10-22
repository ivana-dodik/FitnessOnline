package org.unibl.etf.ip.fitnessonline.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.models.dto.ImageUrlDto;
import org.unibl.etf.ip.fitnessonline.models.entities.ImageEntity;
import org.unibl.etf.ip.fitnessonline.repositories.ImagesRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ImagesService {

    private final ImagesRepository imagesRepository;

    public ImageEntity addProgramImage(String fileName, Integer programId) {
        ImageEntity imagesEntity = new ImageEntity();
        imagesEntity.setId(null);
        imagesEntity.setImageUrl("/images/programs/" + programId + "/" + fileName);
        imagesEntity.setProgramId(programId);
        return imagesRepository.saveAndFlush(imagesEntity);
    }

    public List<ImageEntity> getImagesByProgramId(Integer programId) {
        return imagesRepository.findAllByProgramId(programId);
    }

    public ImageUrlDto getThumbnailOrDefaultForProgramId(Integer id) {
        List<ImageEntity> imagesEntities = getImagesByProgramId(id);
        return new ImageUrlDto(imagesEntities.stream()
                .map(ImageEntity::getImageUrl)
                .findFirst()
                .orElse("/images/no-images.png"));
    }
}
