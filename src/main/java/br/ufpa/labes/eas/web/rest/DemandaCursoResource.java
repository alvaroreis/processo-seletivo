package br.ufpa.labes.eas.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufpa.labes.eas.domain.DemandaCurso;
import br.ufpa.labes.eas.repository.DemandaCursoRepository;
import br.ufpa.labes.eas.web.rest.errors.BadRequestAlertException;
import br.ufpa.labes.eas.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DemandaCurso.
 */
@RestController
@RequestMapping("/api")
public class DemandaCursoResource {

    private final Logger log = LoggerFactory.getLogger(DemandaCursoResource.class);

    private static final String ENTITY_NAME = "demandaCurso";

    private DemandaCursoRepository demandaCursoRepository;

    public DemandaCursoResource(DemandaCursoRepository demandaCursoRepository) {
        this.demandaCursoRepository = demandaCursoRepository;
    }

    /**
     * POST  /demanda-cursos : Create a new demandaCurso.
     *
     * @param demandaCurso the demandaCurso to create
     * @return the ResponseEntity with status 201 (Created) and with body the new demandaCurso, or with status 400 (Bad Request) if the demandaCurso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/demanda-cursos")
    @Timed
    public ResponseEntity<DemandaCurso> createDemandaCurso(@RequestBody DemandaCurso demandaCurso) throws URISyntaxException {
        log.debug("REST request to save DemandaCurso : {}", demandaCurso);
        if (demandaCurso.getId() != null) {
            throw new BadRequestAlertException("A new demandaCurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandaCurso result = demandaCursoRepository.save(demandaCurso);
        return ResponseEntity.created(new URI("/api/demanda-cursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /demanda-cursos : Updates an existing demandaCurso.
     *
     * @param demandaCurso the demandaCurso to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated demandaCurso,
     * or with status 400 (Bad Request) if the demandaCurso is not valid,
     * or with status 500 (Internal Server Error) if the demandaCurso couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/demanda-cursos")
    @Timed
    public ResponseEntity<DemandaCurso> updateDemandaCurso(@RequestBody DemandaCurso demandaCurso) throws URISyntaxException {
        log.debug("REST request to update DemandaCurso : {}", demandaCurso);
        if (demandaCurso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DemandaCurso result = demandaCursoRepository.save(demandaCurso);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, demandaCurso.getId().toString()))
            .body(result);
    }

    /**
     * GET  /demanda-cursos : get all the demandaCursos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of demandaCursos in body
     */
    @GetMapping("/demanda-cursos")
    @Timed
    public List<DemandaCurso> getAllDemandaCursos() {
        log.debug("REST request to get all DemandaCursos");
        return demandaCursoRepository.findAll();
    }

    /**
     * GET  /demanda-cursos/:id : get the "id" demandaCurso.
     *
     * @param id the id of the demandaCurso to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the demandaCurso, or with status 404 (Not Found)
     */
    @GetMapping("/demanda-cursos/{id}")
    @Timed
    public ResponseEntity<DemandaCurso> getDemandaCurso(@PathVariable Long id) {
        log.debug("REST request to get DemandaCurso : {}", id);
        Optional<DemandaCurso> demandaCurso = demandaCursoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(demandaCurso);
    }

    /**
     * DELETE  /demanda-cursos/:id : delete the "id" demandaCurso.
     *
     * @param id the id of the demandaCurso to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/demanda-cursos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDemandaCurso(@PathVariable Long id) {
        log.debug("REST request to delete DemandaCurso : {}", id);

        demandaCursoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
