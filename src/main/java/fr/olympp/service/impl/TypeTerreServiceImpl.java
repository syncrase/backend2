package fr.olympp.service.impl;

import fr.olympp.service.TypeTerreService;
import fr.olympp.domain.TypeTerre;
import fr.olympp.repository.TypeTerreRepository;
import fr.olympp.service.dto.TypeTerreDTO;
import fr.olympp.service.mapper.TypeTerreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeTerre.
 */
@Service
@Transactional
public class TypeTerreServiceImpl implements TypeTerreService {

    private final Logger log = LoggerFactory.getLogger(TypeTerreServiceImpl.class);

    private final TypeTerreRepository typeTerreRepository;

    private final TypeTerreMapper typeTerreMapper;

    public TypeTerreServiceImpl(TypeTerreRepository typeTerreRepository, TypeTerreMapper typeTerreMapper) {
        this.typeTerreRepository = typeTerreRepository;
        this.typeTerreMapper = typeTerreMapper;
    }

    /**
     * Save a typeTerre.
     *
     * @param typeTerreDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeTerreDTO save(TypeTerreDTO typeTerreDTO) {
        log.debug("Request to save TypeTerre : {}", typeTerreDTO);

        TypeTerre typeTerre = typeTerreMapper.toEntity(typeTerreDTO);
        typeTerre = typeTerreRepository.save(typeTerre);
        return typeTerreMapper.toDto(typeTerre);
    }

    /**
     * Get all the typeTerres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeTerreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeTerres");
        return typeTerreRepository.findAll(pageable)
            .map(typeTerreMapper::toDto);
    }


    /**
     * Get one typeTerre by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeTerreDTO> findOne(Long id) {
        log.debug("Request to get TypeTerre : {}", id);
        return typeTerreRepository.findById(id)
            .map(typeTerreMapper::toDto);
    }

    /**
     * Delete the typeTerre by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeTerre : {}", id);
        typeTerreRepository.deleteById(id);
    }
}
