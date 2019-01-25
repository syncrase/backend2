package fr.olympp.web.rest;

import fr.olympp.Backend2App;

import fr.olympp.domain.InteractionPlantePlante;
import fr.olympp.domain.Reference;
import fr.olympp.domain.Plante;
import fr.olympp.domain.Plante;
import fr.olympp.repository.InteractionPlantePlanteRepository;
import fr.olympp.service.InteractionPlantePlanteService;
import fr.olympp.service.dto.InteractionPlantePlanteDTO;
import fr.olympp.service.mapper.InteractionPlantePlanteMapper;
import fr.olympp.web.rest.errors.ExceptionTranslator;
import fr.olympp.service.dto.InteractionPlantePlanteCriteria;
import fr.olympp.service.InteractionPlantePlanteQueryService;

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
 * Test class for the InteractionPlantePlanteResource REST controller.
 *
 * @see InteractionPlantePlanteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Backend2App.class)
public class InteractionPlantePlanteResourceIntTest {

    private static final String DEFAULT_NOTATION = "+";
    private static final String UPDATED_NOTATION = "-";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private InteractionPlantePlanteRepository interactionPlantePlanteRepository;

    @Autowired
    private InteractionPlantePlanteMapper interactionPlantePlanteMapper;

    @Autowired
    private InteractionPlantePlanteService interactionPlantePlanteService;

    @Autowired
    private InteractionPlantePlanteQueryService interactionPlantePlanteQueryService;

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

    private MockMvc restInteractionPlantePlanteMockMvc;

    private InteractionPlantePlante interactionPlantePlante;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InteractionPlantePlanteResource interactionPlantePlanteResource = new InteractionPlantePlanteResource(interactionPlantePlanteService, interactionPlantePlanteQueryService);
        this.restInteractionPlantePlanteMockMvc = MockMvcBuilders.standaloneSetup(interactionPlantePlanteResource)
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
    public static InteractionPlantePlante createEntity(EntityManager em) {
        InteractionPlantePlante interactionPlantePlante = new InteractionPlantePlante()
            .notation(DEFAULT_NOTATION)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Plante plante = PlanteResourceIntTest.createEntity(em);
        em.persist(plante);
        em.flush();
        interactionPlantePlante.setDePlante(plante);
        // Add required entity
        interactionPlantePlante.setVersPlante(plante);
        return interactionPlantePlante;
    }

    @Before
    public void initTest() {
        interactionPlantePlante = createEntity(em);
    }

