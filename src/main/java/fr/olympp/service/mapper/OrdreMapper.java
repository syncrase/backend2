package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.OrdreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ordre and its DTO OrdreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdreMapper extends EntityMapper<OrdreDTO, Ordre> {



    default Ordre fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ordre ordre = new Ordre();
        ordre.setId(id);
        return ordre;
    }
}
