package br.ufpa.labes.eas.web.rest;

import br.ufpa.labes.eas.ProcessoSeletivoApp;

import br.ufpa.labes.eas.domain.Turno;
import br.ufpa.labes.eas.repository.TurnoRepository;
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
import java.util.List;


import static br.ufpa.labes.eas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TurnoResource REST controller.
 *
 * @see TurnoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProcessoSeletivoApp.class)
public class TurnoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTurnoMockMvc;

    private Turno turno;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TurnoResource turnoResource = new TurnoResource(turnoRepository);
        this.restTurnoMockMvc = MockMvcBuilders.standaloneSetup(turnoResource)
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
    public static Turno createEntity(EntityManager em) {
        Turno turno = new Turno()
            .nome(DEFAULT_NOME);
        return turno;
    }

    @Before
    public void initTest() {
        turno = createEntity(em);
    }

    @Test
    @Transactional
    public void createTurno() throws Exception {
        int databaseSizeBeforeCreate = turnoRepository.findAll().size();

        // Create the Turno
        restTurnoMockMvc.perform(post("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turno)))
            .andExpect(status().isCreated());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeCreate + 1);
        Turno testTurno = turnoList.get(turnoList.size() - 1);
        assertThat(testTurno.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createTurnoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = turnoRepository.findAll().size();

        // Create the Turno with an existing ID
        turno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTurnoMockMvc.perform(post("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turno)))
            .andExpect(status().isBadRequest());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTurnos() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        // Get all the turnoList
        restTurnoMockMvc.perform(get("/api/turnos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(turno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }
    
    @Test
    @Transactional
    public void getTurno() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        // Get the turno
        restTurnoMockMvc.perform(get("/api/turnos/{id}", turno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(turno.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTurno() throws Exception {
        // Get the turno
        restTurnoMockMvc.perform(get("/api/turnos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTurno() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        int databaseSizeBeforeUpdate = turnoRepository.findAll().size();

        // Update the turno
        Turno updatedTurno = turnoRepository.findById(turno.getId()).get();
        // Disconnect from session so that the updates on updatedTurno are not directly saved in db
        em.detach(updatedTurno);
        updatedTurno
            .nome(UPDATED_NOME);

        restTurnoMockMvc.perform(put("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTurno)))
            .andExpect(status().isOk());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeUpdate);
        Turno testTurno = turnoList.get(turnoList.size() - 1);
        assertThat(testTurno.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingTurno() throws Exception {
        int databaseSizeBeforeUpdate = turnoRepository.findAll().size();

        // Create the Turno

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTurnoMockMvc.perform(put("/api/turnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turno)))
            .andExpect(status().isBadRequest());

        // Validate the Turno in the database
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTurno() throws Exception {
        // Initialize the database
        turnoRepository.saveAndFlush(turno);

        int databaseSizeBeforeDelete = turnoRepository.findAll().size();

        // Get the turno
        restTurnoMockMvc.perform(delete("/api/turnos/{id}", turno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Turno> turnoList = turnoRepository.findAll();
        assertThat(turnoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Turno.class);
        Turno turno1 = new Turno();
        turno1.setId(1L);
        Turno turno2 = new Turno();
        turno2.setId(turno1.getId());
        assertThat(turno1).isEqualTo(turno2);
        turno2.setId(2L);
        assertThat(turno1).isNotEqualTo(turno2);
        turno1.setId(null);
        assertThat(turno1).isNotEqualTo(turno2);
    }
}
