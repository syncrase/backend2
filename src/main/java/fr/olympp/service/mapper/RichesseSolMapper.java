package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.RichesseSolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RichesseSol and its DTO RichesseSolDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RichesseSolMapper extends EntityMapper<RichesseSolDTO, RichesseSol> {



    default RichesseSol fromId(Long id) {
        if (id == null) {
            return null;
        }
        RichesseSol richesseSol = new RichesseSol();
        richesseSol.setId(id);
        return richesseSol;
    }
}
