package org.unibl.etf.ip.fitnessonline.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.models.entities.ProgramAttributeEntity;
import org.unibl.etf.ip.fitnessonline.repositories.ProgramAttributesRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProgramAttributesService {
    ProgramAttributesRepository programAttributesRepository;

    public ProgramAttributeEntity create(ProgramAttributeEntity programAttributeEntity) {
        return programAttributesRepository.saveAndFlush(programAttributeEntity);
    }

    public List<ProgramAttributeEntity> findByProgramId(Integer id) {
        return programAttributesRepository.findByProgramId(id);
    }
}

