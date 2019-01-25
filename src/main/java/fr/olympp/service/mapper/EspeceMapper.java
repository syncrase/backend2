package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.EspeceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Espece and its DTO EspeceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EspeceMapper extends EntityMapper<EspeceDTO, Espece> {



    default Espece fromId(Long id) {
        if (id == null) {
            return null;
        }
        Espece espece = new Espece();
        espece.setId(id);
        return espece;
    }
}
