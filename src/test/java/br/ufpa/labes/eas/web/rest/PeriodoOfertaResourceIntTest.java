package br.ufpa.labes.eas.web.rest;

import br.ufpa.labes.eas.ProcessoSeletivoApp;

import br.ufpa.labes.eas.domain.PeriodoOferta;
import br.ufpa.labes.eas.repository.PeriodoOfertaRepository;
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
 * Test class for the PeriodoOfertaResource REST controller.
 *
 * @see PeriodoOfertaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProcessoSeletivoApp.class)
public class PeriodoOfertaResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_FIM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FIM = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PeriodoOfertaRepository periodoOfertaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPeriodoOfertaMockMvc;

    private PeriodoOferta periodoOferta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodoOfertaResource periodoOfertaResource = new PeriodoOfertaResource(periodoOfertaRepository);
        this.restPeriodoOfertaMockMvc = MockMvcBuilders.standaloneSetup(periodoOfertaResource)
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
    public static PeriodoOferta createEntity(EntityManager em) {
        PeriodoOferta periodoOferta = new PeriodoOferta()
            .descricao(DEFAULT_DESCRICAO)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFim(DEFAULT_DATA_FIM);
        return periodoOferta;
    }

    @Before
    public void initTest() {
        periodoOferta = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriodoOferta() throws Exception {
        int databaseSizeBeforeCreate = periodoOfertaRepository.findAll().size();

        // Create the PeriodoOferta
        restPeriodoOfertaMockMvc.perform(post("/api/periodo-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoOferta)))
            .andExpect(status().isCreated());

        // Validate the PeriodoOferta in the database
        List<PeriodoOferta> periodoOfertaList = periodoOfertaRepository.findAll();
        assertThat(periodoOfertaList).hasSize(databaseSizeBeforeCreate + 1);
        PeriodoOferta testPeriodoOferta = periodoOfertaList.get(periodoOfertaList.size() - 1);
        assertThat(testPeriodoOferta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPeriodoOferta.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testPeriodoOferta.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
    }

    @Test
    @Transactional
    public void createPeriodoOfertaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodoOfertaRepository.findAll().size();

        // Create the PeriodoOferta with an existing ID
        periodoOferta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodoOfertaMockMvc.perform(post("/api/periodo-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoOferta)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodoOferta in the database
        List<PeriodoOferta> periodoOfertaList = periodoOfertaRepository.findAll();
        assertThat(periodoOfertaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPeriodoOfertas() throws Exception {
        // Initialize the database
        periodoOfertaRepository.saveAndFlush(periodoOferta);

        // Get all the periodoOfertaList
        restPeriodoOfertaMockMvc.perform(get("/api/periodo-ofertas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodoOferta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())));
    }
    
    @Test
    @Transactional
    public void getPeriodoOferta() throws Exception {
        // Initialize the database
        periodoOfertaRepository.saveAndFlush(periodoOferta);

        // Get the periodoOferta
        restPeriodoOfertaMockMvc.perform(get("/api/periodo-ofertas/{id}", periodoOferta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(periodoOferta.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFim").value(DEFAULT_DATA_FIM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeriodoOferta() throws Exception {
        // Get the periodoOferta
        restPeriodoOfertaMockMvc.perform(get("/api/periodo-ofertas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriodoOferta() throws Exception {
        // Initialize the database
        periodoOfertaRepository.saveAndFlush(periodoOferta);

        int databaseSizeBeforeUpdate = periodoOfertaRepository.findAll().size();

        // Update the periodoOferta
        PeriodoOferta updatedPeriodoOferta = periodoOfertaRepository.findById(periodoOferta.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodoOferta are not directly saved in db
        em.detach(updatedPeriodoOferta);
        updatedPeriodoOferta
            .descricao(UPDATED_DESCRICAO)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM);

        restPeriodoOfertaMockMvc.perform(put("/api/periodo-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeriodoOferta)))
            .andExpect(status().isOk());

        // Validate the PeriodoOferta in the database
        List<PeriodoOferta> periodoOfertaList = periodoOfertaRepository.findAll();
        assertThat(periodoOfertaList).hasSize(databaseSizeBeforeUpdate);
        PeriodoOferta testPeriodoOferta = periodoOfertaList.get(periodoOfertaList.size() - 1);
        assertThat(testPeriodoOferta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPeriodoOferta.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testPeriodoOferta.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriodoOferta() throws Exception {
        int databaseSizeBeforeUpdate = periodoOfertaRepository.findAll().size();

        // Create the PeriodoOferta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoOfertaMockMvc.perform(put("/api/periodo-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoOferta)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodoOferta in the database
        List<PeriodoOferta> periodoOfertaList = periodoOfertaRepository.findAll();
        assertThat(periodoOfertaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeriodoOferta() throws Exception {
        // Initialize the database
        periodoOfertaRepository.saveAndFlush(periodoOferta);

        int databaseSizeBeforeDelete = periodoOfertaRepository.findAll().size();

        // Get the periodoOferta
        restPeriodoOfertaMockMvc.perform(delete("/api/periodo-ofertas/{id}", periodoOferta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PeriodoOferta> periodoOfertaList = periodoOfertaRepository.findAll();
        assertThat(periodoOfertaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodoOferta.class);
        PeriodoOferta periodoOferta1 = new PeriodoOferta();
        periodoOferta1.setId(1L);
        PeriodoOferta periodoOferta2 = new PeriodoOferta();
        periodoOferta2.setId(periodoOferta1.getId());
        assertThat(periodoOferta1).isEqualTo(periodoOferta2);
        periodoOferta2.setId(2L);
        assertThat(periodoOferta1).isNotEqualTo(periodoOferta2);
        periodoOferta1.setId(null);
        assertThat(periodoOferta1).isNotEqualTo(periodoOferta2);
    }
}
