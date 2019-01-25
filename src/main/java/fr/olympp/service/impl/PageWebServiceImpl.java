package fr.olympp.service.impl;

import fr.olympp.service.PageWebService;
import fr.olympp.domain.PageWeb;
import fr.olympp.repository.PageWebRepository;
import fr.olympp.service.dto.PageWebDTO;
import fr.olympp.service.mapper.PageWebMapper;
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
 * Service Implementation for managing PageWeb.
 */
@Service
@Transactional
public class PageWebServiceImpl implements PageWebService {

    private final Logger log = LoggerFactory.getLogger(PageWebServiceImpl.class);

    private final PageWebRepository pageWebRepository;

    private final PageWebMapper pageWebMapper;

    public PageWebServiceImpl(PageWebRepository pageWebRepository, PageWebMapper pageWebMapper) {
        this.pageWebRepository = pageWebRepository;
        this.pageWebMapper = pageWebMapper;
    }

    /**
     * Save a pageWeb.
     *
     * @param pageWebDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PageWebDTO save(PageWebDTO pageWebDTO) {
        log.debug("Request to save PageWeb : {}", pageWebDTO);

        PageWeb pageWeb = pageWebMapper.toEntity(pageWebDTO);
        pageWeb = pageWebRepository.save(pageWeb);
        return pageWebMapper.toDto(pageWeb);
    }

    /**
     * Get all the pageWebs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PageWebDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PageWebs");
        return pageWebRepository.findAll(pageable)
            .map(pageWebMapper::toDto);
    }



    /**
     *  get all the pageWebs where Reference is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PageWebDTO> findAllWhereReferenceIsNull() {
        log.debug("Request to get all pageWebs where Reference is null");
        return StreamSupport
            .stream(pageWebRepository.findAll().spliterator(), false)
            .filter(pageWeb -> pageWeb.getReference() == null)
            .map(pageWebMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pageWeb by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PageWebDTO> findOne(Long id) {
        log.debug("Request to get PageWeb : {}", id);
        return pageWebRepository.findById(id)
            .map(pageWebMapper::toDto);
    }

    /**
     * Delete the pageWeb by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PageWeb : {}", id);
        pageWebRepository.deleteById(id);
    }
}
