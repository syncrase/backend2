package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.PlantCommonNameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PlantCommonName and its DTO PlantCommonNameDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlantCommonNameMapper extends EntityMapper<PlantCommonNameDTO, PlantCommonName> {


    @Mapping(target = "plantes", ignore = true)
    PlantCommonName toEntity(PlantCommonNameDTO plantCommonNameDTO);

    default PlantCommonName fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlantCommonName plantCommonName = new PlantCommonName();
        plantCommonName.setId(id);
        return plantCommonName;
    }
}
