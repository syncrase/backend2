package fr.olympp.web.rest;

import fr.olympp.Backend2App;

import fr.olympp.domain.Plante;
import fr.olympp.domain.ClassificationCronquist;
import fr.olympp.domain.Strate;
import fr.olympp.domain.VitesseCroissance;
import fr.olympp.domain.Ensoleillement;
import fr.olympp.domain.RichesseSol;
import fr.olympp.domain.TypeTerre;
import fr.olympp.domain.TypeFeuillage;
import fr.olympp.domain.TypeRacine;
import fr.olympp.domain.PlantCommonName;
import fr.olympp.repository.PlanteRepository;
import fr.olympp.service.PlanteService;
import fr.olympp.service.dto.PlanteDTO;
import fr.olympp.service.mapper.PlanteMapper;
import fr.olympp.web.rest.errors.ExceptionTranslator;
import fr.olympp.service.dto.PlanteCriteria;
import fr.olympp.service.PlanteQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static fr.olympp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PlanteResource REST controller.
 *
 * @see PlanteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Backend2App.class)
public class PlanteResourceIntTest {

    private static final String DEFAULT_PH_MIN = "";
    private static final String UPDATED_PH_MIN = ",5";

    private static final String DEFAULT_PH_MAX = "8,7";
    private static final String UPDATED_PH_MAX = "2,0";

    private static final Integer DEFAULT_TEMP_MIN = 1;
    private static final Integer UPDATED_TEMP_MIN = 2;

    private static final Integer DEFAULT_TEMP_MAX = 1;
    private static final Integer UPDATED_TEMP_MAX = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PlanteRepository planteRepository;

    @Mock
    private PlanteRepository planteRepositoryMock;

    @Autowired
    private PlanteMapper planteMapper;

    @Mock
    private PlanteService planteServiceMock;

    @Autowired
    private PlanteService planteService;

    @Autowired
    private PlanteQueryService planteQueryService;

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

    private MockMvc restPlanteMockMvc;

    private Plante plante;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanteResource planteResource = new PlanteResource(planteService, planteQueryService);
        this.restPlanteMockMvc = MockMvcBuilders.standaloneSetup(planteResource)
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
    public static Plante createEntity(EntityManager em) {
        Plante plante = new Plante()
            .phMin(DEFAULT_PH_MIN)
            .phMax(DEFAULT_PH_MAX)
            .tempMin(DEFAULT_TEMP_MIN)
            .tempMax(DEFAULT_TEMP_MAX)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        ClassificationCronquist classificationCronquist = ClassificationCronquistResourceIntTest.createEntity(em);
        em.persist(classificationCronquist);
        em.flush();
        plante.setClassificationCronquist(classificationCronquist);
        return plante;
    }

