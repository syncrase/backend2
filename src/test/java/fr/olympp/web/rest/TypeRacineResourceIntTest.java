package fr.olympp.web.rest;

import fr.olympp.Backend2App;

import fr.olympp.domain.TypeRacine;
import fr.olympp.repository.TypeRacineRepository;
import fr.olympp.service.TypeRacineService;
import fr.olympp.service.dto.TypeRacineDTO;
import fr.olympp.service.mapper.TypeRacineMapper;
import fr.olympp.web.rest.errors.ExceptionTranslator;
import fr.olympp.service.dto.TypeRacineCriteria;
import fr.olympp.service.TypeRacineQueryService;

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
 * Test class for the TypeRacineResource REST controller.
 *
 * @see TypeRacineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Backend2App.class)
public class TypeRacineResourceIntTest {

    private static final String DEFAULT_TYPE_RACINE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_RACINE = "BBBBBBBBBB";

    @Autowired
    private TypeRacineRepository typeRacineRepository;

    @Autowired
    private TypeRacineMapper typeRacineMapper;

    @Autowired
    private TypeRacineService typeRacineService;

    @Autowired
    private TypeRacineQueryService typeRacineQueryService;

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

    private MockMvc restTypeRacineMockMvc;

    private TypeRacine typeRacine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeRacineResource typeRacineResource = new TypeRacineResource(typeRacineService, typeRacineQueryService);
        this.restTypeRacineMockMvc = MockMvcBuilders.standaloneSetup(typeRacineResource)
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
    public static TypeRacine createEntity(EntityManager em) {
        TypeRacine typeRacine = new TypeRacine()
            .typeRacine(DEFAULT_TYPE_RACINE);
        return typeRacine;
    }

