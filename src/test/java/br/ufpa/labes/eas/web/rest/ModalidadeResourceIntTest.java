package br.ufpa.labes.eas.web.rest;

import br.ufpa.labes.eas.ProcessoSeletivoApp;

import br.ufpa.labes.eas.domain.Modalidade;
import br.ufpa.labes.eas.repository.ModalidadeRepository;
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
 * Test class for the ModalidadeResource REST controller.
 *
 * @see ModalidadeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProcessoSeletivoApp.class)
public class ModalidadeResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private ModalidadeRepository modalidadeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restModalidadeMockMvc;

    private Modalidade modalidade;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModalidadeResource modalidadeResource = new ModalidadeResource(modalidadeRepository);
        this.restModalidadeMockMvc = MockMvcBuilders.standaloneSetup(modalidadeResource)
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
    public static Modalidade createEntity(EntityManager em) {
        Modalidade modalidade = new Modalidade()
            .nome(DEFAULT_NOME);
        return modalidade;
    }

    @Before
    public void initTest() {
        modalidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createModalidade() throws Exception {
        int databaseSizeBeforeCreate = modalidadeRepository.findAll().size();

        // Create the Modalidade
        restModalidadeMockMvc.perform(post("/api/modalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidade)))
            .andExpect(status().isCreated());

        // Validate the Modalidade in the database
        List<Modalidade> modalidadeList = modalidadeRepository.findAll();
        assertThat(modalidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Modalidade testModalidade = modalidadeList.get(modalidadeList.size() - 1);
        assertThat(testModalidade.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createModalidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modalidadeRepository.findAll().size();

        // Create the Modalidade with an existing ID
        modalidade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModalidadeMockMvc.perform(post("/api/modalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidade)))
            .andExpect(status().isBadRequest());

        // Validate the Modalidade in the database
        List<Modalidade> modalidadeList = modalidadeRepository.findAll();
        assertThat(modalidadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllModalidades() throws Exception {
        // Initialize the database
        modalidadeRepository.saveAndFlush(modalidade);

        // Get all the modalidadeList
        restModalidadeMockMvc.perform(get("/api/modalidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modalidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }
    
    @Test
    @Transactional
    public void getModalidade() throws Exception {
        // Initialize the database
        modalidadeRepository.saveAndFlush(modalidade);

        // Get the modalidade
        restModalidadeMockMvc.perform(get("/api/modalidades/{id}", modalidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modalidade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModalidade() throws Exception {
        // Get the modalidade
        restModalidadeMockMvc.perform(get("/api/modalidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModalidade() throws Exception {
        // Initialize the database
        modalidadeRepository.saveAndFlush(modalidade);

        int databaseSizeBeforeUpdate = modalidadeRepository.findAll().size();

        // Update the modalidade
        Modalidade updatedModalidade = modalidadeRepository.findById(modalidade.getId()).get();
        // Disconnect from session so that the updates on updatedModalidade are not directly saved in db
        em.detach(updatedModalidade);
        updatedModalidade
            .nome(UPDATED_NOME);

        restModalidadeMockMvc.perform(put("/api/modalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedModalidade)))
            .andExpect(status().isOk());

        // Validate the Modalidade in the database
        List<Modalidade> modalidadeList = modalidadeRepository.findAll();
        assertThat(modalidadeList).hasSize(databaseSizeBeforeUpdate);
        Modalidade testModalidade = modalidadeList.get(modalidadeList.size() - 1);
        assertThat(testModalidade.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingModalidade() throws Exception {
        int databaseSizeBeforeUpdate = modalidadeRepository.findAll().size();

        // Create the Modalidade

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModalidadeMockMvc.perform(put("/api/modalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidade)))
            .andExpect(status().isBadRequest());

        // Validate the Modalidade in the database
        List<Modalidade> modalidadeList = modalidadeRepository.findAll();
        assertThat(modalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModalidade() throws Exception {
        // Initialize the database
        modalidadeRepository.saveAndFlush(modalidade);

        int databaseSizeBeforeDelete = modalidadeRepository.findAll().size();

        // Get the modalidade
        restModalidadeMockMvc.perform(delete("/api/modalidades/{id}", modalidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Modalidade> modalidadeList = modalidadeRepository.findAll();
        assertThat(modalidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Modalidade.class);
        Modalidade modalidade1 = new Modalidade();
        modalidade1.setId(1L);
        Modalidade modalidade2 = new Modalidade();
        modalidade2.setId(modalidade1.getId());
        assertThat(modalidade1).isEqualTo(modalidade2);
        modalidade2.setId(2L);
        assertThat(modalidade1).isNotEqualTo(modalidade2);
        modalidade1.setId(null);
        assertThat(modalidade1).isNotEqualTo(modalidade2);
    }
}
