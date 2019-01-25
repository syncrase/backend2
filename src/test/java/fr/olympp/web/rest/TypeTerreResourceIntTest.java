package fr.olympp.web.rest;

import fr.olympp.Backend2App;

import fr.olympp.domain.TypeTerre;
import fr.olympp.repository.TypeTerreRepository;
import fr.olympp.service.TypeTerreService;
import fr.olympp.service.dto.TypeTerreDTO;
import fr.olympp.service.mapper.TypeTerreMapper;
import fr.olympp.web.rest.errors.ExceptionTranslator;
import fr.olympp.service.dto.TypeTerreCriteria;
import fr.olympp.service.TypeTerreQueryService;

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
 * Test class for the TypeTerreResource REST controller.
 *
 * @see TypeTerreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Backend2App.class)
public class TypeTerreResourceIntTest {

    private static final String DEFAULT_TYPE_TERRE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_TERRE = "BBBBBBBBBB";

    @Autowired
    private TypeTerreRepository typeTerreRepository;

    @Autowired
    private TypeTerreMapper typeTerreMapper;

    @Autowired
    private TypeTerreService typeTerreService;

    @Autowired
    private TypeTerreQueryService typeTerreQueryService;

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

    private MockMvc restTypeTerreMockMvc;

    private TypeTerre typeTerre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeTerreResource typeTerreResource = new TypeTerreResource(typeTerreService, typeTerreQueryService);
        this.restTypeTerreMockMvc = MockMvcBuilders.standaloneSetup(typeTerreResource)
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
    public static TypeTerre createEntity(EntityManager em) {
        TypeTerre typeTerre = new TypeTerre()
            .typeTerre(DEFAULT_TYPE_TERRE);
        return typeTerre;
    }