    @Before
    public void initTest() {
        typeRacine = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeRacine() throws Exception {
        int databaseSizeBeforeCreate = typeRacineRepository.findAll().size();

        // Create the TypeRacine
        TypeRacineDTO typeRacineDTO = typeRacineMapper.toDto(typeRacine);
        restTypeRacineMockMvc.perform(post("/api/type-racines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRacineDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeRacine in the database
        List<TypeRacine> typeRacineList = typeRacineRepository.findAll();
        assertThat(typeRacineList).hasSize(databaseSizeBeforeCreate + 1);
        TypeRacine testTypeRacine = typeRacineList.get(typeRacineList.size() - 1);
        assertThat(testTypeRacine.getTypeRacine()).isEqualTo(DEFAULT_TYPE_RACINE);
    }

    @Test
    @Transactional
    public void createTypeRacineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeRacineRepository.findAll().size();

        // Create the TypeRacine with an existing ID
        typeRacine.setId(1L);
        TypeRacineDTO typeRacineDTO = typeRacineMapper.toDto(typeRacine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeRacineMockMvc.perform(post("/api/type-racines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRacineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeRacine in the database
        List<TypeRacine> typeRacineList = typeRacineRepository.findAll();
        assertThat(typeRacineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeRacines() throws Exception {
        // Initialize the database
        typeRacineRepository.saveAndFlush(typeRacine);

        // Get all the typeRacineList
        restTypeRacineMockMvc.perform(get("/api/type-racines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeRacine.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeRacine").value(hasItem(DEFAULT_TYPE_RACINE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeRacine() throws Exception {
        // Initialize the database
        typeRacineRepository.saveAndFlush(typeRacine);

        // Get the typeRacine
        restTypeRacineMockMvc.perform(get("/api/type-racines/{id}", typeRacine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeRacine.getId().intValue()))
            .andExpect(jsonPath("$.typeRacine").value(DEFAULT_TYPE_RACINE.toString()));
    }

    @Test
    @Transactional
    public void getAllTypeRacinesByTypeRacineIsEqualToSomething() throws Exception {
        // Initialize the database
        typeRacineRepository.saveAndFlush(typeRacine);

        // Get all the typeRacineList where typeRacine equals to DEFAULT_TYPE_RACINE
        defaultTypeRacineShouldBeFound("typeRacine.equals=" + DEFAULT_TYPE_RACINE);

        // Get all the typeRacineList where typeRacine equals to UPDATED_TYPE_RACINE
        defaultTypeRacineShouldNotBeFound("typeRacine.equals=" + UPDATED_TYPE_RACINE);
    }

    @Test
    @Transactional
    public void getAllTypeRacinesByTypeRacineIsInShouldWork() throws Exception {
        // Initialize the database
        typeRacineRepository.saveAndFlush(typeRacine);

        // Get all the typeRacineList where typeRacine in DEFAULT_TYPE_RACINE or UPDATED_TYPE_RACINE
        defaultTypeRacineShouldBeFound("typeRacine.in=" + DEFAULT_TYPE_RACINE + "," + UPDATED_TYPE_RACINE);

        // Get all the typeRacineList where typeRacine equals to UPDATED_TYPE_RACINE
        defaultTypeRacineShouldNotBeFound("typeRacine.in=" + UPDATED_TYPE_RACINE);
    }

    @Test
    @Transactional
    public void getAllTypeRacinesByTypeRacineIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeRacineRepository.saveAndFlush(typeRacine);

        // Get all the typeRacineList where typeRacine is not null
        defaultTypeRacineShouldBeFound("typeRacine.specified=true");

        // Get all the typeRacineList where typeRacine is null
        defaultTypeRacineShouldNotBeFound("typeRacine.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTypeRacineShouldBeFound(String filter) throws Exception {
        restTypeRacineMockMvc.perform(get("/api/type-racines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeRacine.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeRacine").value(hasItem(DEFAULT_TYPE_RACINE.toString())));

        // Check, that the count call also returns 1
        restTypeRacineMockMvc.perform(get("/api/type-racines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTypeRacineShouldNotBeFound(String filter) throws Exception {
        restTypeRacineMockMvc.perform(get("/api/type-racines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypeRacineMockMvc.perform(get("/api/type-racines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTypeRacine() throws Exception {
        // Get the typeRacine
        restTypeRacineMockMvc.perform(get("/api/type-racines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeRacine() throws Exception {
        // Initialize the database
        typeRacineRepository.saveAndFlush(typeRacine);

        int databaseSizeBeforeUpdate = typeRacineRepository.findAll().size();

        // Update the typeRacine
        TypeRacine updatedTypeRacine = typeRacineRepository.findById(typeRacine.getId()).get();
        // Disconnect from session so that the updates on updatedTypeRacine are not directly saved in db
        em.detach(updatedTypeRacine);
        updatedTypeRacine
            .typeRacine(UPDATED_TYPE_RACINE);
        TypeRacineDTO typeRacineDTO = typeRacineMapper.toDto(updatedTypeRacine);

        restTypeRacineMockMvc.perform(put("/api/type-racines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRacineDTO)))
            .andExpect(status().isOk());

        // Validate the TypeRacine in the database
        List<TypeRacine> typeRacineList = typeRacineRepository.findAll();
        assertThat(typeRacineList).hasSize(databaseSizeBeforeUpdate);
        TypeRacine testTypeRacine = typeRacineList.get(typeRacineList.size() - 1);
        assertThat(testTypeRacine.getTypeRacine()).isEqualTo(UPDATED_TYPE_RACINE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeRacine() throws Exception {
        int databaseSizeBeforeUpdate = typeRacineRepository.findAll().size();

        // Create the TypeRacine
        TypeRacineDTO typeRacineDTO = typeRacineMapper.toDto(typeRacine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeRacineMockMvc.perform(put("/api/type-racines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeRacineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeRacine in the database
        List<TypeRacine> typeRacineList = typeRacineRepository.findAll();
        assertThat(typeRacineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeRacine() throws Exception {
        // Initialize the database
        typeRacineRepository.saveAndFlush(typeRacine);

        int databaseSizeBeforeDelete = typeRacineRepository.findAll().size();

        // Get the typeRacine
        restTypeRacineMockMvc.perform(delete("/api/type-racines/{id}", typeRacine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeRacine> typeRacineList = typeRacineRepository.findAll();
        assertThat(typeRacineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeRacine.class);
        TypeRacine typeRacine1 = new TypeRacine();
        typeRacine1.setId(1L);
        TypeRacine typeRacine2 = new TypeRacine();
        typeRacine2.setId(typeRacine1.getId());
        assertThat(typeRacine1).isEqualTo(typeRacine2);
        typeRacine2.setId(2L);
        assertThat(typeRacine1).isNotEqualTo(typeRacine2);
        typeRacine1.setId(null);
        assertThat(typeRacine1).isNotEqualTo(typeRacine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeRacineDTO.class);
        TypeRacineDTO typeRacineDTO1 = new TypeRacineDTO();
        typeRacineDTO1.setId(1L);
        TypeRacineDTO typeRacineDTO2 = new TypeRacineDTO();
        assertThat(typeRacineDTO1).isNotEqualTo(typeRacineDTO2);
        typeRacineDTO2.setId(typeRacineDTO1.getId());
        assertThat(typeRacineDTO1).isEqualTo(typeRacineDTO2);
        typeRacineDTO2.setId(2L);
        assertThat(typeRacineDTO1).isNotEqualTo(typeRacineDTO2);
        typeRacineDTO1.setId(null);
        assertThat(typeRacineDTO1).isNotEqualTo(typeRacineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeRacineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeRacineMapper.fromId(null)).isNull();
    }
}
