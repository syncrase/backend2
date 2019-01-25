package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.ReferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reference and its DTO ReferenceDTO.
 */
@Mapper(componentModel = "spring", uses = {LivreMapper.class, PageWebMapper.class, InteractionPlantePlanteMapper.class})
public interface ReferenceMapper extends EntityMapper<ReferenceDTO, Reference> {

    @Mapping(source = "livre.id", target = "livreId")
    @Mapping(source = "pageWeb.id", target = "pageWebId")
    @Mapping(source = "interactionPlantePlante.id", target = "interactionPlantePlanteId")
    ReferenceDTO toDto(Reference reference);

    @Mapping(source = "livreId", target = "livre")
    @Mapping(source = "pageWebId", target = "pageWeb")
    @Mapping(source = "interactionPlantePlanteId", target = "interactionPlantePlante")
    Reference toEntity(ReferenceDTO referenceDTO);

    default Reference fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reference reference = new Reference();
        reference.setId(id);
        return reference;
    }
}
