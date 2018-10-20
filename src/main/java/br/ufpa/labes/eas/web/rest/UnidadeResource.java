package br.ufpa.labes.eas.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufpa.labes.eas.domain.Unidade;
import br.ufpa.labes.eas.repository.UnidadeRepository;
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
 * REST controller for managing Unidade.
 */
@RestController
@RequestMapping("/api")
public class UnidadeResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeResource.class);

    private static final String ENTITY_NAME = "unidade";

    private UnidadeRepository unidadeRepository;

    public UnidadeResource(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    /**
     * POST  /unidades : Create a new unidade.
     *
     * @param unidade the unidade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unidade, or with status 400 (Bad Request) if the unidade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unidades")
    @Timed
    public ResponseEntity<Unidade> createUnidade(@RequestBody Unidade unidade) throws URISyntaxException {
        log.debug("REST request to save Unidade : {}", unidade);
        if (unidade.getId() != null) {
            throw new BadRequestAlertException("A new unidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Unidade result = unidadeRepository.save(unidade);
        return ResponseEntity.created(new URI("/api/unidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unidades : Updates an existing unidade.
     *
     * @param unidade the unidade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unidade,
     * or with status 400 (Bad Request) if the unidade is not valid,
     * or with status 500 (Internal Server Error) if the unidade couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unidades")
    @Timed
    public ResponseEntity<Unidade> updateUnidade(@RequestBody Unidade unidade) throws URISyntaxException {
        log.debug("REST request to update Unidade : {}", unidade);
        if (unidade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Unidade result = unidadeRepository.save(unidade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unidade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unidades : get all the unidades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of unidades in body
     */
    @GetMapping("/unidades")
    @Timed
    public ResponseEntity<List<Unidade>> getAllUnidades(Pageable pageable) {
        log.debug("REST request to get a page of Unidades");
        Page<Unidade> page = unidadeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/unidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /unidades/:id : get the "id" unidade.
     *
     * @param id the id of the unidade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unidade, or with status 404 (Not Found)
     */
    @GetMapping("/unidades/{id}")
    @Timed
    public ResponseEntity<Unidade> getUnidade(@PathVariable Long id) {
        log.debug("REST request to get Unidade : {}", id);
        Optional<Unidade> unidade = unidadeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(unidade);
    }

    /**
     * DELETE  /unidades/:id : delete the "id" unidade.
     *
     * @param id the id of the unidade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unidades/{id}")
    @Timed
    public ResponseEntity<Void> deleteUnidade(@PathVariable Long id) {
        log.debug("REST request to delete Unidade : {}", id);

        unidadeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
