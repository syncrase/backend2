package fr.olympp.web.rest;

import fr.olympp.Backend2App;

import fr.olympp.domain.PageWeb;
import fr.olympp.domain.Reference;
import fr.olympp.repository.PageWebRepository;
import fr.olympp.service.PageWebService;
import fr.olympp.service.dto.PageWebDTO;
import fr.olympp.service.mapper.PageWebMapper;
import fr.olympp.web.rest.errors.ExceptionTranslator;
import fr.olympp.service.dto.PageWebCriteria;
import fr.olympp.service.PageWebQueryService;

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
 * Test class for the PageWebResource REST controller.
 *
 * @see PageWebResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Backend2App.class)
public class PageWebResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private PageWebRepository pageWebRepository;

    @Autowired
    private PageWebMapper pageWebMapper;

    @Autowired
    private PageWebService pageWebService;

    @Autowired
    private PageWebQueryService pageWebQueryService;

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

    private MockMvc restPageWebMockMvc;

    private PageWeb pageWeb;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PageWebResource pageWebResource = new PageWebResource(pageWebService, pageWebQueryService);
        this.restPageWebMockMvc = MockMvcBuilders.standaloneSetup(pageWebResource)
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
    public static PageWeb createEntity(EntityManager em) {
        PageWeb pageWeb = new PageWeb()
            .description(DEFAULT_DESCRIPTION)
            .url(DEFAULT_URL);
        return pageWeb;
    }

    @Before
    public void initTest() {
        pageWeb = createEntity(em);
    }

    @Test
    @Transactional
    public void createPageWeb() throws Exception {
        int databaseSizeBeforeCreate = pageWebRepository.findAll().size();

        // Create the PageWeb
        PageWebDTO pageWebDTO = pageWebMapper.toDto(pageWeb);
        restPageWebMockMvc.perform(post("/api/page-webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageWebDTO)))
            .andExpect(status().isCreated());

        // Validate the PageWeb in the database
        List<PageWeb> pageWebList = pageWebRepository.findAll();
        assertThat(pageWebList).hasSize(databaseSizeBeforeCreate + 1);
        PageWeb testPageWeb = pageWebList.get(pageWebList.size() - 1);
        assertThat(testPageWeb.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPageWeb.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createPageWebWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pageWebRepository.findAll().size();

        // Create the PageWeb with an existing ID
        pageWeb.setId(1L);
        PageWebDTO pageWebDTO = pageWebMapper.toDto(pageWeb);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageWebMockMvc.perform(post("/api/page-webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageWebDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PageWeb in the database
        List<PageWeb> pageWebList = pageWebRepository.findAll();
        assertThat(pageWebList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPageWebs() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        // Get all the pageWebList
        restPageWebMockMvc.perform(get("/api/page-webs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageWeb.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getPageWeb() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        // Get the pageWeb
        restPageWebMockMvc.perform(get("/api/page-webs/{id}", pageWeb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pageWeb.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getAllPageWebsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        // Get all the pageWebList where description equals to DEFAULT_DESCRIPTION
        defaultPageWebShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the pageWebList where description equals to UPDATED_DESCRIPTION
        defaultPageWebShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPageWebsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        // Get all the pageWebList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPageWebShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the pageWebList where description equals to UPDATED_DESCRIPTION
        defaultPageWebShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPageWebsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        // Get all the pageWebList where description is not null
        defaultPageWebShouldBeFound("description.specified=true");

        // Get all the pageWebList where description is null
        defaultPageWebShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllPageWebsByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        // Get all the pageWebList where url equals to DEFAULT_URL
        defaultPageWebShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the pageWebList where url equals to UPDATED_URL
        defaultPageWebShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllPageWebsByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        // Get all the pageWebList where url in DEFAULT_URL or UPDATED_URL
        defaultPageWebShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the pageWebList where url equals to UPDATED_URL
        defaultPageWebShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllPageWebsByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        // Get all the pageWebList where url is not null
        defaultPageWebShouldBeFound("url.specified=true");

        // Get all the pageWebList where url is null
        defaultPageWebShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    public void getAllPageWebsByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        Reference reference = ReferenceResourceIntTest.createEntity(em);
        em.persist(reference);
        em.flush();
        pageWeb.setReference(reference);
        reference.setPageWeb(pageWeb);
        pageWebRepository.saveAndFlush(pageWeb);
        Long referenceId = reference.getId();

        // Get all the pageWebList where reference equals to referenceId
        defaultPageWebShouldBeFound("referenceId.equals=" + referenceId);

        // Get all the pageWebList where reference equals to referenceId + 1
        defaultPageWebShouldNotBeFound("referenceId.equals=" + (referenceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPageWebShouldBeFound(String filter) throws Exception {
        restPageWebMockMvc.perform(get("/api/page-webs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageWeb.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));

        // Check, that the count call also returns 1
        restPageWebMockMvc.perform(get("/api/page-webs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPageWebShouldNotBeFound(String filter) throws Exception {
        restPageWebMockMvc.perform(get("/api/page-webs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPageWebMockMvc.perform(get("/api/page-webs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPageWeb() throws Exception {
        // Get the pageWeb
        restPageWebMockMvc.perform(get("/api/page-webs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePageWeb() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        int databaseSizeBeforeUpdate = pageWebRepository.findAll().size();

        // Update the pageWeb
        PageWeb updatedPageWeb = pageWebRepository.findById(pageWeb.getId()).get();
        // Disconnect from session so that the updates on updatedPageWeb are not directly saved in db
        em.detach(updatedPageWeb);
        updatedPageWeb
            .description(UPDATED_DESCRIPTION)
            .url(UPDATED_URL);
        PageWebDTO pageWebDTO = pageWebMapper.toDto(updatedPageWeb);

        restPageWebMockMvc.perform(put("/api/page-webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageWebDTO)))
            .andExpect(status().isOk());

        // Validate the PageWeb in the database
        List<PageWeb> pageWebList = pageWebRepository.findAll();
        assertThat(pageWebList).hasSize(databaseSizeBeforeUpdate);
        PageWeb testPageWeb = pageWebList.get(pageWebList.size() - 1);
        assertThat(testPageWeb.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPageWeb.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingPageWeb() throws Exception {
        int databaseSizeBeforeUpdate = pageWebRepository.findAll().size();

        // Create the PageWeb
        PageWebDTO pageWebDTO = pageWebMapper.toDto(pageWeb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageWebMockMvc.perform(put("/api/page-webs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageWebDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PageWeb in the database
        List<PageWeb> pageWebList = pageWebRepository.findAll();
        assertThat(pageWebList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePageWeb() throws Exception {
        // Initialize the database
        pageWebRepository.saveAndFlush(pageWeb);

        int databaseSizeBeforeDelete = pageWebRepository.findAll().size();

        // Get the pageWeb
        restPageWebMockMvc.perform(delete("/api/page-webs/{id}", pageWeb.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PageWeb> pageWebList = pageWebRepository.findAll();
        assertThat(pageWebList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageWeb.class);
        PageWeb pageWeb1 = new PageWeb();
        pageWeb1.setId(1L);
        PageWeb pageWeb2 = new PageWeb();
        pageWeb2.setId(pageWeb1.getId());
        assertThat(pageWeb1).isEqualTo(pageWeb2);
        pageWeb2.setId(2L);
        assertThat(pageWeb1).isNotEqualTo(pageWeb2);
        pageWeb1.setId(null);
        assertThat(pageWeb1).isNotEqualTo(pageWeb2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageWebDTO.class);
        PageWebDTO pageWebDTO1 = new PageWebDTO();
        pageWebDTO1.setId(1L);
        PageWebDTO pageWebDTO2 = new PageWebDTO();
        assertThat(pageWebDTO1).isNotEqualTo(pageWebDTO2);
        pageWebDTO2.setId(pageWebDTO1.getId());
        assertThat(pageWebDTO1).isEqualTo(pageWebDTO2);
        pageWebDTO2.setId(2L);
        assertThat(pageWebDTO1).isNotEqualTo(pageWebDTO2);
        pageWebDTO1.setId(null);
        assertThat(pageWebDTO1).isNotEqualTo(pageWebDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pageWebMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pageWebMapper.fromId(null)).isNull();
    }
}
