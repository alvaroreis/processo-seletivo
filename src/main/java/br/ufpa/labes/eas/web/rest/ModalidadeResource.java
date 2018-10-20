package br.ufpa.labes.eas.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufpa.labes.eas.domain.Modalidade;
import br.ufpa.labes.eas.repository.ModalidadeRepository;
import br.ufpa.labes.eas.web.rest.errors.BadRequestAlertException;
import br.ufpa.labes.eas.web.rest.util.HeaderUtil;
import br.ufpa.labes.eas.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Modalidade.
 */
@RestController
@RequestMapping("/api")
public class ModalidadeResource {

    private final Logger log = LoggerFactory.getLogger(ModalidadeResource.class);

    private static final String ENTITY_NAME = "modalidade";

    private ModalidadeRepository modalidadeRepository;

    public ModalidadeResource(ModalidadeRepository modalidadeRepository) {
        this.modalidadeRepository = modalidadeRepository;
    }

    /**
     * POST  /modalidades : Create a new modalidade.
     *
     * @param modalidade the modalidade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new modalidade, or with status 400 (Bad Request) if the modalidade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/modalidades")
    @Timed
    public ResponseEntity<Modalidade> createModalidade(@RequestBody Modalidade modalidade) throws URISyntaxException {
        log.debug("REST request to save Modalidade : {}", modalidade);
        if (modalidade.getId() != null) {
            throw new BadRequestAlertException("A new modalidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Modalidade result = modalidadeRepository.save(modalidade);
        return ResponseEntity.created(new URI("/api/modalidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /modalidades : Updates an existing modalidade.
     *
     * @param modalidade the modalidade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated modalidade,
     * or with status 400 (Bad Request) if the modalidade is not valid,
     * or with status 500 (Internal Server Error) if the modalidade couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/modalidades")
    @Timed
    public ResponseEntity<Modalidade> updateModalidade(@RequestBody Modalidade modalidade) throws URISyntaxException {
        log.debug("REST request to update Modalidade : {}", modalidade);
        if (modalidade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Modalidade result = modalidadeRepository.save(modalidade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, modalidade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /modalidades : get all the modalidades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of modalidades in body
     */
    @GetMapping("/modalidades")
    @Timed
    public ResponseEntity<List<Modalidade>> getAllModalidades(Pageable pageable) {
        log.debug("REST request to get a page of Modalidades");
        Page<Modalidade> page = modalidadeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/modalidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /modalidades/:id : get the "id" modalidade.
     *
     * @param id the id of the modalidade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the modalidade, or with status 404 (Not Found)
     */
    @GetMapping("/modalidades/{id}")
    @Timed
    public ResponseEntity<Modalidade> getModalidade(@PathVariable Long id) {
        log.debug("REST request to get Modalidade : {}", id);
        Optional<Modalidade> modalidade = modalidadeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(modalidade);
    }

    /**
     * DELETE  /modalidades/:id : delete the "id" modalidade.
     *
     * @param id the id of the modalidade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/modalidades/{id}")
    @Timed
    public ResponseEntity<Void> deleteModalidade(@PathVariable Long id) {
        log.debug("REST request to delete Modalidade : {}", id);

        modalidadeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
