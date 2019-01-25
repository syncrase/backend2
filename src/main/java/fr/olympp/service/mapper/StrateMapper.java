package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.StrateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Strate and its DTO StrateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StrateMapper extends EntityMapper<StrateDTO, Strate> {



    default Strate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Strate strate = new Strate();
        strate.setId(id);
        return strate;
    }
}
