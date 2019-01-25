package fr.olympp.web.rest;

import fr.olympp.Backend2App;

import fr.olympp.domain.Espece;
import fr.olympp.repository.EspeceRepository;
import fr.olympp.service.EspeceService;
import fr.olympp.service.dto.EspeceDTO;
import fr.olympp.service.mapper.EspeceMapper;
import fr.olympp.web.rest.errors.ExceptionTranslator;
import fr.olympp.service.dto.EspeceCriteria;
import fr.olympp.service.EspeceQueryService;

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
 * Test class for the EspeceResource REST controller.
 *
 * @see EspeceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Backend2App.class)
public class EspeceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private EspeceRepository especeRepository;

    @Autowired
    private EspeceMapper especeMapper;

    @Autowired
    private EspeceService especeService;

    @Autowired
    private EspeceQueryService especeQueryService;

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

    private MockMvc restEspeceMockMvc;

    private Espece espece;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EspeceResource especeResource = new EspeceResource(especeService, especeQueryService);
        this.restEspeceMockMvc = MockMvcBuilders.standaloneSetup(especeResource)
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
    public static Espece createEntity(EntityManager em) {
        Espece espece = new Espece()
            .name(DEFAULT_NAME);
        return espece;
    }

    @Before
    public void initTest() {
        espece = createEntity(em);
    }

    @Test
    @Transactional
    public void createEspece() throws Exception {
        int databaseSizeBeforeCreate = especeRepository.findAll().size();

        // Create the Espece
        EspeceDTO especeDTO = especeMapper.toDto(espece);
        restEspeceMockMvc.perform(post("/api/especes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especeDTO)))
            .andExpect(status().isCreated());

        // Validate the Espece in the database
        List<Espece> especeList = especeRepository.findAll();
        assertThat(especeList).hasSize(databaseSizeBeforeCreate + 1);
        Espece testEspece = especeList.get(especeList.size() - 1);
        assertThat(testEspece.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEspeceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = especeRepository.findAll().size();

        // Create the Espece with an existing ID
        espece.setId(1L);
        EspeceDTO especeDTO = especeMapper.toDto(espece);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspeceMockMvc.perform(post("/api/especes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Espece in the database
        List<Espece> especeList = especeRepository.findAll();
        assertThat(especeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEspeces() throws Exception {
        // Initialize the database
        especeRepository.saveAndFlush(espece);

        // Get all the especeList
        restEspeceMockMvc.perform(get("/api/especes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(espece.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getEspece() throws Exception {
        // Initialize the database
        especeRepository.saveAndFlush(espece);

        // Get the espece
        restEspeceMockMvc.perform(get("/api/especes/{id}", espece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(espece.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllEspecesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        especeRepository.saveAndFlush(espece);

        // Get all the especeList where name equals to DEFAULT_NAME
        defaultEspeceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the especeList where name equals to UPDATED_NAME
        defaultEspeceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEspecesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        especeRepository.saveAndFlush(espece);

        // Get all the especeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEspeceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the especeList where name equals to UPDATED_NAME
        defaultEspeceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEspecesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        especeRepository.saveAndFlush(espece);

        // Get all the especeList where name is not null
        defaultEspeceShouldBeFound("name.specified=true");

        // Get all the especeList where name is null
        defaultEspeceShouldNotBeFound("name.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEspeceShouldBeFound(String filter) throws Exception {
        restEspeceMockMvc.perform(get("/api/especes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(espece.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));

        // Check, that the count call also returns 1
        restEspeceMockMvc.perform(get("/api/especes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEspeceShouldNotBeFound(String filter) throws Exception {
        restEspeceMockMvc.perform(get("/api/especes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEspeceMockMvc.perform(get("/api/especes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEspece() throws Exception {
        // Get the espece
        restEspeceMockMvc.perform(get("/api/especes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEspece() throws Exception {
        // Initialize the database
        especeRepository.saveAndFlush(espece);

        int databaseSizeBeforeUpdate = especeRepository.findAll().size();

        // Update the espece
        Espece updatedEspece = especeRepository.findById(espece.getId()).get();
        // Disconnect from session so that the updates on updatedEspece are not directly saved in db
        em.detach(updatedEspece);
        updatedEspece
            .name(UPDATED_NAME);
        EspeceDTO especeDTO = especeMapper.toDto(updatedEspece);

        restEspeceMockMvc.perform(put("/api/especes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especeDTO)))
            .andExpect(status().isOk());

        // Validate the Espece in the database
        List<Espece> especeList = especeRepository.findAll();
        assertThat(especeList).hasSize(databaseSizeBeforeUpdate);
        Espece testEspece = especeList.get(especeList.size() - 1);
        assertThat(testEspece.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEspece() throws Exception {
        int databaseSizeBeforeUpdate = especeRepository.findAll().size();

        // Create the Espece
        EspeceDTO especeDTO = especeMapper.toDto(espece);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspeceMockMvc.perform(put("/api/especes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Espece in the database
        List<Espece> especeList = especeRepository.findAll();
        assertThat(especeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEspece() throws Exception {
        // Initialize the database
        especeRepository.saveAndFlush(espece);

        int databaseSizeBeforeDelete = especeRepository.findAll().size();

        // Get the espece
        restEspeceMockMvc.perform(delete("/api/especes/{id}", espece.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Espece> especeList = especeRepository.findAll();
        assertThat(especeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Espece.class);
        Espece espece1 = new Espece();
        espece1.setId(1L);
        Espece espece2 = new Espece();
        espece2.setId(espece1.getId());
        assertThat(espece1).isEqualTo(espece2);
        espece2.setId(2L);
        assertThat(espece1).isNotEqualTo(espece2);
        espece1.setId(null);
        assertThat(espece1).isNotEqualTo(espece2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspeceDTO.class);
        EspeceDTO especeDTO1 = new EspeceDTO();
        especeDTO1.setId(1L);
        EspeceDTO especeDTO2 = new EspeceDTO();
        assertThat(especeDTO1).isNotEqualTo(especeDTO2);
        especeDTO2.setId(especeDTO1.getId());
        assertThat(especeDTO1).isEqualTo(especeDTO2);
        especeDTO2.setId(2L);
        assertThat(especeDTO1).isNotEqualTo(especeDTO2);
        especeDTO1.setId(null);
        assertThat(especeDTO1).isNotEqualTo(especeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(especeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(especeMapper.fromId(null)).isNull();
    }
}
