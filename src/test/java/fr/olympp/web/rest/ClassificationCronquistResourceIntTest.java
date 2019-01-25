package fr.olympp.web.rest;

import fr.olympp.Backend2App;

import fr.olympp.domain.ClassificationCronquist;
import fr.olympp.domain.Ordre;
import fr.olympp.domain.Famille;
import fr.olympp.domain.Genre;
import fr.olympp.domain.Espece;
import fr.olympp.repository.ClassificationCronquistRepository;
import fr.olympp.service.ClassificationCronquistService;
import fr.olympp.service.dto.ClassificationCronquistDTO;
import fr.olympp.service.mapper.ClassificationCronquistMapper;
import fr.olympp.web.rest.errors.ExceptionTranslator;
import fr.olympp.service.dto.ClassificationCronquistCriteria;
import fr.olympp.service.ClassificationCronquistQueryService;

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
 * Test class for the ClassificationCronquistResource REST controller.
 *
 * @see ClassificationCronquistResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Backend2App.class)
public class ClassificationCronquistResourceIntTest {

    @Autowired
    private ClassificationCronquistRepository classificationCronquistRepository;

    @Autowired
    private ClassificationCronquistMapper classificationCronquistMapper;

    @Autowired
    private ClassificationCronquistService classificationCronquistService;

    @Autowired
    private ClassificationCronquistQueryService classificationCronquistQueryService;

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

    private MockMvc restClassificationCronquistMockMvc;

    private ClassificationCronquist classificationCronquist;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassificationCronquistResource classificationCronquistResource = new ClassificationCronquistResource(classificationCronquistService, classificationCronquistQueryService);
        this.restClassificationCronquistMockMvc = MockMvcBuilders.standaloneSetup(classificationCronquistResource)
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
    public static ClassificationCronquist createEntity(EntityManager em) {
        ClassificationCronquist classificationCronquist = new ClassificationCronquist();
        // Add required entity
        Espece espece = EspeceResourceIntTest.createEntity(em);
        em.persist(espece);
        em.flush();
        classificationCronquist.setEspece(espece);
        return classificationCronquist;
    }