    @Before
    public void initTest() {
        plante = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlante() throws Exception {
        int databaseSizeBeforeCreate = planteRepository.findAll().size();

        // Create the Plante
        PlanteDTO planteDTO = planteMapper.toDto(plante);
        restPlanteMockMvc.perform(post("/api/plantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planteDTO)))
            .andExpect(status().isCreated());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeCreate + 1);
        Plante testPlante = planteList.get(planteList.size() - 1);
        assertThat(testPlante.getPhMin()).isEqualTo(DEFAULT_PH_MIN);
        assertThat(testPlante.getPhMax()).isEqualTo(DEFAULT_PH_MAX);
        assertThat(testPlante.getTempMin()).isEqualTo(DEFAULT_TEMP_MIN);
        assertThat(testPlante.getTempMax()).isEqualTo(DEFAULT_TEMP_MAX);
        assertThat(testPlante.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPlanteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planteRepository.findAll().size();

        // Create the Plante with an existing ID
        plante.setId(1L);
        PlanteDTO planteDTO = planteMapper.toDto(plante);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanteMockMvc.perform(post("/api/plantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlantes() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList
        restPlanteMockMvc.perform(get("/api/plantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plante.getId().intValue())))
            .andExpect(jsonPath("$.[*].phMin").value(hasItem(DEFAULT_PH_MIN.toString())))
            .andExpect(jsonPath("$.[*].phMax").value(hasItem(DEFAULT_PH_MAX.toString())))
            .andExpect(jsonPath("$.[*].tempMin").value(hasItem(DEFAULT_TEMP_MIN)))
            .andExpect(jsonPath("$.[*].tempMax").value(hasItem(DEFAULT_TEMP_MAX)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPlantesWithEagerRelationshipsIsEnabled() throws Exception {
        PlanteResource planteResource = new PlanteResource(planteServiceMock, planteQueryService);
        when(planteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPlanteMockMvc = MockMvcBuilders.standaloneSetup(planteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPlanteMockMvc.perform(get("/api/plantes?eagerload=true"))
        .andExpect(status().isOk());

        verify(planteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPlantesWithEagerRelationshipsIsNotEnabled() throws Exception {
        PlanteResource planteResource = new PlanteResource(planteServiceMock, planteQueryService);
            when(planteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPlanteMockMvc = MockMvcBuilders.standaloneSetup(planteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPlanteMockMvc.perform(get("/api/plantes?eagerload=true"))
        .andExpect(status().isOk());

            verify(planteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPlante() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get the plante
        restPlanteMockMvc.perform(get("/api/plantes/{id}", plante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plante.getId().intValue()))
            .andExpect(jsonPath("$.phMin").value(DEFAULT_PH_MIN.toString()))
            .andExpect(jsonPath("$.phMax").value(DEFAULT_PH_MAX.toString()))
            .andExpect(jsonPath("$.tempMin").value(DEFAULT_TEMP_MIN))
            .andExpect(jsonPath("$.tempMax").value(DEFAULT_TEMP_MAX))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllPlantesByPhMinIsEqualToSomething() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where phMin equals to DEFAULT_PH_MIN
        defaultPlanteShouldBeFound("phMin.equals=" + DEFAULT_PH_MIN);

        // Get all the planteList where phMin equals to UPDATED_PH_MIN
        defaultPlanteShouldNotBeFound("phMin.equals=" + UPDATED_PH_MIN);
    }

    @Test
    @Transactional
    public void getAllPlantesByPhMinIsInShouldWork() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where phMin in DEFAULT_PH_MIN or UPDATED_PH_MIN
        defaultPlanteShouldBeFound("phMin.in=" + DEFAULT_PH_MIN + "," + UPDATED_PH_MIN);

        // Get all the planteList where phMin equals to UPDATED_PH_MIN
        defaultPlanteShouldNotBeFound("phMin.in=" + UPDATED_PH_MIN);
    }

    @Test
    @Transactional
    public void getAllPlantesByPhMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where phMin is not null
        defaultPlanteShouldBeFound("phMin.specified=true");

        // Get all the planteList where phMin is null
        defaultPlanteShouldNotBeFound("phMin.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantesByPhMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where phMax equals to DEFAULT_PH_MAX
        defaultPlanteShouldBeFound("phMax.equals=" + DEFAULT_PH_MAX);

        // Get all the planteList where phMax equals to UPDATED_PH_MAX
        defaultPlanteShouldNotBeFound("phMax.equals=" + UPDATED_PH_MAX);
    }

    @Test
    @Transactional
    public void getAllPlantesByPhMaxIsInShouldWork() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where phMax in DEFAULT_PH_MAX or UPDATED_PH_MAX
        defaultPlanteShouldBeFound("phMax.in=" + DEFAULT_PH_MAX + "," + UPDATED_PH_MAX);

        // Get all the planteList where phMax equals to UPDATED_PH_MAX
        defaultPlanteShouldNotBeFound("phMax.in=" + UPDATED_PH_MAX);
    }

    @Test
    @Transactional
    public void getAllPlantesByPhMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where phMax is not null
        defaultPlanteShouldBeFound("phMax.specified=true");

        // Get all the planteList where phMax is null
        defaultPlanteShouldNotBeFound("phMax.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantesByTempMinIsEqualToSomething() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where tempMin equals to DEFAULT_TEMP_MIN
        defaultPlanteShouldBeFound("tempMin.equals=" + DEFAULT_TEMP_MIN);

        // Get all the planteList where tempMin equals to UPDATED_TEMP_MIN
        defaultPlanteShouldNotBeFound("tempMin.equals=" + UPDATED_TEMP_MIN);
    }

    @Test
    @Transactional
    public void getAllPlantesByTempMinIsInShouldWork() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where tempMin in DEFAULT_TEMP_MIN or UPDATED_TEMP_MIN
        defaultPlanteShouldBeFound("tempMin.in=" + DEFAULT_TEMP_MIN + "," + UPDATED_TEMP_MIN);

        // Get all the planteList where tempMin equals to UPDATED_TEMP_MIN
        defaultPlanteShouldNotBeFound("tempMin.in=" + UPDATED_TEMP_MIN);
    }

    @Test
    @Transactional
    public void getAllPlantesByTempMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where tempMin is not null
        defaultPlanteShouldBeFound("tempMin.specified=true");

        // Get all the planteList where tempMin is null
        defaultPlanteShouldNotBeFound("tempMin.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantesByTempMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where tempMin greater than or equals to DEFAULT_TEMP_MIN
        defaultPlanteShouldBeFound("tempMin.greaterOrEqualThan=" + DEFAULT_TEMP_MIN);

        // Get all the planteList where tempMin greater than or equals to UPDATED_TEMP_MIN
        defaultPlanteShouldNotBeFound("tempMin.greaterOrEqualThan=" + UPDATED_TEMP_MIN);
    }

    @Test
    @Transactional
    public void getAllPlantesByTempMinIsLessThanSomething() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where tempMin less than or equals to DEFAULT_TEMP_MIN
        defaultPlanteShouldNotBeFound("tempMin.lessThan=" + DEFAULT_TEMP_MIN);

        // Get all the planteList where tempMin less than or equals to UPDATED_TEMP_MIN
        defaultPlanteShouldBeFound("tempMin.lessThan=" + UPDATED_TEMP_MIN);
    }


    @Test
    @Transactional
    public void getAllPlantesByTempMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where tempMax equals to DEFAULT_TEMP_MAX
        defaultPlanteShouldBeFound("tempMax.equals=" + DEFAULT_TEMP_MAX);

        // Get all the planteList where tempMax equals to UPDATED_TEMP_MAX
        defaultPlanteShouldNotBeFound("tempMax.equals=" + UPDATED_TEMP_MAX);
    }

    @Test
    @Transactional
    public void getAllPlantesByTempMaxIsInShouldWork() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where tempMax in DEFAULT_TEMP_MAX or UPDATED_TEMP_MAX
        defaultPlanteShouldBeFound("tempMax.in=" + DEFAULT_TEMP_MAX + "," + UPDATED_TEMP_MAX);

        // Get all the planteList where tempMax equals to UPDATED_TEMP_MAX
        defaultPlanteShouldNotBeFound("tempMax.in=" + UPDATED_TEMP_MAX);
    }

    @Test
    @Transactional
    public void getAllPlantesByTempMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where tempMax is not null
        defaultPlanteShouldBeFound("tempMax.specified=true");

        // Get all the planteList where tempMax is null
        defaultPlanteShouldNotBeFound("tempMax.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantesByTempMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where tempMax greater than or equals to DEFAULT_TEMP_MAX
        defaultPlanteShouldBeFound("tempMax.greaterOrEqualThan=" + DEFAULT_TEMP_MAX);

        // Get all the planteList where tempMax greater than or equals to UPDATED_TEMP_MAX
        defaultPlanteShouldNotBeFound("tempMax.greaterOrEqualThan=" + UPDATED_TEMP_MAX);
    }

    @Test
    @Transactional
    public void getAllPlantesByTempMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where tempMax less than or equals to DEFAULT_TEMP_MAX
        defaultPlanteShouldNotBeFound("tempMax.lessThan=" + DEFAULT_TEMP_MAX);

        // Get all the planteList where tempMax less than or equals to UPDATED_TEMP_MAX
        defaultPlanteShouldBeFound("tempMax.lessThan=" + UPDATED_TEMP_MAX);
    }


    @Test
    @Transactional
    public void getAllPlantesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where description equals to DEFAULT_DESCRIPTION
        defaultPlanteShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the planteList where description equals to UPDATED_DESCRIPTION
        defaultPlanteShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPlantesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPlanteShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the planteList where description equals to UPDATED_DESCRIPTION
        defaultPlanteShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPlantesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        // Get all the planteList where description is not null
        defaultPlanteShouldBeFound("description.specified=true");

        // Get all the planteList where description is null
        defaultPlanteShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlantesByClassificationCronquistIsEqualToSomething() throws Exception {
        // Initialize the database
        ClassificationCronquist classificationCronquist = ClassificationCronquistResourceIntTest.createEntity(em);
        em.persist(classificationCronquist);
        em.flush();
        plante.setClassificationCronquist(classificationCronquist);
        planteRepository.saveAndFlush(plante);
        Long classificationCronquistId = classificationCronquist.getId();

        // Get all the planteList where classificationCronquist equals to classificationCronquistId
        defaultPlanteShouldBeFound("classificationCronquistId.equals=" + classificationCronquistId);

        // Get all the planteList where classificationCronquist equals to classificationCronquistId + 1
        defaultPlanteShouldNotBeFound("classificationCronquistId.equals=" + (classificationCronquistId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantesByStrateIsEqualToSomething() throws Exception {
        // Initialize the database
        Strate strate = StrateResourceIntTest.createEntity(em);
        em.persist(strate);
        em.flush();
        plante.setStrate(strate);
        planteRepository.saveAndFlush(plante);
        Long strateId = strate.getId();

        // Get all the planteList where strate equals to strateId
        defaultPlanteShouldBeFound("strateId.equals=" + strateId);

        // Get all the planteList where strate equals to strateId + 1
        defaultPlanteShouldNotBeFound("strateId.equals=" + (strateId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantesByVitesseCroissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        VitesseCroissance vitesseCroissance = VitesseCroissanceResourceIntTest.createEntity(em);
        em.persist(vitesseCroissance);
        em.flush();
        plante.setVitesseCroissance(vitesseCroissance);
        planteRepository.saveAndFlush(plante);
        Long vitesseCroissanceId = vitesseCroissance.getId();

        // Get all the planteList where vitesseCroissance equals to vitesseCroissanceId
        defaultPlanteShouldBeFound("vitesseCroissanceId.equals=" + vitesseCroissanceId);

        // Get all the planteList where vitesseCroissance equals to vitesseCroissanceId + 1
        defaultPlanteShouldNotBeFound("vitesseCroissanceId.equals=" + (vitesseCroissanceId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantesByEnsoleillementIsEqualToSomething() throws Exception {
        // Initialize the database
        Ensoleillement ensoleillement = EnsoleillementResourceIntTest.createEntity(em);
        em.persist(ensoleillement);
        em.flush();
        plante.setEnsoleillement(ensoleillement);
        planteRepository.saveAndFlush(plante);
        Long ensoleillementId = ensoleillement.getId();

        // Get all the planteList where ensoleillement equals to ensoleillementId
        defaultPlanteShouldBeFound("ensoleillementId.equals=" + ensoleillementId);

        // Get all the planteList where ensoleillement equals to ensoleillementId + 1
        defaultPlanteShouldNotBeFound("ensoleillementId.equals=" + (ensoleillementId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantesByRichesseSolIsEqualToSomething() throws Exception {
        // Initialize the database
        RichesseSol richesseSol = RichesseSolResourceIntTest.createEntity(em);
        em.persist(richesseSol);
        em.flush();
        plante.setRichesseSol(richesseSol);
        planteRepository.saveAndFlush(plante);
        Long richesseSolId = richesseSol.getId();

        // Get all the planteList where richesseSol equals to richesseSolId
        defaultPlanteShouldBeFound("richesseSolId.equals=" + richesseSolId);

        // Get all the planteList where richesseSol equals to richesseSolId + 1
        defaultPlanteShouldNotBeFound("richesseSolId.equals=" + (richesseSolId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantesByTypeTerreIsEqualToSomething() throws Exception {
        // Initialize the database
        TypeTerre typeTerre = TypeTerreResourceIntTest.createEntity(em);
        em.persist(typeTerre);
        em.flush();
        plante.setTypeTerre(typeTerre);
        planteRepository.saveAndFlush(plante);
        Long typeTerreId = typeTerre.getId();

        // Get all the planteList where typeTerre equals to typeTerreId
        defaultPlanteShouldBeFound("typeTerreId.equals=" + typeTerreId);

        // Get all the planteList where typeTerre equals to typeTerreId + 1
        defaultPlanteShouldNotBeFound("typeTerreId.equals=" + (typeTerreId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantesByTypeFeuillageIsEqualToSomething() throws Exception {
        // Initialize the database
        TypeFeuillage typeFeuillage = TypeFeuillageResourceIntTest.createEntity(em);
        em.persist(typeFeuillage);
        em.flush();
        plante.setTypeFeuillage(typeFeuillage);
        planteRepository.saveAndFlush(plante);
        Long typeFeuillageId = typeFeuillage.getId();

        // Get all the planteList where typeFeuillage equals to typeFeuillageId
        defaultPlanteShouldBeFound("typeFeuillageId.equals=" + typeFeuillageId);

        // Get all the planteList where typeFeuillage equals to typeFeuillageId + 1
        defaultPlanteShouldNotBeFound("typeFeuillageId.equals=" + (typeFeuillageId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantesByTypeRacineIsEqualToSomething() throws Exception {
        // Initialize the database
        TypeRacine typeRacine = TypeRacineResourceIntTest.createEntity(em);
        em.persist(typeRacine);
        em.flush();
        plante.setTypeRacine(typeRacine);
        planteRepository.saveAndFlush(plante);
        Long typeRacineId = typeRacine.getId();

        // Get all the planteList where typeRacine equals to typeRacineId
        defaultPlanteShouldBeFound("typeRacineId.equals=" + typeRacineId);

        // Get all the planteList where typeRacine equals to typeRacineId + 1
        defaultPlanteShouldNotBeFound("typeRacineId.equals=" + (typeRacineId + 1));
    }


    @Test
    @Transactional
    public void getAllPlantesByPlantCommonNameIsEqualToSomething() throws Exception {
        // Initialize the database
        PlantCommonName plantCommonName = PlantCommonNameResourceIntTest.createEntity(em);
        em.persist(plantCommonName);
        em.flush();
        plante.addPlantCommonName(plantCommonName);
        planteRepository.saveAndFlush(plante);
        Long plantCommonNameId = plantCommonName.getId();

        // Get all the planteList where plantCommonName equals to plantCommonNameId
        defaultPlanteShouldBeFound("plantCommonNameId.equals=" + plantCommonNameId);

        // Get all the planteList where plantCommonName equals to plantCommonNameId + 1
        defaultPlanteShouldNotBeFound("plantCommonNameId.equals=" + (plantCommonNameId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPlanteShouldBeFound(String filter) throws Exception {
        restPlanteMockMvc.perform(get("/api/plantes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plante.getId().intValue())))
            .andExpect(jsonPath("$.[*].phMin").value(hasItem(DEFAULT_PH_MIN.toString())))
            .andExpect(jsonPath("$.[*].phMax").value(hasItem(DEFAULT_PH_MAX.toString())))
            .andExpect(jsonPath("$.[*].tempMin").value(hasItem(DEFAULT_TEMP_MIN)))
            .andExpect(jsonPath("$.[*].tempMax").value(hasItem(DEFAULT_TEMP_MAX)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));

        // Check, that the count call also returns 1
        restPlanteMockMvc.perform(get("/api/plantes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPlanteShouldNotBeFound(String filter) throws Exception {
        restPlanteMockMvc.perform(get("/api/plantes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanteMockMvc.perform(get("/api/plantes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPlante() throws Exception {
        // Get the plante
        restPlanteMockMvc.perform(get("/api/plantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlante() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        int databaseSizeBeforeUpdate = planteRepository.findAll().size();

        // Update the plante
        Plante updatedPlante = planteRepository.findById(plante.getId()).get();
        // Disconnect from session so that the updates on updatedPlante are not directly saved in db
        em.detach(updatedPlante);
        updatedPlante
            .phMin(UPDATED_PH_MIN)
            .phMax(UPDATED_PH_MAX)
            .tempMin(UPDATED_TEMP_MIN)
            .tempMax(UPDATED_TEMP_MAX)
            .description(UPDATED_DESCRIPTION);
        PlanteDTO planteDTO = planteMapper.toDto(updatedPlante);

        restPlanteMockMvc.perform(put("/api/plantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planteDTO)))
            .andExpect(status().isOk());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeUpdate);
        Plante testPlante = planteList.get(planteList.size() - 1);
        assertThat(testPlante.getPhMin()).isEqualTo(UPDATED_PH_MIN);
        assertThat(testPlante.getPhMax()).isEqualTo(UPDATED_PH_MAX);
        assertThat(testPlante.getTempMin()).isEqualTo(UPDATED_TEMP_MIN);
        assertThat(testPlante.getTempMax()).isEqualTo(UPDATED_TEMP_MAX);
        assertThat(testPlante.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPlante() throws Exception {
        int databaseSizeBeforeUpdate = planteRepository.findAll().size();

        // Create the Plante
        PlanteDTO planteDTO = planteMapper.toDto(plante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanteMockMvc.perform(put("/api/plantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plante in the database
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlante() throws Exception {
        // Initialize the database
        planteRepository.saveAndFlush(plante);

        int databaseSizeBeforeDelete = planteRepository.findAll().size();

        // Get the plante
        restPlanteMockMvc.perform(delete("/api/plantes/{id}", plante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Plante> planteList = planteRepository.findAll();
        assertThat(planteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plante.class);
        Plante plante1 = new Plante();
        plante1.setId(1L);
        Plante plante2 = new Plante();
        plante2.setId(plante1.getId());
        assertThat(plante1).isEqualTo(plante2);
        plante2.setId(2L);
        assertThat(plante1).isNotEqualTo(plante2);
        plante1.setId(null);
        assertThat(plante1).isNotEqualTo(plante2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanteDTO.class);
        PlanteDTO planteDTO1 = new PlanteDTO();
        planteDTO1.setId(1L);
        PlanteDTO planteDTO2 = new PlanteDTO();
        assertThat(planteDTO1).isNotEqualTo(planteDTO2);
        planteDTO2.setId(planteDTO1.getId());
        assertThat(planteDTO1).isEqualTo(planteDTO2);
        planteDTO2.setId(2L);
        assertThat(planteDTO1).isNotEqualTo(planteDTO2);
        planteDTO1.setId(null);
        assertThat(planteDTO1).isNotEqualTo(planteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(planteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(planteMapper.fromId(null)).isNull();
    }
}