    @Test
    @Transactional
    public void createInteractionPlantePlante() throws Exception {
        int databaseSizeBeforeCreate = interactionPlantePlanteRepository.findAll().size();

        // Create the InteractionPlantePlante
        InteractionPlantePlanteDTO interactionPlantePlanteDTO = interactionPlantePlanteMapper.toDto(interactionPlantePlante);
        restInteractionPlantePlanteMockMvc.perform(post("/api/interaction-plante-plantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interactionPlantePlanteDTO)))
            .andExpect(status().isCreated());

        // Validate the InteractionPlantePlante in the database
        List<InteractionPlantePlante> interactionPlantePlanteList = interactionPlantePlanteRepository.findAll();
        assertThat(interactionPlantePlanteList).hasSize(databaseSizeBeforeCreate + 1);
        InteractionPlantePlante testInteractionPlantePlante = interactionPlantePlanteList.get(interactionPlantePlanteList.size() - 1);
        assertThat(testInteractionPlantePlante.getNotation()).isEqualTo(DEFAULT_NOTATION);
        assertThat(testInteractionPlantePlante.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createInteractionPlantePlanteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interactionPlantePlanteRepository.findAll().size();

        // Create the InteractionPlantePlante with an existing ID
        interactionPlantePlante.setId(1L);
        InteractionPlantePlanteDTO interactionPlantePlanteDTO = interactionPlantePlanteMapper.toDto(interactionPlantePlante);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInteractionPlantePlanteMockMvc.perform(post("/api/interaction-plante-plantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interactionPlantePlanteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InteractionPlantePlante in the database
        List<InteractionPlantePlante> interactionPlantePlanteList = interactionPlantePlanteRepository.findAll();
        assertThat(interactionPlantePlanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInteractionPlantePlantes() throws Exception {
        // Initialize the database
        interactionPlantePlanteRepository.saveAndFlush(interactionPlantePlante);

        // Get all the interactionPlantePlanteList
        restInteractionPlantePlanteMockMvc.perform(get("/api/interaction-plante-plantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interactionPlantePlante.getId().intValue())))
            .andExpect(jsonPath("$.[*].notation").value(hasItem(DEFAULT_NOTATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getInteractionPlantePlante() throws Exception {
        // Initialize the database
        interactionPlantePlanteRepository.saveAndFlush(interactionPlantePlante);

        // Get the interactionPlantePlante
        restInteractionPlantePlanteMockMvc.perform(get("/api/interaction-plante-plantes/{id}", interactionPlantePlante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(interactionPlantePlante.getId().intValue()))
            .andExpect(jsonPath("$.notation").value(DEFAULT_NOTATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllInteractionPlantePlantesByNotationIsEqualToSomething() throws Exception {
        // Initialize the database
        interactionPlantePlanteRepository.saveAndFlush(interactionPlantePlante);

        // Get all the interactionPlantePlanteList where notation equals to DEFAULT_NOTATION
        defaultInteractionPlantePlanteShouldBeFound("notation.equals=" + DEFAULT_NOTATION);

        // Get all the interactionPlantePlanteList where notation equals to UPDATED_NOTATION
        defaultInteractionPlantePlanteShouldNotBeFound("notation.equals=" + UPDATED_NOTATION);
    }

    @Test
    @Transactional
    public void getAllInteractionPlantePlantesByNotationIsInShouldWork() throws Exception {
        // Initialize the database
        interactionPlantePlanteRepository.saveAndFlush(interactionPlantePlante);

        // Get all the interactionPlantePlanteList where notation in DEFAULT_NOTATION or UPDATED_NOTATION
        defaultInteractionPlantePlanteShouldBeFound("notation.in=" + DEFAULT_NOTATION + "," + UPDATED_NOTATION);

        // Get all the interactionPlantePlanteList where notation equals to UPDATED_NOTATION
        defaultInteractionPlantePlanteShouldNotBeFound("notation.in=" + UPDATED_NOTATION);
    }

    @Test
    @Transactional
    public void getAllInteractionPlantePlantesByNotationIsNullOrNotNull() throws Exception {
        // Initialize the database
        interactionPlantePlanteRepository.saveAndFlush(interactionPlantePlante);

        // Get all the interactionPlantePlanteList where notation is not null
        defaultInteractionPlantePlanteShouldBeFound("notation.specified=true");

        // Get all the interactionPlantePlanteList where notation is null
        defaultInteractionPlantePlanteShouldNotBeFound("notation.specified=false");
    }

    @Test
    @Transactional
    public void getAllInteractionPlantePlantesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        interactionPlantePlanteRepository.saveAndFlush(interactionPlantePlante);

        // Get all the interactionPlantePlanteList where description equals to DEFAULT_DESCRIPTION
        defaultInteractionPlantePlanteShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the interactionPlantePlanteList where description equals to UPDATED_DESCRIPTION
        defaultInteractionPlantePlanteShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInteractionPlantePlantesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        interactionPlantePlanteRepository.saveAndFlush(interactionPlantePlante);

        // Get all the interactionPlantePlanteList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultInteractionPlantePlanteShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the interactionPlantePlanteList where description equals to UPDATED_DESCRIPTION
        defaultInteractionPlantePlanteShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInteractionPlantePlantesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        interactionPlantePlanteRepository.saveAndFlush(interactionPlantePlante);

        // Get all the interactionPlantePlanteList where description is not null
        defaultInteractionPlantePlanteShouldBeFound("description.specified=true");

        // Get all the interactionPlantePlanteList where description is null
        defaultInteractionPlantePlanteShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllInteractionPlantePlantesByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        Reference reference = ReferenceResourceIntTest.createEntity(em);
        em.persist(reference);
        em.flush();
        interactionPlantePlante.addReference(reference);
        interactionPlantePlanteRepository.saveAndFlush(interactionPlantePlante);
        Long referenceId = reference.getId();

        // Get all the interactionPlantePlanteList where reference equals to referenceId
        defaultInteractionPlantePlanteShouldBeFound("referenceId.equals=" + referenceId);

        // Get all the interactionPlantePlanteList where reference equals to referenceId + 1
        defaultInteractionPlantePlanteShouldNotBeFound("referenceId.equals=" + (referenceId + 1));
    }


    @Test
    @Transactional
    public void getAllInteractionPlantePlantesByDePlanteIsEqualToSomething() throws Exception {
        // Initialize the database
        Plante dePlante = PlanteResourceIntTest.createEntity(em);
        em.persist(dePlante);
        em.flush();
        interactionPlantePlante.setDePlante(dePlante);
        interactionPlantePlanteRepository.saveAndFlush(interactionPlantePlante);
        Long dePlanteId = dePlante.getId();

        // Get all the interactionPlantePlanteList where dePlante equals to dePlanteId
        defaultInteractionPlantePlanteShouldBeFound("dePlanteId.equals=" + dePlanteId);

        // Get all the interactionPlantePlanteList where dePlante equals to dePlanteId + 1
        defaultInteractionPlantePlanteShouldNotBeFound("dePlanteId.equals=" + (dePlanteId + 1));
    }


    @Test
    @Transactional
    public void getAllInteractionPlantePlantesByVersPlanteIsEqualToSomething() throws Exception {
        // Initialize the database
        Plante versPlante = PlanteResourceIntTest.createEntity(em);
        em.persist(versPlante);
        em.flush();
        interactionPlantePlante.setVersPlante(versPlante);
        interactionPlantePlanteRepository.saveAndFlush(interactionPlantePlante);
        Long versPlanteId = versPlante.getId();

        // Get all the interactionPlantePlanteList where versPlante equals to versPlanteId
        defaultInteractionPlantePlanteShouldBeFound("versPlanteId.equals=" + versPlanteId);

        // Get all the interactionPlantePlanteList where versPlante equals to versPlanteId + 1
        defaultInteractionPlantePlanteShouldNotBeFound("versPlanteId.equals=" + (versPlanteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultInteractionPlantePlanteShouldBeFound(String filter) throws Exception {
        restInteractionPlantePlanteMockMvc.perform(get("/api/interaction-plante-plantes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interactionPlantePlante.getId().intValue())))
            .andExpect(jsonPath("$.[*].notation").value(hasItem(DEFAULT_NOTATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));

        // Check, that the count call also returns 1
        restInteractionPlantePlanteMockMvc.perform(get("/api/interaction-plante-plantes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultInteractionPlantePlanteShouldNotBeFound(String filter) throws Exception {
        restInteractionPlantePlanteMockMvc.perform(get("/api/interaction-plante-plantes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInteractionPlantePlanteMockMvc.perform(get("/api/interaction-plante-plantes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInteractionPlantePlante() throws Exception {
        // Get the interactionPlantePlante
        restInteractionPlantePlanteMockMvc.perform(get("/api/interaction-plante-plantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInteractionPlantePlante() throws Exception {
        // Initialize the database
        interactionPlantePlanteRepository.saveAndFlush(interactionPlantePlante);

        int databaseSizeBeforeUpdate = interactionPlantePlanteRepository.findAll().size();

        // Update the interactionPlantePlante
        InteractionPlantePlante updatedInteractionPlantePlante = interactionPlantePlanteRepository.findById(interactionPlantePlante.getId()).get();
        // Disconnect from session so that the updates on updatedInteractionPlantePlante are not directly saved in db
        em.detach(updatedInteractionPlantePlante);
        updatedInteractionPlantePlante
            .notation(UPDATED_NOTATION)
            .description(UPDATED_DESCRIPTION);
        InteractionPlantePlanteDTO interactionPlantePlanteDTO = interactionPlantePlanteMapper.toDto(updatedInteractionPlantePlante);

        restInteractionPlantePlanteMockMvc.perform(put("/api/interaction-plante-plantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interactionPlantePlanteDTO)))
            .andExpect(status().isOk());

        // Validate the InteractionPlantePlante in the database
        List<InteractionPlantePlante> interactionPlantePlanteList = interactionPlantePlanteRepository.findAll();
        assertThat(interactionPlantePlanteList).hasSize(databaseSizeBeforeUpdate);
        InteractionPlantePlante testInteractionPlantePlante = interactionPlantePlanteList.get(interactionPlantePlanteList.size() - 1);
        assertThat(testInteractionPlantePlante.getNotation()).isEqualTo(UPDATED_NOTATION);
        assertThat(testInteractionPlantePlante.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingInteractionPlantePlante() throws Exception {
        int databaseSizeBeforeUpdate = interactionPlantePlanteRepository.findAll().size();

        // Create the InteractionPlantePlante
        InteractionPlantePlanteDTO interactionPlantePlanteDTO = interactionPlantePlanteMapper.toDto(interactionPlantePlante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInteractionPlantePlanteMockMvc.perform(put("/api/interaction-plante-plantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interactionPlantePlanteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InteractionPlantePlante in the database
        List<InteractionPlantePlante> interactionPlantePlanteList = interactionPlantePlanteRepository.findAll();
        assertThat(interactionPlantePlanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInteractionPlantePlante() throws Exception {
        // Initialize the database
        interactionPlantePlanteRepository.saveAndFlush(interactionPlantePlante);

        int databaseSizeBeforeDelete = interactionPlantePlanteRepository.findAll().size();

        // Get the interactionPlantePlante
        restInteractionPlantePlanteMockMvc.perform(delete("/api/interaction-plante-plantes/{id}", interactionPlantePlante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InteractionPlantePlante> interactionPlantePlanteList = interactionPlantePlanteRepository.findAll();
        assertThat(interactionPlantePlanteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InteractionPlantePlante.class);
        InteractionPlantePlante interactionPlantePlante1 = new InteractionPlantePlante();
        interactionPlantePlante1.setId(1L);
        InteractionPlantePlante interactionPlantePlante2 = new InteractionPlantePlante();
        interactionPlantePlante2.setId(interactionPlantePlante1.getId());
        assertThat(interactionPlantePlante1).isEqualTo(interactionPlantePlante2);
        interactionPlantePlante2.setId(2L);
        assertThat(interactionPlantePlante1).isNotEqualTo(interactionPlantePlante2);
        interactionPlantePlante1.setId(null);
        assertThat(interactionPlantePlante1).isNotEqualTo(interactionPlantePlante2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InteractionPlantePlanteDTO.class);
        InteractionPlantePlanteDTO interactionPlantePlanteDTO1 = new InteractionPlantePlanteDTO();
        interactionPlantePlanteDTO1.setId(1L);
        InteractionPlantePlanteDTO interactionPlantePlanteDTO2 = new InteractionPlantePlanteDTO();
        assertThat(interactionPlantePlanteDTO1).isNotEqualTo(interactionPlantePlanteDTO2);
        interactionPlantePlanteDTO2.setId(interactionPlantePlanteDTO1.getId());
        assertThat(interactionPlantePlanteDTO1).isEqualTo(interactionPlantePlanteDTO2);
        interactionPlantePlanteDTO2.setId(2L);
        assertThat(interactionPlantePlanteDTO1).isNotEqualTo(interactionPlantePlanteDTO2);
        interactionPlantePlanteDTO1.setId(null);
        assertThat(interactionPlantePlanteDTO1).isNotEqualTo(interactionPlantePlanteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(interactionPlantePlanteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(interactionPlantePlanteMapper.fromId(null)).isNull();
    }
}
