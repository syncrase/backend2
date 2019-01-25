package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.TypeTerreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeTerre and its DTO TypeTerreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeTerreMapper extends EntityMapper<TypeTerreDTO, TypeTerre> {



    default TypeTerre fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeTerre typeTerre = new TypeTerre();
        typeTerre.setId(id);
        return typeTerre;
    }
}
