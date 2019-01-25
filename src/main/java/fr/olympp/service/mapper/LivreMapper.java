package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.LivreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Livre and its DTO LivreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LivreMapper extends EntityMapper<LivreDTO, Livre> {


    @Mapping(target = "reference", ignore = true)
    Livre toEntity(LivreDTO livreDTO);

    default Livre fromId(Long id) {
        if (id == null) {
            return null;
        }
        Livre livre = new Livre();
        livre.setId(id);
        return livre;
    }
}
