package br.ufpa.labes.eas.web.rest;

import br.ufpa.labes.eas.ProcessoSeletivoApp;

import br.ufpa.labes.eas.domain.DemandaCurso;
import br.ufpa.labes.eas.repository.DemandaCursoRepository;
import br.ufpa.labes.eas.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static br.ufpa.labes.eas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DemandaCursoResource REST controller.
 *
 * @see DemandaCursoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProcessoSeletivoApp.class)
public class DemandaCursoResourceIntTest {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RESOLUCAO = "AAAAAAAAAA";
    private static final String UPDATED_RESOLUCAO = "BBBBBBBBBB";

    @Autowired
    private DemandaCursoRepository demandaCursoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDemandaCursoMockMvc;

    private DemandaCurso demandaCurso;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DemandaCursoResource demandaCursoResource = new DemandaCursoResource(demandaCursoRepository);
        this.restDemandaCursoMockMvc = MockMvcBuilders.standaloneSetup(demandaCursoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandaCurso createEntity(EntityManager em) {
        DemandaCurso demandaCurso = new DemandaCurso()
            .data(DEFAULT_DATA)
            .resolucao(DEFAULT_RESOLUCAO);
        return demandaCurso;
    }

    @Before
    public void initTest() {
        demandaCurso = createEntity(em);
    }

    @Test
    @Transactional
    public void createDemandaCurso() throws Exception {
        int databaseSizeBeforeCreate = demandaCursoRepository.findAll().size();

        // Create the DemandaCurso
        restDemandaCursoMockMvc.perform(post("/api/demanda-cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandaCurso)))
            .andExpect(status().isCreated());

        // Validate the DemandaCurso in the database
        List<DemandaCurso> demandaCursoList = demandaCursoRepository.findAll();
        assertThat(demandaCursoList).hasSize(databaseSizeBeforeCreate + 1);
        DemandaCurso testDemandaCurso = demandaCursoList.get(demandaCursoList.size() - 1);
        assertThat(testDemandaCurso.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDemandaCurso.getResolucao()).isEqualTo(DEFAULT_RESOLUCAO);
    }

    @Test
    @Transactional
    public void createDemandaCursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = demandaCursoRepository.findAll().size();

        // Create the DemandaCurso with an existing ID
        demandaCurso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandaCursoMockMvc.perform(post("/api/demanda-cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandaCurso)))
            .andExpect(status().isBadRequest());

        // Validate the DemandaCurso in the database
        List<DemandaCurso> demandaCursoList = demandaCursoRepository.findAll();
        assertThat(demandaCursoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDemandaCursos() throws Exception {
        // Initialize the database
        demandaCursoRepository.saveAndFlush(demandaCurso);

        // Get all the demandaCursoList
        restDemandaCursoMockMvc.perform(get("/api/demanda-cursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandaCurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].resolucao").value(hasItem(DEFAULT_RESOLUCAO.toString())));
    }
    
    @Test
    @Transactional
    public void getDemandaCurso() throws Exception {
        // Initialize the database
        demandaCursoRepository.saveAndFlush(demandaCurso);

        // Get the demandaCurso
        restDemandaCursoMockMvc.perform(get("/api/demanda-cursos/{id}", demandaCurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(demandaCurso.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.resolucao").value(DEFAULT_RESOLUCAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDemandaCurso() throws Exception {
        // Get the demandaCurso
        restDemandaCursoMockMvc.perform(get("/api/demanda-cursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDemandaCurso() throws Exception {
        // Initialize the database
        demandaCursoRepository.saveAndFlush(demandaCurso);

        int databaseSizeBeforeUpdate = demandaCursoRepository.findAll().size();

        // Update the demandaCurso
        DemandaCurso updatedDemandaCurso = demandaCursoRepository.findById(demandaCurso.getId()).get();
        // Disconnect from session so that the updates on updatedDemandaCurso are not directly saved in db
        em.detach(updatedDemandaCurso);
        updatedDemandaCurso
            .data(UPDATED_DATA)
            .resolucao(UPDATED_RESOLUCAO);

        restDemandaCursoMockMvc.perform(put("/api/demanda-cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDemandaCurso)))
            .andExpect(status().isOk());

        // Validate the DemandaCurso in the database
        List<DemandaCurso> demandaCursoList = demandaCursoRepository.findAll();
        assertThat(demandaCursoList).hasSize(databaseSizeBeforeUpdate);
        DemandaCurso testDemandaCurso = demandaCursoList.get(demandaCursoList.size() - 1);
        assertThat(testDemandaCurso.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDemandaCurso.getResolucao()).isEqualTo(UPDATED_RESOLUCAO);
    }

    @Test
    @Transactional
    public void updateNonExistingDemandaCurso() throws Exception {
        int databaseSizeBeforeUpdate = demandaCursoRepository.findAll().size();

        // Create the DemandaCurso

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandaCursoMockMvc.perform(put("/api/demanda-cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandaCurso)))
            .andExpect(status().isBadRequest());

        // Validate the DemandaCurso in the database
        List<DemandaCurso> demandaCursoList = demandaCursoRepository.findAll();
        assertThat(demandaCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDemandaCurso() throws Exception {
        // Initialize the database
        demandaCursoRepository.saveAndFlush(demandaCurso);

        int databaseSizeBeforeDelete = demandaCursoRepository.findAll().size();

        // Get the demandaCurso
        restDemandaCursoMockMvc.perform(delete("/api/demanda-cursos/{id}", demandaCurso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DemandaCurso> demandaCursoList = demandaCursoRepository.findAll();
        assertThat(demandaCursoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandaCurso.class);
        DemandaCurso demandaCurso1 = new DemandaCurso();
        demandaCurso1.setId(1L);
        DemandaCurso demandaCurso2 = new DemandaCurso();
        demandaCurso2.setId(demandaCurso1.getId());
        assertThat(demandaCurso1).isEqualTo(demandaCurso2);
        demandaCurso2.setId(2L);
        assertThat(demandaCurso1).isNotEqualTo(demandaCurso2);
        demandaCurso1.setId(null);
        assertThat(demandaCurso1).isNotEqualTo(demandaCurso2);
    }
}
