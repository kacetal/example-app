package com.kacetal.typoreporter.web.rest;

import com.kacetal.typoreporter.TypoReporterApp;
import com.kacetal.typoreporter.domain.Typo;
import com.kacetal.typoreporter.repository.TypoRepository;
import com.kacetal.typoreporter.service.TypoService;
import com.kacetal.typoreporter.service.dto.TypoDTO;
import com.kacetal.typoreporter.service.mapper.TypoMapper;
import com.kacetal.typoreporter.service.dto.TypoCriteria;
import com.kacetal.typoreporter.service.TypoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kacetal.typoreporter.domain.enumeration.TypoStatus;
/**
 * Integration tests for the {@link TypoResource} REST controller.
 */
@SpringBootTest(classes = TypoReporterApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypoResourceIT {

    private static final String DEFAULT_PAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_PAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_REPORTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REPORTER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REPORTER_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_REPORTER_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_BEFORE_TYPO = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_BEFORE_TYPO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_TYPO = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_TYPO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_AFTER_TYPO = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_AFTER_TYPO = "BBBBBBBBBB";

    private static final TypoStatus DEFAULT_CORRECTION_STATUS = TypoStatus.REPORTED;
    private static final TypoStatus UPDATED_CORRECTION_STATUS = TypoStatus.IN_PROGRESS;

    @Autowired
    private TypoRepository typoRepository;

    @Autowired
    private TypoMapper typoMapper;

    @Autowired
    private TypoService typoService;

    @Autowired
    private TypoQueryService typoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypoMockMvc;

    private Typo typo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Typo createEntity(EntityManager em) {
        Typo typo = new Typo()
            .pageURL(DEFAULT_PAGE_URL)
            .reporterName(DEFAULT_REPORTER_NAME)
            .reporterComment(DEFAULT_REPORTER_COMMENT)
            .textBeforeTypo(DEFAULT_TEXT_BEFORE_TYPO)
            .textTypo(DEFAULT_TEXT_TYPO)
            .textAfterTypo(DEFAULT_TEXT_AFTER_TYPO)
            .correctionStatus(DEFAULT_CORRECTION_STATUS);
        return typo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Typo createUpdatedEntity(EntityManager em) {
        Typo typo = new Typo()
            .pageURL(UPDATED_PAGE_URL)
            .reporterName(UPDATED_REPORTER_NAME)
            .reporterComment(UPDATED_REPORTER_COMMENT)
            .textBeforeTypo(UPDATED_TEXT_BEFORE_TYPO)
            .textTypo(UPDATED_TEXT_TYPO)
            .textAfterTypo(UPDATED_TEXT_AFTER_TYPO)
            .correctionStatus(UPDATED_CORRECTION_STATUS);
        return typo;
    }

    @BeforeEach
    public void initTest() {
        typo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypo() throws Exception {
        int databaseSizeBeforeCreate = typoRepository.findAll().size();
        // Create the Typo
        TypoDTO typoDTO = typoMapper.toDto(typo);
        restTypoMockMvc.perform(post("/api/typos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typoDTO)))
            .andExpect(status().isCreated());

        // Validate the Typo in the database
        List<Typo> typoList = typoRepository.findAll();
        assertThat(typoList).hasSize(databaseSizeBeforeCreate + 1);
        Typo testTypo = typoList.get(typoList.size() - 1);
        assertThat(testTypo.getPageURL()).isEqualTo(DEFAULT_PAGE_URL);
        assertThat(testTypo.getReporterName()).isEqualTo(DEFAULT_REPORTER_NAME);
        assertThat(testTypo.getReporterComment()).isEqualTo(DEFAULT_REPORTER_COMMENT);
        assertThat(testTypo.getTextBeforeTypo()).isEqualTo(DEFAULT_TEXT_BEFORE_TYPO);
        assertThat(testTypo.getTextTypo()).isEqualTo(DEFAULT_TEXT_TYPO);
        assertThat(testTypo.getTextAfterTypo()).isEqualTo(DEFAULT_TEXT_AFTER_TYPO);
        assertThat(testTypo.getCorrectionStatus()).isEqualTo(DEFAULT_CORRECTION_STATUS);
    }

    @Test
    @Transactional
    public void createTypoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typoRepository.findAll().size();

        // Create the Typo with an existing ID
        typo.setId(1L);
        TypoDTO typoDTO = typoMapper.toDto(typo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypoMockMvc.perform(post("/api/typos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Typo in the database
        List<Typo> typoList = typoRepository.findAll();
        assertThat(typoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPageURLIsRequired() throws Exception {
        int databaseSizeBeforeTest = typoRepository.findAll().size();
        // set the field null
        typo.setPageURL(null);

        // Create the Typo, which fails.
        TypoDTO typoDTO = typoMapper.toDto(typo);


        restTypoMockMvc.perform(post("/api/typos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typoDTO)))
            .andExpect(status().isBadRequest());

        List<Typo> typoList = typoRepository.findAll();
        assertThat(typoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReporterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = typoRepository.findAll().size();
        // set the field null
        typo.setReporterName(null);

        // Create the Typo, which fails.
        TypoDTO typoDTO = typoMapper.toDto(typo);


        restTypoMockMvc.perform(post("/api/typos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typoDTO)))
            .andExpect(status().isBadRequest());

        List<Typo> typoList = typoRepository.findAll();
        assertThat(typoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextTypoIsRequired() throws Exception {
        int databaseSizeBeforeTest = typoRepository.findAll().size();
        // set the field null
        typo.setTextTypo(null);

        // Create the Typo, which fails.
        TypoDTO typoDTO = typoMapper.toDto(typo);


        restTypoMockMvc.perform(post("/api/typos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typoDTO)))
            .andExpect(status().isBadRequest());

        List<Typo> typoList = typoRepository.findAll();
        assertThat(typoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorrectionStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = typoRepository.findAll().size();
        // set the field null
        typo.setCorrectionStatus(null);

        // Create the Typo, which fails.
        TypoDTO typoDTO = typoMapper.toDto(typo);


        restTypoMockMvc.perform(post("/api/typos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typoDTO)))
            .andExpect(status().isBadRequest());

        List<Typo> typoList = typoRepository.findAll();
        assertThat(typoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypos() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList
        restTypoMockMvc.perform(get("/api/typos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typo.getId().intValue())))
            .andExpect(jsonPath("$.[*].pageURL").value(hasItem(DEFAULT_PAGE_URL)))
            .andExpect(jsonPath("$.[*].reporterName").value(hasItem(DEFAULT_REPORTER_NAME)))
            .andExpect(jsonPath("$.[*].reporterComment").value(hasItem(DEFAULT_REPORTER_COMMENT)))
            .andExpect(jsonPath("$.[*].textBeforeTypo").value(hasItem(DEFAULT_TEXT_BEFORE_TYPO)))
            .andExpect(jsonPath("$.[*].textTypo").value(hasItem(DEFAULT_TEXT_TYPO)))
            .andExpect(jsonPath("$.[*].textAfterTypo").value(hasItem(DEFAULT_TEXT_AFTER_TYPO)))
            .andExpect(jsonPath("$.[*].correctionStatus").value(hasItem(DEFAULT_CORRECTION_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getTypo() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get the typo
        restTypoMockMvc.perform(get("/api/typos/{id}", typo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typo.getId().intValue()))
            .andExpect(jsonPath("$.pageURL").value(DEFAULT_PAGE_URL))
            .andExpect(jsonPath("$.reporterName").value(DEFAULT_REPORTER_NAME))
            .andExpect(jsonPath("$.reporterComment").value(DEFAULT_REPORTER_COMMENT))
            .andExpect(jsonPath("$.textBeforeTypo").value(DEFAULT_TEXT_BEFORE_TYPO))
            .andExpect(jsonPath("$.textTypo").value(DEFAULT_TEXT_TYPO))
            .andExpect(jsonPath("$.textAfterTypo").value(DEFAULT_TEXT_AFTER_TYPO))
            .andExpect(jsonPath("$.correctionStatus").value(DEFAULT_CORRECTION_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getTyposByIdFiltering() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        Long id = typo.getId();

        defaultTypoShouldBeFound("id.equals=" + id);
        defaultTypoShouldNotBeFound("id.notEquals=" + id);

        defaultTypoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTypoShouldNotBeFound("id.greaterThan=" + id);

        defaultTypoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTypoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTyposByPageURLIsEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where pageURL equals to DEFAULT_PAGE_URL
        defaultTypoShouldBeFound("pageURL.equals=" + DEFAULT_PAGE_URL);

        // Get all the typoList where pageURL equals to UPDATED_PAGE_URL
        defaultTypoShouldNotBeFound("pageURL.equals=" + UPDATED_PAGE_URL);
    }

    @Test
    @Transactional
    public void getAllTyposByPageURLIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where pageURL not equals to DEFAULT_PAGE_URL
        defaultTypoShouldNotBeFound("pageURL.notEquals=" + DEFAULT_PAGE_URL);

        // Get all the typoList where pageURL not equals to UPDATED_PAGE_URL
        defaultTypoShouldBeFound("pageURL.notEquals=" + UPDATED_PAGE_URL);
    }

    @Test
    @Transactional
    public void getAllTyposByPageURLIsInShouldWork() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where pageURL in DEFAULT_PAGE_URL or UPDATED_PAGE_URL
        defaultTypoShouldBeFound("pageURL.in=" + DEFAULT_PAGE_URL + "," + UPDATED_PAGE_URL);

        // Get all the typoList where pageURL equals to UPDATED_PAGE_URL
        defaultTypoShouldNotBeFound("pageURL.in=" + UPDATED_PAGE_URL);
    }

    @Test
    @Transactional
    public void getAllTyposByPageURLIsNullOrNotNull() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where pageURL is not null
        defaultTypoShouldBeFound("pageURL.specified=true");

        // Get all the typoList where pageURL is null
        defaultTypoShouldNotBeFound("pageURL.specified=false");
    }
                @Test
    @Transactional
    public void getAllTyposByPageURLContainsSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where pageURL contains DEFAULT_PAGE_URL
        defaultTypoShouldBeFound("pageURL.contains=" + DEFAULT_PAGE_URL);

        // Get all the typoList where pageURL contains UPDATED_PAGE_URL
        defaultTypoShouldNotBeFound("pageURL.contains=" + UPDATED_PAGE_URL);
    }

    @Test
    @Transactional
    public void getAllTyposByPageURLNotContainsSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where pageURL does not contain DEFAULT_PAGE_URL
        defaultTypoShouldNotBeFound("pageURL.doesNotContain=" + DEFAULT_PAGE_URL);

        // Get all the typoList where pageURL does not contain UPDATED_PAGE_URL
        defaultTypoShouldBeFound("pageURL.doesNotContain=" + UPDATED_PAGE_URL);
    }


    @Test
    @Transactional
    public void getAllTyposByReporterNameIsEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where reporterName equals to DEFAULT_REPORTER_NAME
        defaultTypoShouldBeFound("reporterName.equals=" + DEFAULT_REPORTER_NAME);

        // Get all the typoList where reporterName equals to UPDATED_REPORTER_NAME
        defaultTypoShouldNotBeFound("reporterName.equals=" + UPDATED_REPORTER_NAME);
    }

    @Test
    @Transactional
    public void getAllTyposByReporterNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where reporterName not equals to DEFAULT_REPORTER_NAME
        defaultTypoShouldNotBeFound("reporterName.notEquals=" + DEFAULT_REPORTER_NAME);

        // Get all the typoList where reporterName not equals to UPDATED_REPORTER_NAME
        defaultTypoShouldBeFound("reporterName.notEquals=" + UPDATED_REPORTER_NAME);
    }

    @Test
    @Transactional
    public void getAllTyposByReporterNameIsInShouldWork() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where reporterName in DEFAULT_REPORTER_NAME or UPDATED_REPORTER_NAME
        defaultTypoShouldBeFound("reporterName.in=" + DEFAULT_REPORTER_NAME + "," + UPDATED_REPORTER_NAME);

        // Get all the typoList where reporterName equals to UPDATED_REPORTER_NAME
        defaultTypoShouldNotBeFound("reporterName.in=" + UPDATED_REPORTER_NAME);
    }

    @Test
    @Transactional
    public void getAllTyposByReporterNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where reporterName is not null
        defaultTypoShouldBeFound("reporterName.specified=true");

        // Get all the typoList where reporterName is null
        defaultTypoShouldNotBeFound("reporterName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTyposByReporterNameContainsSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where reporterName contains DEFAULT_REPORTER_NAME
        defaultTypoShouldBeFound("reporterName.contains=" + DEFAULT_REPORTER_NAME);

        // Get all the typoList where reporterName contains UPDATED_REPORTER_NAME
        defaultTypoShouldNotBeFound("reporterName.contains=" + UPDATED_REPORTER_NAME);
    }

    @Test
    @Transactional
    public void getAllTyposByReporterNameNotContainsSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where reporterName does not contain DEFAULT_REPORTER_NAME
        defaultTypoShouldNotBeFound("reporterName.doesNotContain=" + DEFAULT_REPORTER_NAME);

        // Get all the typoList where reporterName does not contain UPDATED_REPORTER_NAME
        defaultTypoShouldBeFound("reporterName.doesNotContain=" + UPDATED_REPORTER_NAME);
    }


    @Test
    @Transactional
    public void getAllTyposByReporterCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where reporterComment equals to DEFAULT_REPORTER_COMMENT
        defaultTypoShouldBeFound("reporterComment.equals=" + DEFAULT_REPORTER_COMMENT);

        // Get all the typoList where reporterComment equals to UPDATED_REPORTER_COMMENT
        defaultTypoShouldNotBeFound("reporterComment.equals=" + UPDATED_REPORTER_COMMENT);
    }

    @Test
    @Transactional
    public void getAllTyposByReporterCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where reporterComment not equals to DEFAULT_REPORTER_COMMENT
        defaultTypoShouldNotBeFound("reporterComment.notEquals=" + DEFAULT_REPORTER_COMMENT);

        // Get all the typoList where reporterComment not equals to UPDATED_REPORTER_COMMENT
        defaultTypoShouldBeFound("reporterComment.notEquals=" + UPDATED_REPORTER_COMMENT);
    }

    @Test
    @Transactional
    public void getAllTyposByReporterCommentIsInShouldWork() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where reporterComment in DEFAULT_REPORTER_COMMENT or UPDATED_REPORTER_COMMENT
        defaultTypoShouldBeFound("reporterComment.in=" + DEFAULT_REPORTER_COMMENT + "," + UPDATED_REPORTER_COMMENT);

        // Get all the typoList where reporterComment equals to UPDATED_REPORTER_COMMENT
        defaultTypoShouldNotBeFound("reporterComment.in=" + UPDATED_REPORTER_COMMENT);
    }

    @Test
    @Transactional
    public void getAllTyposByReporterCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where reporterComment is not null
        defaultTypoShouldBeFound("reporterComment.specified=true");

        // Get all the typoList where reporterComment is null
        defaultTypoShouldNotBeFound("reporterComment.specified=false");
    }
                @Test
    @Transactional
    public void getAllTyposByReporterCommentContainsSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where reporterComment contains DEFAULT_REPORTER_COMMENT
        defaultTypoShouldBeFound("reporterComment.contains=" + DEFAULT_REPORTER_COMMENT);

        // Get all the typoList where reporterComment contains UPDATED_REPORTER_COMMENT
        defaultTypoShouldNotBeFound("reporterComment.contains=" + UPDATED_REPORTER_COMMENT);
    }

    @Test
    @Transactional
    public void getAllTyposByReporterCommentNotContainsSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where reporterComment does not contain DEFAULT_REPORTER_COMMENT
        defaultTypoShouldNotBeFound("reporterComment.doesNotContain=" + DEFAULT_REPORTER_COMMENT);

        // Get all the typoList where reporterComment does not contain UPDATED_REPORTER_COMMENT
        defaultTypoShouldBeFound("reporterComment.doesNotContain=" + UPDATED_REPORTER_COMMENT);
    }


    @Test
    @Transactional
    public void getAllTyposByTextBeforeTypoIsEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textBeforeTypo equals to DEFAULT_TEXT_BEFORE_TYPO
        defaultTypoShouldBeFound("textBeforeTypo.equals=" + DEFAULT_TEXT_BEFORE_TYPO);

        // Get all the typoList where textBeforeTypo equals to UPDATED_TEXT_BEFORE_TYPO
        defaultTypoShouldNotBeFound("textBeforeTypo.equals=" + UPDATED_TEXT_BEFORE_TYPO);
    }

    @Test
    @Transactional
    public void getAllTyposByTextBeforeTypoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textBeforeTypo not equals to DEFAULT_TEXT_BEFORE_TYPO
        defaultTypoShouldNotBeFound("textBeforeTypo.notEquals=" + DEFAULT_TEXT_BEFORE_TYPO);

        // Get all the typoList where textBeforeTypo not equals to UPDATED_TEXT_BEFORE_TYPO
        defaultTypoShouldBeFound("textBeforeTypo.notEquals=" + UPDATED_TEXT_BEFORE_TYPO);
    }

    @Test
    @Transactional
    public void getAllTyposByTextBeforeTypoIsInShouldWork() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textBeforeTypo in DEFAULT_TEXT_BEFORE_TYPO or UPDATED_TEXT_BEFORE_TYPO
        defaultTypoShouldBeFound("textBeforeTypo.in=" + DEFAULT_TEXT_BEFORE_TYPO + "," + UPDATED_TEXT_BEFORE_TYPO);

        // Get all the typoList where textBeforeTypo equals to UPDATED_TEXT_BEFORE_TYPO
        defaultTypoShouldNotBeFound("textBeforeTypo.in=" + UPDATED_TEXT_BEFORE_TYPO);
    }

    @Test
    @Transactional
    public void getAllTyposByTextBeforeTypoIsNullOrNotNull() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textBeforeTypo is not null
        defaultTypoShouldBeFound("textBeforeTypo.specified=true");

        // Get all the typoList where textBeforeTypo is null
        defaultTypoShouldNotBeFound("textBeforeTypo.specified=false");
    }
                @Test
    @Transactional
    public void getAllTyposByTextBeforeTypoContainsSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textBeforeTypo contains DEFAULT_TEXT_BEFORE_TYPO
        defaultTypoShouldBeFound("textBeforeTypo.contains=" + DEFAULT_TEXT_BEFORE_TYPO);

        // Get all the typoList where textBeforeTypo contains UPDATED_TEXT_BEFORE_TYPO
        defaultTypoShouldNotBeFound("textBeforeTypo.contains=" + UPDATED_TEXT_BEFORE_TYPO);
    }

    @Test
    @Transactional
    public void getAllTyposByTextBeforeTypoNotContainsSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textBeforeTypo does not contain DEFAULT_TEXT_BEFORE_TYPO
        defaultTypoShouldNotBeFound("textBeforeTypo.doesNotContain=" + DEFAULT_TEXT_BEFORE_TYPO);

        // Get all the typoList where textBeforeTypo does not contain UPDATED_TEXT_BEFORE_TYPO
        defaultTypoShouldBeFound("textBeforeTypo.doesNotContain=" + UPDATED_TEXT_BEFORE_TYPO);
    }


    @Test
    @Transactional
    public void getAllTyposByTextTypoIsEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textTypo equals to DEFAULT_TEXT_TYPO
        defaultTypoShouldBeFound("textTypo.equals=" + DEFAULT_TEXT_TYPO);

        // Get all the typoList where textTypo equals to UPDATED_TEXT_TYPO
        defaultTypoShouldNotBeFound("textTypo.equals=" + UPDATED_TEXT_TYPO);
    }

    @Test
    @Transactional
    public void getAllTyposByTextTypoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textTypo not equals to DEFAULT_TEXT_TYPO
        defaultTypoShouldNotBeFound("textTypo.notEquals=" + DEFAULT_TEXT_TYPO);

        // Get all the typoList where textTypo not equals to UPDATED_TEXT_TYPO
        defaultTypoShouldBeFound("textTypo.notEquals=" + UPDATED_TEXT_TYPO);
    }

    @Test
    @Transactional
    public void getAllTyposByTextTypoIsInShouldWork() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textTypo in DEFAULT_TEXT_TYPO or UPDATED_TEXT_TYPO
        defaultTypoShouldBeFound("textTypo.in=" + DEFAULT_TEXT_TYPO + "," + UPDATED_TEXT_TYPO);

        // Get all the typoList where textTypo equals to UPDATED_TEXT_TYPO
        defaultTypoShouldNotBeFound("textTypo.in=" + UPDATED_TEXT_TYPO);
    }

    @Test
    @Transactional
    public void getAllTyposByTextTypoIsNullOrNotNull() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textTypo is not null
        defaultTypoShouldBeFound("textTypo.specified=true");

        // Get all the typoList where textTypo is null
        defaultTypoShouldNotBeFound("textTypo.specified=false");
    }
                @Test
    @Transactional
    public void getAllTyposByTextTypoContainsSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textTypo contains DEFAULT_TEXT_TYPO
        defaultTypoShouldBeFound("textTypo.contains=" + DEFAULT_TEXT_TYPO);

        // Get all the typoList where textTypo contains UPDATED_TEXT_TYPO
        defaultTypoShouldNotBeFound("textTypo.contains=" + UPDATED_TEXT_TYPO);
    }

    @Test
    @Transactional
    public void getAllTyposByTextTypoNotContainsSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textTypo does not contain DEFAULT_TEXT_TYPO
        defaultTypoShouldNotBeFound("textTypo.doesNotContain=" + DEFAULT_TEXT_TYPO);

        // Get all the typoList where textTypo does not contain UPDATED_TEXT_TYPO
        defaultTypoShouldBeFound("textTypo.doesNotContain=" + UPDATED_TEXT_TYPO);
    }


    @Test
    @Transactional
    public void getAllTyposByTextAfterTypoIsEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textAfterTypo equals to DEFAULT_TEXT_AFTER_TYPO
        defaultTypoShouldBeFound("textAfterTypo.equals=" + DEFAULT_TEXT_AFTER_TYPO);

        // Get all the typoList where textAfterTypo equals to UPDATED_TEXT_AFTER_TYPO
        defaultTypoShouldNotBeFound("textAfterTypo.equals=" + UPDATED_TEXT_AFTER_TYPO);
    }

    @Test
    @Transactional
    public void getAllTyposByTextAfterTypoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textAfterTypo not equals to DEFAULT_TEXT_AFTER_TYPO
        defaultTypoShouldNotBeFound("textAfterTypo.notEquals=" + DEFAULT_TEXT_AFTER_TYPO);

        // Get all the typoList where textAfterTypo not equals to UPDATED_TEXT_AFTER_TYPO
        defaultTypoShouldBeFound("textAfterTypo.notEquals=" + UPDATED_TEXT_AFTER_TYPO);
    }

    @Test
    @Transactional
    public void getAllTyposByTextAfterTypoIsInShouldWork() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textAfterTypo in DEFAULT_TEXT_AFTER_TYPO or UPDATED_TEXT_AFTER_TYPO
        defaultTypoShouldBeFound("textAfterTypo.in=" + DEFAULT_TEXT_AFTER_TYPO + "," + UPDATED_TEXT_AFTER_TYPO);

        // Get all the typoList where textAfterTypo equals to UPDATED_TEXT_AFTER_TYPO
        defaultTypoShouldNotBeFound("textAfterTypo.in=" + UPDATED_TEXT_AFTER_TYPO);
    }

    @Test
    @Transactional
    public void getAllTyposByTextAfterTypoIsNullOrNotNull() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textAfterTypo is not null
        defaultTypoShouldBeFound("textAfterTypo.specified=true");

        // Get all the typoList where textAfterTypo is null
        defaultTypoShouldNotBeFound("textAfterTypo.specified=false");
    }
                @Test
    @Transactional
    public void getAllTyposByTextAfterTypoContainsSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textAfterTypo contains DEFAULT_TEXT_AFTER_TYPO
        defaultTypoShouldBeFound("textAfterTypo.contains=" + DEFAULT_TEXT_AFTER_TYPO);

        // Get all the typoList where textAfterTypo contains UPDATED_TEXT_AFTER_TYPO
        defaultTypoShouldNotBeFound("textAfterTypo.contains=" + UPDATED_TEXT_AFTER_TYPO);
    }

    @Test
    @Transactional
    public void getAllTyposByTextAfterTypoNotContainsSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where textAfterTypo does not contain DEFAULT_TEXT_AFTER_TYPO
        defaultTypoShouldNotBeFound("textAfterTypo.doesNotContain=" + DEFAULT_TEXT_AFTER_TYPO);

        // Get all the typoList where textAfterTypo does not contain UPDATED_TEXT_AFTER_TYPO
        defaultTypoShouldBeFound("textAfterTypo.doesNotContain=" + UPDATED_TEXT_AFTER_TYPO);
    }


    @Test
    @Transactional
    public void getAllTyposByCorrectionStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where correctionStatus equals to DEFAULT_CORRECTION_STATUS
        defaultTypoShouldBeFound("correctionStatus.equals=" + DEFAULT_CORRECTION_STATUS);

        // Get all the typoList where correctionStatus equals to UPDATED_CORRECTION_STATUS
        defaultTypoShouldNotBeFound("correctionStatus.equals=" + UPDATED_CORRECTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllTyposByCorrectionStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where correctionStatus not equals to DEFAULT_CORRECTION_STATUS
        defaultTypoShouldNotBeFound("correctionStatus.notEquals=" + DEFAULT_CORRECTION_STATUS);

        // Get all the typoList where correctionStatus not equals to UPDATED_CORRECTION_STATUS
        defaultTypoShouldBeFound("correctionStatus.notEquals=" + UPDATED_CORRECTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllTyposByCorrectionStatusIsInShouldWork() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where correctionStatus in DEFAULT_CORRECTION_STATUS or UPDATED_CORRECTION_STATUS
        defaultTypoShouldBeFound("correctionStatus.in=" + DEFAULT_CORRECTION_STATUS + "," + UPDATED_CORRECTION_STATUS);

        // Get all the typoList where correctionStatus equals to UPDATED_CORRECTION_STATUS
        defaultTypoShouldNotBeFound("correctionStatus.in=" + UPDATED_CORRECTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllTyposByCorrectionStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        // Get all the typoList where correctionStatus is not null
        defaultTypoShouldBeFound("correctionStatus.specified=true");

        // Get all the typoList where correctionStatus is null
        defaultTypoShouldNotBeFound("correctionStatus.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTypoShouldBeFound(String filter) throws Exception {
        restTypoMockMvc.perform(get("/api/typos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typo.getId().intValue())))
            .andExpect(jsonPath("$.[*].pageURL").value(hasItem(DEFAULT_PAGE_URL)))
            .andExpect(jsonPath("$.[*].reporterName").value(hasItem(DEFAULT_REPORTER_NAME)))
            .andExpect(jsonPath("$.[*].reporterComment").value(hasItem(DEFAULT_REPORTER_COMMENT)))
            .andExpect(jsonPath("$.[*].textBeforeTypo").value(hasItem(DEFAULT_TEXT_BEFORE_TYPO)))
            .andExpect(jsonPath("$.[*].textTypo").value(hasItem(DEFAULT_TEXT_TYPO)))
            .andExpect(jsonPath("$.[*].textAfterTypo").value(hasItem(DEFAULT_TEXT_AFTER_TYPO)))
            .andExpect(jsonPath("$.[*].correctionStatus").value(hasItem(DEFAULT_CORRECTION_STATUS.toString())));

        // Check, that the count call also returns 1
        restTypoMockMvc.perform(get("/api/typos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTypoShouldNotBeFound(String filter) throws Exception {
        restTypoMockMvc.perform(get("/api/typos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypoMockMvc.perform(get("/api/typos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTypo() throws Exception {
        // Get the typo
        restTypoMockMvc.perform(get("/api/typos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypo() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        int databaseSizeBeforeUpdate = typoRepository.findAll().size();

        // Update the typo
        Typo updatedTypo = typoRepository.findById(typo.getId()).get();
        // Disconnect from session so that the updates on updatedTypo are not directly saved in db
        em.detach(updatedTypo);
        updatedTypo
            .pageURL(UPDATED_PAGE_URL)
            .reporterName(UPDATED_REPORTER_NAME)
            .reporterComment(UPDATED_REPORTER_COMMENT)
            .textBeforeTypo(UPDATED_TEXT_BEFORE_TYPO)
            .textTypo(UPDATED_TEXT_TYPO)
            .textAfterTypo(UPDATED_TEXT_AFTER_TYPO)
            .correctionStatus(UPDATED_CORRECTION_STATUS);
        TypoDTO typoDTO = typoMapper.toDto(updatedTypo);

        restTypoMockMvc.perform(put("/api/typos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typoDTO)))
            .andExpect(status().isOk());

        // Validate the Typo in the database
        List<Typo> typoList = typoRepository.findAll();
        assertThat(typoList).hasSize(databaseSizeBeforeUpdate);
        Typo testTypo = typoList.get(typoList.size() - 1);
        assertThat(testTypo.getPageURL()).isEqualTo(UPDATED_PAGE_URL);
        assertThat(testTypo.getReporterName()).isEqualTo(UPDATED_REPORTER_NAME);
        assertThat(testTypo.getReporterComment()).isEqualTo(UPDATED_REPORTER_COMMENT);
        assertThat(testTypo.getTextBeforeTypo()).isEqualTo(UPDATED_TEXT_BEFORE_TYPO);
        assertThat(testTypo.getTextTypo()).isEqualTo(UPDATED_TEXT_TYPO);
        assertThat(testTypo.getTextAfterTypo()).isEqualTo(UPDATED_TEXT_AFTER_TYPO);
        assertThat(testTypo.getCorrectionStatus()).isEqualTo(UPDATED_CORRECTION_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingTypo() throws Exception {
        int databaseSizeBeforeUpdate = typoRepository.findAll().size();

        // Create the Typo
        TypoDTO typoDTO = typoMapper.toDto(typo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypoMockMvc.perform(put("/api/typos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Typo in the database
        List<Typo> typoList = typoRepository.findAll();
        assertThat(typoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypo() throws Exception {
        // Initialize the database
        typoRepository.saveAndFlush(typo);

        int databaseSizeBeforeDelete = typoRepository.findAll().size();

        // Delete the typo
        restTypoMockMvc.perform(delete("/api/typos/{id}", typo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Typo> typoList = typoRepository.findAll();
        assertThat(typoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
