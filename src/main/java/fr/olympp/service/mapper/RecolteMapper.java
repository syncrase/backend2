package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.RecolteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Recolte and its DTO RecolteDTO.
 */
@Mapper(componentModel = "spring", uses = {PlanteMapper.class, MoisMapper.class})
public interface RecolteMapper extends EntityMapper<RecolteDTO, Recolte> {

    @Mapping(source = "plante.id", target = "planteId")
    @Mapping(source = "mois.id", target = "moisId")
    RecolteDTO toDto(Recolte recolte);

    @Mapping(source = "planteId", target = "plante")
    @Mapping(source = "moisId", target = "mois")
    Recolte toEntity(RecolteDTO recolteDTO);

    default Recolte fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recolte recolte = new Recolte();
        recolte.setId(id);
        return recolte;
    }
}
