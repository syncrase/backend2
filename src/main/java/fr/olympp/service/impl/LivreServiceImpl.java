package fr.olympp.service.impl;

import fr.olympp.service.LivreService;
import fr.olympp.domain.Livre;
import fr.olympp.repository.LivreRepository;
import fr.olympp.service.dto.LivreDTO;
import fr.olympp.service.mapper.LivreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Livre.
 */
@Service
@Transactional
public class LivreServiceImpl implements LivreService {

    private final Logger log = LoggerFactory.getLogger(LivreServiceImpl.class);

    private final LivreRepository livreRepository;

    private final LivreMapper livreMapper;

    public LivreServiceImpl(LivreRepository livreRepository, LivreMapper livreMapper) {
        this.livreRepository = livreRepository;
        this.livreMapper = livreMapper;
    }

    /**
     * Save a livre.
     *
     * @param livreDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LivreDTO save(LivreDTO livreDTO) {
        log.debug("Request to save Livre : {}", livreDTO);

        Livre livre = livreMapper.toEntity(livreDTO);
        livre = livreRepository.save(livre);
        return livreMapper.toDto(livre);
    }

    /**
     * Get all the livres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LivreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Livres");
        return livreRepository.findAll(pageable)
            .map(livreMapper::toDto);
    }



    /**
     *  get all the livres where Reference is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<LivreDTO> findAllWhereReferenceIsNull() {
        log.debug("Request to get all livres where Reference is null");
        return StreamSupport
            .stream(livreRepository.findAll().spliterator(), false)
            .filter(livre -> livre.getReference() == null)
            .map(livreMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one livre by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LivreDTO> findOne(Long id) {
        log.debug("Request to get Livre : {}", id);
        return livreRepository.findById(id)
            .map(livreMapper::toDto);
    }

    /**
     * Delete the livre by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Livre : {}", id);
        livreRepository.deleteById(id);
    }
}
