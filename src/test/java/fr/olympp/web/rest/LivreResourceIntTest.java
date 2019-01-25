package fr.olympp.web.rest;

import fr.olympp.Backend2App;

import fr.olympp.domain.Livre;
import fr.olympp.domain.Reference;
import fr.olympp.repository.LivreRepository;
import fr.olympp.service.LivreService;
import fr.olympp.service.dto.LivreDTO;
import fr.olympp.service.mapper.LivreMapper;
import fr.olympp.web.rest.errors.ExceptionTranslator;
import fr.olympp.service.dto.LivreCriteria;
import fr.olympp.service.LivreQueryService;

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
 * Test class for the LivreResource REST controller.
 *
 * @see LivreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Backend2App.class)
public class LivreResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ISBN = "AAAAAAAAAA";
    private static final String UPDATED_ISBN = "BBBBBBBBBB";

    private static final String DEFAULT_AUTEUR = "AAAAAAAAAA";
    private static final String UPDATED_AUTEUR = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer UPDATED_PAGE = 2;

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private LivreMapper livreMapper;

    @Autowired
    private LivreService livreService;

    @Autowired
    private LivreQueryService livreQueryService;

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

    private MockMvc restLivreMockMvc;

    private Livre livre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LivreResource livreResource = new LivreResource(livreService, livreQueryService);
        this.restLivreMockMvc = MockMvcBuilders.standaloneSetup(livreResource)
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
    public static Livre createEntity(EntityManager em) {
        Livre livre = new Livre()
            .description(DEFAULT_DESCRIPTION)
            .isbn(DEFAULT_ISBN)
            .auteur(DEFAULT_AUTEUR)
            .page(DEFAULT_PAGE);
        return livre;
    }

    @Before
    public void initTest() {
        livre = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivre() throws Exception {
        int databaseSizeBeforeCreate = livreRepository.findAll().size();

        // Create the Livre
        LivreDTO livreDTO = livreMapper.toDto(livre);
        restLivreMockMvc.perform(post("/api/livres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livreDTO)))
            .andExpect(status().isCreated());

        // Validate the Livre in the database
        List<Livre> livreList = livreRepository.findAll();
        assertThat(livreList).hasSize(databaseSizeBeforeCreate + 1);
        Livre testLivre = livreList.get(livreList.size() - 1);
        assertThat(testLivre.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLivre.getIsbn()).isEqualTo(DEFAULT_ISBN);
        assertThat(testLivre.getAuteur()).isEqualTo(DEFAULT_AUTEUR);
        assertThat(testLivre.getPage()).isEqualTo(DEFAULT_PAGE);
    }

    @Test
    @Transactional
    public void createLivreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = livreRepository.findAll().size();

        // Create the Livre with an existing ID
        livre.setId(1L);
        LivreDTO livreDTO = livreMapper.toDto(livre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivreMockMvc.perform(post("/api/livres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Livre in the database
        List<Livre> livreList = livreRepository.findAll();
        assertThat(livreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLivres() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList
        restLivreMockMvc.perform(get("/api/livres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livre.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN.toString())))
            .andExpect(jsonPath("$.[*].auteur").value(hasItem(DEFAULT_AUTEUR.toString())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)));
    }
    
    @Test
    @Transactional
    public void getLivre() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get the livre
        restLivreMockMvc.perform(get("/api/livres/{id}", livre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(livre.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isbn").value(DEFAULT_ISBN.toString()))
            .andExpect(jsonPath("$.auteur").value(DEFAULT_AUTEUR.toString()))
            .andExpect(jsonPath("$.page").value(DEFAULT_PAGE));
    }

    @Test
    @Transactional
    public void getAllLivresByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where description equals to DEFAULT_DESCRIPTION
        defaultLivreShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the livreList where description equals to UPDATED_DESCRIPTION
        defaultLivreShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLivresByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLivreShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the livreList where description equals to UPDATED_DESCRIPTION
        defaultLivreShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLivresByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where description is not null
        defaultLivreShouldBeFound("description.specified=true");

        // Get all the livreList where description is null
        defaultLivreShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivresByIsbnIsEqualToSomething() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where isbn equals to DEFAULT_ISBN
        defaultLivreShouldBeFound("isbn.equals=" + DEFAULT_ISBN);

        // Get all the livreList where isbn equals to UPDATED_ISBN
        defaultLivreShouldNotBeFound("isbn.equals=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllLivresByIsbnIsInShouldWork() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where isbn in DEFAULT_ISBN or UPDATED_ISBN
        defaultLivreShouldBeFound("isbn.in=" + DEFAULT_ISBN + "," + UPDATED_ISBN);

        // Get all the livreList where isbn equals to UPDATED_ISBN
        defaultLivreShouldNotBeFound("isbn.in=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllLivresByIsbnIsNullOrNotNull() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where isbn is not null
        defaultLivreShouldBeFound("isbn.specified=true");

        // Get all the livreList where isbn is null
        defaultLivreShouldNotBeFound("isbn.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivresByAuteurIsEqualToSomething() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where auteur equals to DEFAULT_AUTEUR
        defaultLivreShouldBeFound("auteur.equals=" + DEFAULT_AUTEUR);

        // Get all the livreList where auteur equals to UPDATED_AUTEUR
        defaultLivreShouldNotBeFound("auteur.equals=" + UPDATED_AUTEUR);
    }

    @Test
    @Transactional
    public void getAllLivresByAuteurIsInShouldWork() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where auteur in DEFAULT_AUTEUR or UPDATED_AUTEUR
        defaultLivreShouldBeFound("auteur.in=" + DEFAULT_AUTEUR + "," + UPDATED_AUTEUR);

        // Get all the livreList where auteur equals to UPDATED_AUTEUR
        defaultLivreShouldNotBeFound("auteur.in=" + UPDATED_AUTEUR);
    }

    @Test
    @Transactional
    public void getAllLivresByAuteurIsNullOrNotNull() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where auteur is not null
        defaultLivreShouldBeFound("auteur.specified=true");

        // Get all the livreList where auteur is null
        defaultLivreShouldNotBeFound("auteur.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivresByPageIsEqualToSomething() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where page equals to DEFAULT_PAGE
        defaultLivreShouldBeFound("page.equals=" + DEFAULT_PAGE);

        // Get all the livreList where page equals to UPDATED_PAGE
        defaultLivreShouldNotBeFound("page.equals=" + UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void getAllLivresByPageIsInShouldWork() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where page in DEFAULT_PAGE or UPDATED_PAGE
        defaultLivreShouldBeFound("page.in=" + DEFAULT_PAGE + "," + UPDATED_PAGE);

        // Get all the livreList where page equals to UPDATED_PAGE
        defaultLivreShouldNotBeFound("page.in=" + UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void getAllLivresByPageIsNullOrNotNull() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where page is not null
        defaultLivreShouldBeFound("page.specified=true");

        // Get all the livreList where page is null
        defaultLivreShouldNotBeFound("page.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivresByPageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where page greater than or equals to DEFAULT_PAGE
        defaultLivreShouldBeFound("page.greaterOrEqualThan=" + DEFAULT_PAGE);

        // Get all the livreList where page greater than or equals to UPDATED_PAGE
        defaultLivreShouldNotBeFound("page.greaterOrEqualThan=" + UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void getAllLivresByPageIsLessThanSomething() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livreList where page less than or equals to DEFAULT_PAGE
        defaultLivreShouldNotBeFound("page.lessThan=" + DEFAULT_PAGE);

        // Get all the livreList where page less than or equals to UPDATED_PAGE
        defaultLivreShouldBeFound("page.lessThan=" + UPDATED_PAGE);
    }


    @Test
    @Transactional
    public void getAllLivresByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        Reference reference = ReferenceResourceIntTest.createEntity(em);
        em.persist(reference);
        em.flush();
        livre.setReference(reference);
        reference.setLivre(livre);
        livreRepository.saveAndFlush(livre);
        Long referenceId = reference.getId();

        // Get all the livreList where reference equals to referenceId
        defaultLivreShouldBeFound("referenceId.equals=" + referenceId);

        // Get all the livreList where reference equals to referenceId + 1
        defaultLivreShouldNotBeFound("referenceId.equals=" + (referenceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLivreShouldBeFound(String filter) throws Exception {
        restLivreMockMvc.perform(get("/api/livres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livre.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN.toString())))
            .andExpect(jsonPath("$.[*].auteur").value(hasItem(DEFAULT_AUTEUR.toString())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)));

        // Check, that the count call also returns 1
        restLivreMockMvc.perform(get("/api/livres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLivreShouldNotBeFound(String filter) throws Exception {
        restLivreMockMvc.perform(get("/api/livres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLivreMockMvc.perform(get("/api/livres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLivre() throws Exception {
        // Get the livre
        restLivreMockMvc.perform(get("/api/livres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivre() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        int databaseSizeBeforeUpdate = livreRepository.findAll().size();

        // Update the livre
        Livre updatedLivre = livreRepository.findById(livre.getId()).get();
        // Disconnect from session so that the updates on updatedLivre are not directly saved in db
        em.detach(updatedLivre);
        updatedLivre
            .description(UPDATED_DESCRIPTION)
            .isbn(UPDATED_ISBN)
            .auteur(UPDATED_AUTEUR)
            .page(UPDATED_PAGE);
        LivreDTO livreDTO = livreMapper.toDto(updatedLivre);

        restLivreMockMvc.perform(put("/api/livres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livreDTO)))
            .andExpect(status().isOk());

        // Validate the Livre in the database
        List<Livre> livreList = livreRepository.findAll();
        assertThat(livreList).hasSize(databaseSizeBeforeUpdate);
        Livre testLivre = livreList.get(livreList.size() - 1);
        assertThat(testLivre.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLivre.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testLivre.getAuteur()).isEqualTo(UPDATED_AUTEUR);
        assertThat(testLivre.getPage()).isEqualTo(UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingLivre() throws Exception {
        int databaseSizeBeforeUpdate = livreRepository.findAll().size();

        // Create the Livre
        LivreDTO livreDTO = livreMapper.toDto(livre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivreMockMvc.perform(put("/api/livres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Livre in the database
        List<Livre> livreList = livreRepository.findAll();
        assertThat(livreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLivre() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        int databaseSizeBeforeDelete = livreRepository.findAll().size();

        // Get the livre
        restLivreMockMvc.perform(delete("/api/livres/{id}", livre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Livre> livreList = livreRepository.findAll();
        assertThat(livreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Livre.class);
        Livre livre1 = new Livre();
        livre1.setId(1L);
        Livre livre2 = new Livre();
        livre2.setId(livre1.getId());
        assertThat(livre1).isEqualTo(livre2);
        livre2.setId(2L);
        assertThat(livre1).isNotEqualTo(livre2);
        livre1.setId(null);
        assertThat(livre1).isNotEqualTo(livre2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LivreDTO.class);
        LivreDTO livreDTO1 = new LivreDTO();
        livreDTO1.setId(1L);
        LivreDTO livreDTO2 = new LivreDTO();
        assertThat(livreDTO1).isNotEqualTo(livreDTO2);
        livreDTO2.setId(livreDTO1.getId());
        assertThat(livreDTO1).isEqualTo(livreDTO2);
        livreDTO2.setId(2L);
        assertThat(livreDTO1).isNotEqualTo(livreDTO2);
        livreDTO1.setId(null);
        assertThat(livreDTO1).isNotEqualTo(livreDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(livreMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(livreMapper.fromId(null)).isNull();
    }
}
