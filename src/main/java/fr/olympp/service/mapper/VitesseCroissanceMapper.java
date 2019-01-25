package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.VitesseCroissanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VitesseCroissance and its DTO VitesseCroissanceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VitesseCroissanceMapper extends EntityMapper<VitesseCroissanceDTO, VitesseCroissance> {



    default VitesseCroissance fromId(Long id) {
        if (id == null) {
            return null;
        }
        VitesseCroissance vitesseCroissance = new VitesseCroissance();
        vitesseCroissance.setId(id);
        return vitesseCroissance;
    }
}
