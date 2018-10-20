package br.ufpa.labes.eas.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.ufpa.labes.eas.domain.PeriodoOferta;
import br.ufpa.labes.eas.repository.PeriodoOfertaRepository;
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
 * REST controller for managing PeriodoOferta.
 */
@RestController
@RequestMapping("/api")
public class PeriodoOfertaResource {

    private final Logger log = LoggerFactory.getLogger(PeriodoOfertaResource.class);

    private static final String ENTITY_NAME = "periodoOferta";

    private PeriodoOfertaRepository periodoOfertaRepository;

    public PeriodoOfertaResource(PeriodoOfertaRepository periodoOfertaRepository) {
        this.periodoOfertaRepository = periodoOfertaRepository;
    }

    /**
     * POST  /periodo-ofertas : Create a new periodoOferta.
     *
     * @param periodoOferta the periodoOferta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodoOferta, or with status 400 (Bad Request) if the periodoOferta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/periodo-ofertas")
    @Timed
    public ResponseEntity<PeriodoOferta> createPeriodoOferta(@RequestBody PeriodoOferta periodoOferta) throws URISyntaxException {
        log.debug("REST request to save PeriodoOferta : {}", periodoOferta);
        if (periodoOferta.getId() != null) {
            throw new BadRequestAlertException("A new periodoOferta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodoOferta result = periodoOfertaRepository.save(periodoOferta);
        return ResponseEntity.created(new URI("/api/periodo-ofertas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /periodo-ofertas : Updates an existing periodoOferta.
     *
     * @param periodoOferta the periodoOferta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodoOferta,
     * or with status 400 (Bad Request) if the periodoOferta is not valid,
     * or with status 500 (Internal Server Error) if the periodoOferta couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/periodo-ofertas")
    @Timed
    public ResponseEntity<PeriodoOferta> updatePeriodoOferta(@RequestBody PeriodoOferta periodoOferta) throws URISyntaxException {
        log.debug("REST request to update PeriodoOferta : {}", periodoOferta);
        if (periodoOferta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodoOferta result = periodoOfertaRepository.save(periodoOferta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodoOferta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /periodo-ofertas : get all the periodoOfertas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of periodoOfertas in body
     */
    @GetMapping("/periodo-ofertas")
    @Timed
    public List<PeriodoOferta> getAllPeriodoOfertas() {
        log.debug("REST request to get all PeriodoOfertas");
        return periodoOfertaRepository.findAll();
    }

    /**
     * GET  /periodo-ofertas/:id : get the "id" periodoOferta.
     *
     * @param id the id of the periodoOferta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the periodoOferta, or with status 404 (Not Found)
     */
    @GetMapping("/periodo-ofertas/{id}")
    @Timed
    public ResponseEntity<PeriodoOferta> getPeriodoOferta(@PathVariable Long id) {
        log.debug("REST request to get PeriodoOferta : {}", id);
        Optional<PeriodoOferta> periodoOferta = periodoOfertaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(periodoOferta);
    }

    /**
     * DELETE  /periodo-ofertas/:id : delete the "id" periodoOferta.
     *
     * @param id the id of the periodoOferta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/periodo-ofertas/{id}")
    @Timed
    public ResponseEntity<Void> deletePeriodoOferta(@PathVariable Long id) {
        log.debug("REST request to delete PeriodoOferta : {}", id);

        periodoOfertaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
