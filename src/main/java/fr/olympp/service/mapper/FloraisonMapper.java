package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.FloraisonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Floraison and its DTO FloraisonDTO.
 */
@Mapper(componentModel = "spring", uses = {PlanteMapper.class, MoisMapper.class})
public interface FloraisonMapper extends EntityMapper<FloraisonDTO, Floraison> {

    @Mapping(source = "plante.id", target = "planteId")
    @Mapping(source = "mois.id", target = "moisId")
    FloraisonDTO toDto(Floraison floraison);

    @Mapping(source = "planteId", target = "plante")
    @Mapping(source = "moisId", target = "mois")
    Floraison toEntity(FloraisonDTO floraisonDTO);

    default Floraison fromId(Long id) {
        if (id == null) {
            return null;
        }
        Floraison floraison = new Floraison();
        floraison.setId(id);
        return floraison;
    }
}
