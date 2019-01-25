package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.TypeFeuillageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeFeuillage and its DTO TypeFeuillageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeFeuillageMapper extends EntityMapper<TypeFeuillageDTO, TypeFeuillage> {



    default TypeFeuillage fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeFeuillage typeFeuillage = new TypeFeuillage();
        typeFeuillage.setId(id);
        return typeFeuillage;
    }
}
