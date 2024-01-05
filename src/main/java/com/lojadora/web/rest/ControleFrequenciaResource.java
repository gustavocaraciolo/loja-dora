package com.lojadora.web.rest;

import com.lojadora.domain.ControleFrequencia;
import com.lojadora.repository.ControleFrequenciaRepository;
import com.lojadora.service.ControleFrequenciaService;
import com.lojadora.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.lojadora.domain.ControleFrequencia}.
 */
@RestController
@RequestMapping("/api/controle-frequencias")
public class ControleFrequenciaResource {

    private final Logger log = LoggerFactory.getLogger(ControleFrequenciaResource.class);

    private static final String ENTITY_NAME = "controleFrequencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControleFrequenciaService controleFrequenciaService;

    private final ControleFrequenciaRepository controleFrequenciaRepository;

    public ControleFrequenciaResource(
        ControleFrequenciaService controleFrequenciaService,
        ControleFrequenciaRepository controleFrequenciaRepository
    ) {
        this.controleFrequenciaService = controleFrequenciaService;
        this.controleFrequenciaRepository = controleFrequenciaRepository;
    }

    /**
     * {@code POST  /controle-frequencias} : Create a new controleFrequencia.
     *
     * @param controleFrequencia the controleFrequencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new controleFrequencia, or with status {@code 400 (Bad Request)} if the controleFrequencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ControleFrequencia> createControleFrequencia(@RequestBody ControleFrequencia controleFrequencia)
        throws URISyntaxException {
        log.debug("REST request to save ControleFrequencia : {}", controleFrequencia);
        if (controleFrequencia.getId() != null) {
            throw new BadRequestAlertException("A new controleFrequencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControleFrequencia result = controleFrequenciaService.save(controleFrequencia);
        return ResponseEntity
            .created(new URI("/api/controle-frequencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /controle-frequencias/:id} : Updates an existing controleFrequencia.
     *
     * @param id the id of the controleFrequencia to save.
     * @param controleFrequencia the controleFrequencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controleFrequencia,
     * or with status {@code 400 (Bad Request)} if the controleFrequencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the controleFrequencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ControleFrequencia> updateControleFrequencia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ControleFrequencia controleFrequencia
    ) throws URISyntaxException {
        log.debug("REST request to update ControleFrequencia : {}, {}", id, controleFrequencia);
        if (controleFrequencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controleFrequencia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controleFrequenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ControleFrequencia result = controleFrequenciaService.update(controleFrequencia);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controleFrequencia.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /controle-frequencias/:id} : Partial updates given fields of an existing controleFrequencia, field will ignore if it is null
     *
     * @param id the id of the controleFrequencia to save.
     * @param controleFrequencia the controleFrequencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controleFrequencia,
     * or with status {@code 400 (Bad Request)} if the controleFrequencia is not valid,
     * or with status {@code 404 (Not Found)} if the controleFrequencia is not found,
     * or with status {@code 500 (Internal Server Error)} if the controleFrequencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ControleFrequencia> partialUpdateControleFrequencia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ControleFrequencia controleFrequencia
    ) throws URISyntaxException {
        log.debug("REST request to partial update ControleFrequencia partially : {}, {}", id, controleFrequencia);
        if (controleFrequencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controleFrequencia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controleFrequenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ControleFrequencia> result = controleFrequenciaService.partialUpdate(controleFrequencia);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controleFrequencia.getId().toString())
        );
    }

    /**
     * {@code GET  /controle-frequencias} : get all the controleFrequencias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controleFrequencias in body.
     */
    @GetMapping("")
    public List<ControleFrequencia> getAllControleFrequencias() {
        log.debug("REST request to get all ControleFrequencias");
        return controleFrequenciaService.findAll();
    }

    /**
     * {@code GET  /controle-frequencias/:id} : get the "id" controleFrequencia.
     *
     * @param id the id of the controleFrequencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the controleFrequencia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ControleFrequencia> getControleFrequencia(@PathVariable("id") Long id) {
        log.debug("REST request to get ControleFrequencia : {}", id);
        Optional<ControleFrequencia> controleFrequencia = controleFrequenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(controleFrequencia);
    }

    /**
     * {@code DELETE  /controle-frequencias/:id} : delete the "id" controleFrequencia.
     *
     * @param id the id of the controleFrequencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteControleFrequencia(@PathVariable("id") Long id) {
        log.debug("REST request to delete ControleFrequencia : {}", id);
        controleFrequenciaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
