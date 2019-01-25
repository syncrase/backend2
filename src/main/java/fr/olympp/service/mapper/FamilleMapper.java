package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.FamilleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Famille and its DTO FamilleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FamilleMapper extends EntityMapper<FamilleDTO, Famille> {



    default Famille fromId(Long id) {
        if (id == null) {
            return null;
        }
        Famille famille = new Famille();
        famille.setId(id);
        return famille;
    }
}
