package fr.olympp.service.impl;

import fr.olympp.service.TypeRacineService;
import fr.olympp.domain.TypeRacine;
import fr.olympp.repository.TypeRacineRepository;
import fr.olympp.service.dto.TypeRacineDTO;
import fr.olympp.service.mapper.TypeRacineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeRacine.
 */
@Service
@Transactional
public class TypeRacineServiceImpl implements TypeRacineService {

    private final Logger log = LoggerFactory.getLogger(TypeRacineServiceImpl.class);

    private final TypeRacineRepository typeRacineRepository;

    private final TypeRacineMapper typeRacineMapper;

    public TypeRacineServiceImpl(TypeRacineRepository typeRacineRepository, TypeRacineMapper typeRacineMapper) {
        this.typeRacineRepository = typeRacineRepository;
        this.typeRacineMapper = typeRacineMapper;
    }

    /**
     * Save a typeRacine.
     *
     * @param typeRacineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeRacineDTO save(TypeRacineDTO typeRacineDTO) {
        log.debug("Request to save TypeRacine : {}", typeRacineDTO);

        TypeRacine typeRacine = typeRacineMapper.toEntity(typeRacineDTO);
        typeRacine = typeRacineRepository.save(typeRacine);
        return typeRacineMapper.toDto(typeRacine);
    }

    /**
     * Get all the typeRacines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeRacineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeRacines");
        return typeRacineRepository.findAll(pageable)
            .map(typeRacineMapper::toDto);
    }


    /**
     * Get one typeRacine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeRacineDTO> findOne(Long id) {
        log.debug("Request to get TypeRacine : {}", id);
        return typeRacineRepository.findById(id)
            .map(typeRacineMapper::toDto);
    }

    /**
     * Delete the typeRacine by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeRacine : {}", id);
        typeRacineRepository.deleteById(id);
    }
}
