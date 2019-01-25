package fr.olympp.service.mapper;

import fr.olympp.domain.*;
import fr.olympp.service.dto.PageWebDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PageWeb and its DTO PageWebDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PageWebMapper extends EntityMapper<PageWebDTO, PageWeb> {


    @Mapping(target = "reference", ignore = true)
    PageWeb toEntity(PageWebDTO pageWebDTO);

    default PageWeb fromId(Long id) {
        if (id == null) {
            return null;
        }
        PageWeb pageWeb = new PageWeb();
        pageWeb.setId(id);
        return pageWeb;
    }
}