    @Before
    public void initTest() {
        classificationCronquist = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassificationCronquist() throws Exception {
        int databaseSizeBeforeCreate = classificationCronquistRepository.findAll().size();

        // Create the ClassificationCronquist
        ClassificationCronquistDTO classificationCronquistDTO = classificationCronquistMapper.toDto(classificationCronquist);
        restClassificationCronquistMockMvc.perform(post("/api/classification-cronquists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationCronquistDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassificationCronquist in the database
        List<ClassificationCronquist> classificationCronquistList = classificationCronquistRepository.findAll();
        assertThat(classificationCronquistList).hasSize(databaseSizeBeforeCreate + 1);
        ClassificationCronquist testClassificationCronquist = classificationCronquistList.get(classificationCronquistList.size() - 1);
    }

    @Test
    @Transactional
    public void createClassificationCronquistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classificationCronquistRepository.findAll().size();

        // Create the ClassificationCronquist with an existing ID
        classificationCronquist.setId(1L);
        ClassificationCronquistDTO classificationCronquistDTO = classificationCronquistMapper.toDto(classificationCronquist);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassificationCronquistMockMvc.perform(post("/api/classification-cronquists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationCronquistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassificationCronquist in the database
        List<ClassificationCronquist> classificationCronquistList = classificationCronquistRepository.findAll();
        assertThat(classificationCronquistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClassificationCronquists() throws Exception {
        // Initialize the database
        classificationCronquistRepository.saveAndFlush(classificationCronquist);

        // Get all the classificationCronquistList
        restClassificationCronquistMockMvc.perform(get("/api/classification-cronquists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classificationCronquist.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getClassificationCronquist() throws Exception {
        // Initialize the database
        classificationCronquistRepository.saveAndFlush(classificationCronquist);

        // Get the classificationCronquist
        restClassificationCronquistMockMvc.perform(get("/api/classification-cronquists/{id}", classificationCronquist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classificationCronquist.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllClassificationCronquistsByOrdreIsEqualToSomething() throws Exception {
        // Initialize the database
        Ordre ordre = OrdreResourceIntTest.createEntity(em);
        em.persist(ordre);
        em.flush();
        classificationCronquist.setOrdre(ordre);
        classificationCronquistRepository.saveAndFlush(classificationCronquist);
        Long ordreId = ordre.getId();

        // Get all the classificationCronquistList where ordre equals to ordreId
        defaultClassificationCronquistShouldBeFound("ordreId.equals=" + ordreId);

        // Get all the classificationCronquistList where ordre equals to ordreId + 1
        defaultClassificationCronquistShouldNotBeFound("ordreId.equals=" + (ordreId + 1));
    }


    @Test
    @Transactional
    public void getAllClassificationCronquistsByFamilleIsEqualToSomething() throws Exception {
        // Initialize the database
        Famille famille = FamilleResourceIntTest.createEntity(em);
        em.persist(famille);
        em.flush();
        classificationCronquist.setFamille(famille);
        classificationCronquistRepository.saveAndFlush(classificationCronquist);
        Long familleId = famille.getId();

        // Get all the classificationCronquistList where famille equals to familleId
        defaultClassificationCronquistShouldBeFound("familleId.equals=" + familleId);

        // Get all the classificationCronquistList where famille equals to familleId + 1
        defaultClassificationCronquistShouldNotBeFound("familleId.equals=" + (familleId + 1));
    }


    @Test
    @Transactional
    public void getAllClassificationCronquistsByGenreIsEqualToSomething() throws Exception {
        // Initialize the database
        Genre genre = GenreResourceIntTest.createEntity(em);
        em.persist(genre);
        em.flush();
        classificationCronquist.setGenre(genre);
        classificationCronquistRepository.saveAndFlush(classificationCronquist);
        Long genreId = genre.getId();

        // Get all the classificationCronquistList where genre equals to genreId
        defaultClassificationCronquistShouldBeFound("genreId.equals=" + genreId);

        // Get all the classificationCronquistList where genre equals to genreId + 1
        defaultClassificationCronquistShouldNotBeFound("genreId.equals=" + (genreId + 1));
    }


    @Test
    @Transactional
    public void getAllClassificationCronquistsByEspeceIsEqualToSomething() throws Exception {
        // Initialize the database
        Espece espece = EspeceResourceIntTest.createEntity(em);
        em.persist(espece);
        em.flush();
        classificationCronquist.setEspece(espece);
        classificationCronquistRepository.saveAndFlush(classificationCronquist);
        Long especeId = espece.getId();

        // Get all the classificationCronquistList where espece equals to especeId
        defaultClassificationCronquistShouldBeFound("especeId.equals=" + especeId);

        // Get all the classificationCronquistList where espece equals to especeId + 1
        defaultClassificationCronquistShouldNotBeFound("especeId.equals=" + (especeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultClassificationCronquistShouldBeFound(String filter) throws Exception {
        restClassificationCronquistMockMvc.perform(get("/api/classification-cronquists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classificationCronquist.getId().intValue())));

        // Check, that the count call also returns 1
        restClassificationCronquistMockMvc.perform(get("/api/classification-cronquists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultClassificationCronquistShouldNotBeFound(String filter) throws Exception {
        restClassificationCronquistMockMvc.perform(get("/api/classification-cronquists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClassificationCronquistMockMvc.perform(get("/api/classification-cronquists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingClassificationCronquist() throws Exception {
        // Get the classificationCronquist
        restClassificationCronquistMockMvc.perform(get("/api/classification-cronquists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassificationCronquist() throws Exception {
        // Initialize the database
        classificationCronquistRepository.saveAndFlush(classificationCronquist);

        int databaseSizeBeforeUpdate = classificationCronquistRepository.findAll().size();

        // Update the classificationCronquist
        ClassificationCronquist updatedClassificationCronquist = classificationCronquistRepository.findById(classificationCronquist.getId()).get();
        // Disconnect from session so that the updates on updatedClassificationCronquist are not directly saved in db
        em.detach(updatedClassificationCronquist);
        ClassificationCronquistDTO classificationCronquistDTO = classificationCronquistMapper.toDto(updatedClassificationCronquist);

        restClassificationCronquistMockMvc.perform(put("/api/classification-cronquists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationCronquistDTO)))
            .andExpect(status().isOk());

        // Validate the ClassificationCronquist in the database
        List<ClassificationCronquist> classificationCronquistList = classificationCronquistRepository.findAll();
        assertThat(classificationCronquistList).hasSize(databaseSizeBeforeUpdate);
        ClassificationCronquist testClassificationCronquist = classificationCronquistList.get(classificationCronquistList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingClassificationCronquist() throws Exception {
        int databaseSizeBeforeUpdate = classificationCronquistRepository.findAll().size();

        // Create the ClassificationCronquist
        ClassificationCronquistDTO classificationCronquistDTO = classificationCronquistMapper.toDto(classificationCronquist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassificationCronquistMockMvc.perform(put("/api/classification-cronquists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationCronquistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassificationCronquist in the database
        List<ClassificationCronquist> classificationCronquistList = classificationCronquistRepository.findAll();
        assertThat(classificationCronquistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassificationCronquist() throws Exception {
        // Initialize the database
        classificationCronquistRepository.saveAndFlush(classificationCronquist);

        int databaseSizeBeforeDelete = classificationCronquistRepository.findAll().size();

        // Get the classificationCronquist
        restClassificationCronquistMockMvc.perform(delete("/api/classification-cronquists/{id}", classificationCronquist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassificationCronquist> classificationCronquistList = classificationCronquistRepository.findAll();
        assertThat(classificationCronquistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassificationCronquist.class);
        ClassificationCronquist classificationCronquist1 = new ClassificationCronquist();
        classificationCronquist1.setId(1L);
        ClassificationCronquist classificationCronquist2 = new ClassificationCronquist();
        classificationCronquist2.setId(classificationCronquist1.getId());
        assertThat(classificationCronquist1).isEqualTo(classificationCronquist2);
        classificationCronquist2.setId(2L);
        assertThat(classificationCronquist1).isNotEqualTo(classificationCronquist2);
        classificationCronquist1.setId(null);
        assertThat(classificationCronquist1).isNotEqualTo(classificationCronquist2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassificationCronquistDTO.class);
        ClassificationCronquistDTO classificationCronquistDTO1 = new ClassificationCronquistDTO();
        classificationCronquistDTO1.setId(1L);
        ClassificationCronquistDTO classificationCronquistDTO2 = new ClassificationCronquistDTO();
        assertThat(classificationCronquistDTO1).isNotEqualTo(classificationCronquistDTO2);
        classificationCronquistDTO2.setId(classificationCronquistDTO1.getId());
        assertThat(classificationCronquistDTO1).isEqualTo(classificationCronquistDTO2);
        classificationCronquistDTO2.setId(2L);
        assertThat(classificationCronquistDTO1).isNotEqualTo(classificationCronquistDTO2);
        classificationCronquistDTO1.setId(null);
        assertThat(classificationCronquistDTO1).isNotEqualTo(classificationCronquistDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(classificationCronquistMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(classificationCronquistMapper.fromId(null)).isNull();
    }
}
