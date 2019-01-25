package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.MoisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mois and its DTO MoisDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MoisMapper extends EntityMapper<MoisDTO, Mois> {



    default Mois fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mois mois = new Mois();
        mois.setId(id);
        return mois;
    }
}
