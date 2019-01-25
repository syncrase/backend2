package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.ClassificationCronquistDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClassificationCronquist and its DTO ClassificationCronquistDTO.
 */
@Mapper(componentModel = "spring", uses = {OrdreMapper.class, FamilleMapper.class, GenreMapper.class, EspeceMapper.class})
public interface ClassificationCronquistMapper extends EntityMapper<ClassificationCronquistDTO, ClassificationCronquist> {

    @Mapping(source = "ordre.id", target = "ordreId")
    @Mapping(source = "ordre.name", target = "ordreName")
    @Mapping(source = "famille.id", target = "familleId")
    @Mapping(source = "famille.name", target = "familleName")
    @Mapping(source = "genre.id", target = "genreId")
    @Mapping(source = "genre.name", target = "genreName")
    @Mapping(source = "espece.id", target = "especeId")
    @Mapping(source = "espece.name", target = "especeName")
    ClassificationCronquistDTO toDto(ClassificationCronquist classificationCronquist);

    @Mapping(source = "ordreId", target = "ordre")
    @Mapping(source = "familleId", target = "famille")
    @Mapping(source = "genreId", target = "genre")
    @Mapping(source = "especeId", target = "espece")
    ClassificationCronquist toEntity(ClassificationCronquistDTO classificationCronquistDTO);

    default ClassificationCronquist fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassificationCronquist classificationCronquist = new ClassificationCronquist();
        classificationCronquist.setId(id);
        return classificationCronquist;
    }
}
