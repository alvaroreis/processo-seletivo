package br.ufpa.labes.eas.web.rest;

import br.ufpa.labes.eas.ProcessoSeletivoApp;

import br.ufpa.labes.eas.domain.Curso;
import br.ufpa.labes.eas.repository.CursoRepository;
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
 * Test class for the CursoResource REST controller.
 *
 * @see CursoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProcessoSeletivoApp.class)
public class CursoResourceIntTest {

    private static final Integer DEFAULT_COD_SIGAA = 1;
    private static final Integer UPDATED_COD_SIGAA = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CURSO_NOVO = 1;
    private static final Integer UPDATED_CURSO_NOVO = 2;

    private static final String DEFAULT_JUSTIFICATIVA_NOVO = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATIVA_NOVO = "BBBBBBBBBB";

    private static final Integer DEFAULT_VAGAS_AUTORIZADAS_MEC = 1;
    private static final Integer UPDATED_VAGAS_AUTORIZADAS_MEC = 2;

    private static final String DEFAULT_RESOLUCAO = "AAAAAAAAAA";
    private static final String UPDATED_RESOLUCAO = "BBBBBBBBBB";

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCursoMockMvc;

    private Curso curso;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CursoResource cursoResource = new CursoResource(cursoRepository);
        this.restCursoMockMvc = MockMvcBuilders.standaloneSetup(cursoResource)
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
    public static Curso createEntity(EntityManager em) {
        Curso curso = new Curso()
            .codSigaa(DEFAULT_COD_SIGAA)
            .nome(DEFAULT_NOME)
            .cursoNovo(DEFAULT_CURSO_NOVO)
            .justificativaNovo(DEFAULT_JUSTIFICATIVA_NOVO)
            .vagasAutorizadasMec(DEFAULT_VAGAS_AUTORIZADAS_MEC)
            .resolucao(DEFAULT_RESOLUCAO);
        return curso;
    }

    @Before
    public void initTest() {
        curso = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurso() throws Exception {
        int databaseSizeBeforeCreate = cursoRepository.findAll().size();

        // Create the Curso
        restCursoMockMvc.perform(post("/api/cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curso)))
            .andExpect(status().isCreated());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeCreate + 1);
        Curso testCurso = cursoList.get(cursoList.size() - 1);
        assertThat(testCurso.getCodSigaa()).isEqualTo(DEFAULT_COD_SIGAA);
        assertThat(testCurso.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCurso.getCursoNovo()).isEqualTo(DEFAULT_CURSO_NOVO);
        assertThat(testCurso.getJustificativaNovo()).isEqualTo(DEFAULT_JUSTIFICATIVA_NOVO);
        assertThat(testCurso.getVagasAutorizadasMec()).isEqualTo(DEFAULT_VAGAS_AUTORIZADAS_MEC);
        assertThat(testCurso.getResolucao()).isEqualTo(DEFAULT_RESOLUCAO);
    }

    @Test
    @Transactional
    public void createCursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cursoRepository.findAll().size();

        // Create the Curso with an existing ID
        curso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCursoMockMvc.perform(post("/api/cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curso)))
            .andExpect(status().isBadRequest());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCursos() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get all the cursoList
        restCursoMockMvc.perform(get("/api/cursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curso.getId().intValue())))
            .andExpect(jsonPath("$.[*].codSigaa").value(hasItem(DEFAULT_COD_SIGAA)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cursoNovo").value(hasItem(DEFAULT_CURSO_NOVO)))
            .andExpect(jsonPath("$.[*].justificativaNovo").value(hasItem(DEFAULT_JUSTIFICATIVA_NOVO.toString())))
            .andExpect(jsonPath("$.[*].vagasAutorizadasMec").value(hasItem(DEFAULT_VAGAS_AUTORIZADAS_MEC)))
            .andExpect(jsonPath("$.[*].resolucao").value(hasItem(DEFAULT_RESOLUCAO.toString())));
    }
    
    @Test
    @Transactional
    public void getCurso() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        // Get the curso
        restCursoMockMvc.perform(get("/api/cursos/{id}", curso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(curso.getId().intValue()))
            .andExpect(jsonPath("$.codSigaa").value(DEFAULT_COD_SIGAA))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cursoNovo").value(DEFAULT_CURSO_NOVO))
            .andExpect(jsonPath("$.justificativaNovo").value(DEFAULT_JUSTIFICATIVA_NOVO.toString()))
            .andExpect(jsonPath("$.vagasAutorizadasMec").value(DEFAULT_VAGAS_AUTORIZADAS_MEC))
            .andExpect(jsonPath("$.resolucao").value(DEFAULT_RESOLUCAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCurso() throws Exception {
        // Get the curso
        restCursoMockMvc.perform(get("/api/cursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurso() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        int databaseSizeBeforeUpdate = cursoRepository.findAll().size();

        // Update the curso
        Curso updatedCurso = cursoRepository.findById(curso.getId()).get();
        // Disconnect from session so that the updates on updatedCurso are not directly saved in db
        em.detach(updatedCurso);
        updatedCurso
            .codSigaa(UPDATED_COD_SIGAA)
            .nome(UPDATED_NOME)
            .cursoNovo(UPDATED_CURSO_NOVO)
            .justificativaNovo(UPDATED_JUSTIFICATIVA_NOVO)
            .vagasAutorizadasMec(UPDATED_VAGAS_AUTORIZADAS_MEC)
            .resolucao(UPDATED_RESOLUCAO);

        restCursoMockMvc.perform(put("/api/cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCurso)))
            .andExpect(status().isOk());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeUpdate);
        Curso testCurso = cursoList.get(cursoList.size() - 1);
        assertThat(testCurso.getCodSigaa()).isEqualTo(UPDATED_COD_SIGAA);
        assertThat(testCurso.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCurso.getCursoNovo()).isEqualTo(UPDATED_CURSO_NOVO);
        assertThat(testCurso.getJustificativaNovo()).isEqualTo(UPDATED_JUSTIFICATIVA_NOVO);
        assertThat(testCurso.getVagasAutorizadasMec()).isEqualTo(UPDATED_VAGAS_AUTORIZADAS_MEC);
        assertThat(testCurso.getResolucao()).isEqualTo(UPDATED_RESOLUCAO);
    }

    @Test
    @Transactional
    public void updateNonExistingCurso() throws Exception {
        int databaseSizeBeforeUpdate = cursoRepository.findAll().size();

        // Create the Curso

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCursoMockMvc.perform(put("/api/cursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curso)))
            .andExpect(status().isBadRequest());

        // Validate the Curso in the database
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurso() throws Exception {
        // Initialize the database
        cursoRepository.saveAndFlush(curso);

        int databaseSizeBeforeDelete = cursoRepository.findAll().size();

        // Get the curso
        restCursoMockMvc.perform(delete("/api/cursos/{id}", curso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Curso> cursoList = cursoRepository.findAll();
        assertThat(cursoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Curso.class);
        Curso curso1 = new Curso();
        curso1.setId(1L);
        Curso curso2 = new Curso();
        curso2.setId(curso1.getId());
        assertThat(curso1).isEqualTo(curso2);
        curso2.setId(2L);
        assertThat(curso1).isNotEqualTo(curso2);
        curso1.setId(null);
        assertThat(curso1).isNotEqualTo(curso2);
    }
}
