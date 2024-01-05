package com.lojadora.web.rest;

import com.lojadora.domain.ControleAjustes;
import com.lojadora.repository.ControleAjustesRepository;
import com.lojadora.service.ControleAjustesService;
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
 * REST controller for managing {@link com.lojadora.domain.ControleAjustes}.
 */
@RestController
@RequestMapping("/api/controle-ajustes")
public class ControleAjustesResource {

    private final Logger log = LoggerFactory.getLogger(ControleAjustesResource.class);

    private static final String ENTITY_NAME = "controleAjustes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControleAjustesService controleAjustesService;

    private final ControleAjustesRepository controleAjustesRepository;

    public ControleAjustesResource(ControleAjustesService controleAjustesService, ControleAjustesRepository controleAjustesRepository) {
        this.controleAjustesService = controleAjustesService;
        this.controleAjustesRepository = controleAjustesRepository;
    }

    /**
     * {@code POST  /controle-ajustes} : Create a new controleAjustes.
     *
     * @param controleAjustes the controleAjustes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new controleAjustes, or with status {@code 400 (Bad Request)} if the controleAjustes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ControleAjustes> createControleAjustes(@RequestBody ControleAjustes controleAjustes) throws URISyntaxException {
        log.debug("REST request to save ControleAjustes : {}", controleAjustes);
        if (controleAjustes.getId() != null) {
            throw new BadRequestAlertException("A new controleAjustes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControleAjustes result = controleAjustesService.save(controleAjustes);
        return ResponseEntity
            .created(new URI("/api/controle-ajustes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /controle-ajustes/:id} : Updates an existing controleAjustes.
     *
     * @param id the id of the controleAjustes to save.
     * @param controleAjustes the controleAjustes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controleAjustes,
     * or with status {@code 400 (Bad Request)} if the controleAjustes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the controleAjustes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ControleAjustes> updateControleAjustes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ControleAjustes controleAjustes
    ) throws URISyntaxException {
        log.debug("REST request to update ControleAjustes : {}, {}", id, controleAjustes);
        if (controleAjustes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controleAjustes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controleAjustesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ControleAjustes result = controleAjustesService.update(controleAjustes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controleAjustes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /controle-ajustes/:id} : Partial updates given fields of an existing controleAjustes, field will ignore if it is null
     *
     * @param id the id of the controleAjustes to save.
     * @param controleAjustes the controleAjustes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controleAjustes,
     * or with status {@code 400 (Bad Request)} if the controleAjustes is not valid,
     * or with status {@code 404 (Not Found)} if the controleAjustes is not found,
     * or with status {@code 500 (Internal Server Error)} if the controleAjustes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ControleAjustes> partialUpdateControleAjustes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ControleAjustes controleAjustes
    ) throws URISyntaxException {
        log.debug("REST request to partial update ControleAjustes partially : {}, {}", id, controleAjustes);
        if (controleAjustes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controleAjustes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controleAjustesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ControleAjustes> result = controleAjustesService.partialUpdate(controleAjustes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controleAjustes.getId().toString())
        );
    }

    /**
     * {@code GET  /controle-ajustes} : get all the controleAjustes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controleAjustes in body.
     */
    @GetMapping("")
    public List<ControleAjustes> getAllControleAjustes(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ControleAjustes");
        return controleAjustesService.findAll();
    }

    /**
     * {@code GET  /controle-ajustes/:id} : get the "id" controleAjustes.
     *
     * @param id the id of the controleAjustes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the controleAjustes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ControleAjustes> getControleAjustes(@PathVariable("id") Long id) {
        log.debug("REST request to get ControleAjustes : {}", id);
        Optional<ControleAjustes> controleAjustes = controleAjustesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(controleAjustes);
    }

    /**
     * {@code DELETE  /controle-ajustes/:id} : delete the "id" controleAjustes.
     *
     * @param id the id of the controleAjustes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteControleAjustes(@PathVariable("id") Long id) {
        log.debug("REST request to delete ControleAjustes : {}", id);
        controleAjustesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
