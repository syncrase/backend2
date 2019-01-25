package fr.olympp.web.rest;

import fr.olympp.Backend2App;

import fr.olympp.domain.Reference;
import fr.olympp.domain.Livre;
import fr.olympp.domain.PageWeb;
import fr.olympp.domain.InteractionPlantePlante;
import fr.olympp.repository.ReferenceRepository;
import fr.olympp.service.ReferenceService;
import fr.olympp.service.dto.ReferenceDTO;
import fr.olympp.service.mapper.ReferenceMapper;
import fr.olympp.web.rest.errors.ExceptionTranslator;
import fr.olympp.service.dto.ReferenceCriteria;
import fr.olympp.service.ReferenceQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static fr.olympp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReferenceResource REST controller.
 *
 * @see ReferenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Backend2App.class)
public class ReferenceResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ReferenceRepository referenceRepository;

    @Autowired
    private ReferenceMapper referenceMapper;

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private ReferenceQueryService referenceQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restReferenceMockMvc;

    private Reference reference;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReferenceResource referenceResource = new ReferenceResource(referenceService, referenceQueryService);
        this.restReferenceMockMvc = MockMvcBuilders.standaloneSetup(referenceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reference createEntity(EntityManager em) {
        Reference reference = new Reference()
            .description(DEFAULT_DESCRIPTION);
        return reference;
    }

    @Before
    public void initTest() {
        reference = createEntity(em);
    }

    @Test
    @Transactional
    public void createReference() throws Exception {
        int databaseSizeBeforeCreate = referenceRepository.findAll().size();

        // Create the Reference
        ReferenceDTO referenceDTO = referenceMapper.toDto(reference);
        restReferenceMockMvc.perform(post("/api/references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Reference in the database
        List<Reference> referenceList = referenceRepository.findAll();
        assertThat(referenceList).hasSize(databaseSizeBeforeCreate + 1);
        Reference testReference = referenceList.get(referenceList.size() - 1);
        assertThat(testReference.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createReferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = referenceRepository.findAll().size();

        // Create the Reference with an existing ID
        reference.setId(1L);
        ReferenceDTO referenceDTO = referenceMapper.toDto(reference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferenceMockMvc.perform(post("/api/references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reference in the database
        List<Reference> referenceList = referenceRepository.findAll();
        assertThat(referenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReferences() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList
        restReferenceMockMvc.perform(get("/api/references?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reference.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getReference() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get the reference
        restReferenceMockMvc.perform(get("/api/references/{id}", reference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reference.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllReferencesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where description equals to DEFAULT_DESCRIPTION
        defaultReferenceShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the referenceList where description equals to UPDATED_DESCRIPTION
        defaultReferenceShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReferencesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultReferenceShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the referenceList where description equals to UPDATED_DESCRIPTION
        defaultReferenceShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReferencesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        // Get all the referenceList where description is not null
        defaultReferenceShouldBeFound("description.specified=true");

        // Get all the referenceList where description is null
        defaultReferenceShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllReferencesByLivreIsEqualToSomething() throws Exception {
        // Initialize the database
        Livre livre = LivreResourceIntTest.createEntity(em);
        em.persist(livre);
        em.flush();
        reference.setLivre(livre);
        referenceRepository.saveAndFlush(reference);
        Long livreId = livre.getId();

        // Get all the referenceList where livre equals to livreId
        defaultReferenceShouldBeFound("livreId.equals=" + livreId);

        // Get all the referenceList where livre equals to livreId + 1
        defaultReferenceShouldNotBeFound("livreId.equals=" + (livreId + 1));
    }


    @Test
    @Transactional
    public void getAllReferencesByPageWebIsEqualToSomething() throws Exception {
        // Initialize the database
        PageWeb pageWeb = PageWebResourceIntTest.createEntity(em);
        em.persist(pageWeb);
        em.flush();
        reference.setPageWeb(pageWeb);
        referenceRepository.saveAndFlush(reference);
        Long pageWebId = pageWeb.getId();

        // Get all the referenceList where pageWeb equals to pageWebId
        defaultReferenceShouldBeFound("pageWebId.equals=" + pageWebId);

        // Get all the referenceList where pageWeb equals to pageWebId + 1
        defaultReferenceShouldNotBeFound("pageWebId.equals=" + (pageWebId + 1));
    }


    @Test
    @Transactional
    public void getAllReferencesByInteractionPlantePlanteIsEqualToSomething() throws Exception {
        // Initialize the database
        InteractionPlantePlante interactionPlantePlante = InteractionPlantePlanteResourceIntTest.createEntity(em);
        em.persist(interactionPlantePlante);
        em.flush();
        reference.setInteractionPlantePlante(interactionPlantePlante);
        referenceRepository.saveAndFlush(reference);
        Long interactionPlantePlanteId = interactionPlantePlante.getId();

        // Get all the referenceList where interactionPlantePlante equals to interactionPlantePlanteId
        defaultReferenceShouldBeFound("interactionPlantePlanteId.equals=" + interactionPlantePlanteId);

        // Get all the referenceList where interactionPlantePlante equals to interactionPlantePlanteId + 1
        defaultReferenceShouldNotBeFound("interactionPlantePlanteId.equals=" + (interactionPlantePlanteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultReferenceShouldBeFound(String filter) throws Exception {
        restReferenceMockMvc.perform(get("/api/references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reference.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));

        // Check, that the count call also returns 1
        restReferenceMockMvc.perform(get("/api/references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultReferenceShouldNotBeFound(String filter) throws Exception {
        restReferenceMockMvc.perform(get("/api/references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReferenceMockMvc.perform(get("/api/references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReference() throws Exception {
        // Get the reference
        restReferenceMockMvc.perform(get("/api/references/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReference() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        int databaseSizeBeforeUpdate = referenceRepository.findAll().size();

        // Update the reference
        Reference updatedReference = referenceRepository.findById(reference.getId()).get();
        // Disconnect from session so that the updates on updatedReference are not directly saved in db
        em.detach(updatedReference);
        updatedReference
            .description(UPDATED_DESCRIPTION);
        ReferenceDTO referenceDTO = referenceMapper.toDto(updatedReference);

        restReferenceMockMvc.perform(put("/api/references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referenceDTO)))
            .andExpect(status().isOk());

        // Validate the Reference in the database
        List<Reference> referenceList = referenceRepository.findAll();
        assertThat(referenceList).hasSize(databaseSizeBeforeUpdate);
        Reference testReference = referenceList.get(referenceList.size() - 1);
        assertThat(testReference.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingReference() throws Exception {
        int databaseSizeBeforeUpdate = referenceRepository.findAll().size();

        // Create the Reference
        ReferenceDTO referenceDTO = referenceMapper.toDto(reference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferenceMockMvc.perform(put("/api/references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reference in the database
        List<Reference> referenceList = referenceRepository.findAll();
        assertThat(referenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReference() throws Exception {
        // Initialize the database
        referenceRepository.saveAndFlush(reference);

        int databaseSizeBeforeDelete = referenceRepository.findAll().size();

        // Get the reference
        restReferenceMockMvc.perform(delete("/api/references/{id}", reference.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reference> referenceList = referenceRepository.findAll();
        assertThat(referenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reference.class);
        Reference reference1 = new Reference();
        reference1.setId(1L);
        Reference reference2 = new Reference();
        reference2.setId(reference1.getId());
        assertThat(reference1).isEqualTo(reference2);
        reference2.setId(2L);
        assertThat(reference1).isNotEqualTo(reference2);
        reference1.setId(null);
        assertThat(reference1).isNotEqualTo(reference2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferenceDTO.class);
        ReferenceDTO referenceDTO1 = new ReferenceDTO();
        referenceDTO1.setId(1L);
        ReferenceDTO referenceDTO2 = new ReferenceDTO();
        assertThat(referenceDTO1).isNotEqualTo(referenceDTO2);
        referenceDTO2.setId(referenceDTO1.getId());
        assertThat(referenceDTO1).isEqualTo(referenceDTO2);
        referenceDTO2.setId(2L);
        assertThat(referenceDTO1).isNotEqualTo(referenceDTO2);
        referenceDTO1.setId(null);
        assertThat(referenceDTO1).isNotEqualTo(referenceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(referenceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(referenceMapper.fromId(null)).isNull();
    }
}
