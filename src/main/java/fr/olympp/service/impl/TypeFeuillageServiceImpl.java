package fr.olympp.service.impl;

import fr.olympp.service.TypeFeuillageService;
import fr.olympp.domain.TypeFeuillage;
import fr.olympp.repository.TypeFeuillageRepository;
import fr.olympp.service.dto.TypeFeuillageDTO;
import fr.olympp.service.mapper.TypeFeuillageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeFeuillage.
 */
@Service
@Transactional
public class TypeFeuillageServiceImpl implements TypeFeuillageService {

    private final Logger log = LoggerFactory.getLogger(TypeFeuillageServiceImpl.class);

    private final TypeFeuillageRepository typeFeuillageRepository;

    private final TypeFeuillageMapper typeFeuillageMapper;

    public TypeFeuillageServiceImpl(TypeFeuillageRepository typeFeuillageRepository, TypeFeuillageMapper typeFeuillageMapper) {
        this.typeFeuillageRepository = typeFeuillageRepository;
        this.typeFeuillageMapper = typeFeuillageMapper;
    }

    /**
     * Save a typeFeuillage.
     *
     * @param typeFeuillageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeFeuillageDTO save(TypeFeuillageDTO typeFeuillageDTO) {
        log.debug("Request to save TypeFeuillage : {}", typeFeuillageDTO);

        TypeFeuillage typeFeuillage = typeFeuillageMapper.toEntity(typeFeuillageDTO);
        typeFeuillage = typeFeuillageRepository.save(typeFeuillage);
        return typeFeuillageMapper.toDto(typeFeuillage);
    }

    /**
     * Get all the typeFeuillages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeFeuillageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeFeuillages");
        return typeFeuillageRepository.findAll(pageable)
            .map(typeFeuillageMapper::toDto);
    }


    /**
     * Get one typeFeuillage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeFeuillageDTO> findOne(Long id) {
        log.debug("Request to get TypeFeuillage : {}", id);
        return typeFeuillageRepository.findById(id)
            .map(typeFeuillageMapper::toDto);
    }

    /**
     * Delete the typeFeuillage by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeFeuillage : {}", id);
        typeFeuillageRepository.deleteById(id);
    }
}
