package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.InteractionPlantePlanteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InteractionPlantePlante and its DTO InteractionPlantePlanteDTO.
 */
@Mapper(componentModel = "spring", uses = {PlanteMapper.class})
public interface InteractionPlantePlanteMapper extends EntityMapper<InteractionPlantePlanteDTO, InteractionPlantePlante> {

    @Mapping(source = "dePlante.id", target = "dePlanteId")
    @Mapping(source = "versPlante.id", target = "versPlanteId")
    InteractionPlantePlanteDTO toDto(InteractionPlantePlante interactionPlantePlante);

    @Mapping(target = "references", ignore = true)
    @Mapping(source = "dePlanteId", target = "dePlante")
    @Mapping(source = "versPlanteId", target = "versPlante")
    InteractionPlantePlante toEntity(InteractionPlantePlanteDTO interactionPlantePlanteDTO);

    default InteractionPlantePlante fromId(Long id) {
        if (id == null) {
            return null;
        }
        InteractionPlantePlante interactionPlantePlante = new InteractionPlantePlante();
        interactionPlantePlante.setId(id);
        return interactionPlantePlante;
    }
}
