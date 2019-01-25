package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.TypeRacineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeRacine and its DTO TypeRacineDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeRacineMapper extends EntityMapper<TypeRacineDTO, TypeRacine> {



    default TypeRacine fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeRacine typeRacine = new TypeRacine();
        typeRacine.setId(id);
        return typeRacine;
    }
}