    @Before
    public void initTest() {
        typeTerre = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeTerre() throws Exception {
        int databaseSizeBeforeCreate = typeTerreRepository.findAll().size();

        // Create the TypeTerre
        TypeTerreDTO typeTerreDTO = typeTerreMapper.toDto(typeTerre);
        restTypeTerreMockMvc.perform(post("/api/type-terres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTerreDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeTerre in the database
        List<TypeTerre> typeTerreList = typeTerreRepository.findAll();
        assertThat(typeTerreList).hasSize(databaseSizeBeforeCreate + 1);
        TypeTerre testTypeTerre = typeTerreList.get(typeTerreList.size() - 1);
        assertThat(testTypeTerre.getTypeTerre()).isEqualTo(DEFAULT_TYPE_TERRE);
    }

    @Test
    @Transactional
    public void createTypeTerreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeTerreRepository.findAll().size();

        // Create the TypeTerre with an existing ID
        typeTerre.setId(1L);
        TypeTerreDTO typeTerreDTO = typeTerreMapper.toDto(typeTerre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeTerreMockMvc.perform(post("/api/type-terres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTerreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTerre in the database
        List<TypeTerre> typeTerreList = typeTerreRepository.findAll();
        assertThat(typeTerreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeTerres() throws Exception {
        // Initialize the database
        typeTerreRepository.saveAndFlush(typeTerre);

        // Get all the typeTerreList
        restTypeTerreMockMvc.perform(get("/api/type-terres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeTerre.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeTerre").value(hasItem(DEFAULT_TYPE_TERRE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeTerre() throws Exception {
        // Initialize the database
        typeTerreRepository.saveAndFlush(typeTerre);

        // Get the typeTerre
        restTypeTerreMockMvc.perform(get("/api/type-terres/{id}", typeTerre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeTerre.getId().intValue()))
            .andExpect(jsonPath("$.typeTerre").value(DEFAULT_TYPE_TERRE.toString()));
    }

    @Test
    @Transactional
    public void getAllTypeTerresByTypeTerreIsEqualToSomething() throws Exception {
        // Initialize the database
        typeTerreRepository.saveAndFlush(typeTerre);

        // Get all the typeTerreList where typeTerre equals to DEFAULT_TYPE_TERRE
        defaultTypeTerreShouldBeFound("typeTerre.equals=" + DEFAULT_TYPE_TERRE);

        // Get all the typeTerreList where typeTerre equals to UPDATED_TYPE_TERRE
        defaultTypeTerreShouldNotBeFound("typeTerre.equals=" + UPDATED_TYPE_TERRE);
    }

    @Test
    @Transactional
    public void getAllTypeTerresByTypeTerreIsInShouldWork() throws Exception {
        // Initialize the database
        typeTerreRepository.saveAndFlush(typeTerre);

        // Get all the typeTerreList where typeTerre in DEFAULT_TYPE_TERRE or UPDATED_TYPE_TERRE
        defaultTypeTerreShouldBeFound("typeTerre.in=" + DEFAULT_TYPE_TERRE + "," + UPDATED_TYPE_TERRE);

        // Get all the typeTerreList where typeTerre equals to UPDATED_TYPE_TERRE
        defaultTypeTerreShouldNotBeFound("typeTerre.in=" + UPDATED_TYPE_TERRE);
    }

    @Test
    @Transactional
    public void getAllTypeTerresByTypeTerreIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeTerreRepository.saveAndFlush(typeTerre);

        // Get all the typeTerreList where typeTerre is not null
        defaultTypeTerreShouldBeFound("typeTerre.specified=true");

        // Get all the typeTerreList where typeTerre is null
        defaultTypeTerreShouldNotBeFound("typeTerre.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTypeTerreShouldBeFound(String filter) throws Exception {
        restTypeTerreMockMvc.perform(get("/api/type-terres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeTerre.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeTerre").value(hasItem(DEFAULT_TYPE_TERRE.toString())));

        // Check, that the count call also returns 1
        restTypeTerreMockMvc.perform(get("/api/type-terres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTypeTerreShouldNotBeFound(String filter) throws Exception {
        restTypeTerreMockMvc.perform(get("/api/type-terres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypeTerreMockMvc.perform(get("/api/type-terres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTypeTerre() throws Exception {
        // Get the typeTerre
        restTypeTerreMockMvc.perform(get("/api/type-terres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeTerre() throws Exception {
        // Initialize the database
        typeTerreRepository.saveAndFlush(typeTerre);

        int databaseSizeBeforeUpdate = typeTerreRepository.findAll().size();

        // Update the typeTerre
        TypeTerre updatedTypeTerre = typeTerreRepository.findById(typeTerre.getId()).get();
        // Disconnect from session so that the updates on updatedTypeTerre are not directly saved in db
        em.detach(updatedTypeTerre);
        updatedTypeTerre
            .typeTerre(UPDATED_TYPE_TERRE);
        TypeTerreDTO typeTerreDTO = typeTerreMapper.toDto(updatedTypeTerre);

        restTypeTerreMockMvc.perform(put("/api/type-terres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTerreDTO)))
            .andExpect(status().isOk());

        // Validate the TypeTerre in the database
        List<TypeTerre> typeTerreList = typeTerreRepository.findAll();
        assertThat(typeTerreList).hasSize(databaseSizeBeforeUpdate);
        TypeTerre testTypeTerre = typeTerreList.get(typeTerreList.size() - 1);
        assertThat(testTypeTerre.getTypeTerre()).isEqualTo(UPDATED_TYPE_TERRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeTerre() throws Exception {
        int databaseSizeBeforeUpdate = typeTerreRepository.findAll().size();

        // Create the TypeTerre
        TypeTerreDTO typeTerreDTO = typeTerreMapper.toDto(typeTerre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeTerreMockMvc.perform(put("/api/type-terres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTerreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTerre in the database
        List<TypeTerre> typeTerreList = typeTerreRepository.findAll();
        assertThat(typeTerreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeTerre() throws Exception {
        // Initialize the database
        typeTerreRepository.saveAndFlush(typeTerre);

        int databaseSizeBeforeDelete = typeTerreRepository.findAll().size();

        // Get the typeTerre
        restTypeTerreMockMvc.perform(delete("/api/type-terres/{id}", typeTerre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeTerre> typeTerreList = typeTerreRepository.findAll();
        assertThat(typeTerreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeTerre.class);
        TypeTerre typeTerre1 = new TypeTerre();
        typeTerre1.setId(1L);
        TypeTerre typeTerre2 = new TypeTerre();
        typeTerre2.setId(typeTerre1.getId());
        assertThat(typeTerre1).isEqualTo(typeTerre2);
        typeTerre2.setId(2L);
        assertThat(typeTerre1).isNotEqualTo(typeTerre2);
        typeTerre1.setId(null);
        assertThat(typeTerre1).isNotEqualTo(typeTerre2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeTerreDTO.class);
        TypeTerreDTO typeTerreDTO1 = new TypeTerreDTO();
        typeTerreDTO1.setId(1L);
        TypeTerreDTO typeTerreDTO2 = new TypeTerreDTO();
        assertThat(typeTerreDTO1).isNotEqualTo(typeTerreDTO2);
        typeTerreDTO2.setId(typeTerreDTO1.getId());
        assertThat(typeTerreDTO1).isEqualTo(typeTerreDTO2);
        typeTerreDTO2.setId(2L);
        assertThat(typeTerreDTO1).isNotEqualTo(typeTerreDTO2);
        typeTerreDTO1.setId(null);
        assertThat(typeTerreDTO1).isNotEqualTo(typeTerreDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeTerreMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeTerreMapper.fromId(null)).isNull();
    }
}
