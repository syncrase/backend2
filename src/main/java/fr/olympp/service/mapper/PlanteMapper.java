package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.PlanteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Plante and its DTO PlanteDTO.
 */
@Mapper(componentModel = "spring", uses = {ClassificationCronquistMapper.class, StrateMapper.class, VitesseCroissanceMapper.class, EnsoleillementMapper.class, RichesseSolMapper.class, TypeTerreMapper.class, TypeFeuillageMapper.class, TypeRacineMapper.class, PlantCommonNameMapper.class})
public interface PlanteMapper extends EntityMapper<PlanteDTO, Plante> {

    @Mapping(source = "classificationCronquist.id", target = "classificationCronquistId")
    @Mapping(source = "strate.id", target = "strateId")
    @Mapping(source = "vitesseCroissance.id", target = "vitesseCroissanceId")
    @Mapping(source = "ensoleillement.id", target = "ensoleillementId")
    @Mapping(source = "richesseSol.id", target = "richesseSolId")
    @Mapping(source = "typeTerre.id", target = "typeTerreId")
    @Mapping(source = "typeFeuillage.id", target = "typeFeuillageId")
    @Mapping(source = "typeRacine.id", target = "typeRacineId")
    PlanteDTO toDto(Plante plante);

    @Mapping(source = "classificationCronquistId", target = "classificationCronquist")
    @Mapping(source = "strateId", target = "strate")
    @Mapping(source = "vitesseCroissanceId", target = "vitesseCroissance")
    @Mapping(source = "ensoleillementId", target = "ensoleillement")
    @Mapping(source = "richesseSolId", target = "richesseSol")
    @Mapping(source = "typeTerreId", target = "typeTerre")
    @Mapping(source = "typeFeuillageId", target = "typeFeuillage")
    @Mapping(source = "typeRacineId", target = "typeRacine")
    Plante toEntity(PlanteDTO planteDTO);

    default Plante fromId(Long id) {
        if (id == null) {
            return null;
        }
        Plante plante = new Plante();
        plante.setId(id);
        return plante;
    }
}
