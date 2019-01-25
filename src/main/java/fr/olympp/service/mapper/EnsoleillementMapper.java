package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.EnsoleillementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ensoleillement and its DTO EnsoleillementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EnsoleillementMapper extends EntityMapper<EnsoleillementDTO, Ensoleillement> {



    default Ensoleillement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ensoleillement ensoleillement = new Ensoleillement();
        ensoleillement.setId(id);
        return ensoleillement;
    }
}
