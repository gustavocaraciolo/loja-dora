package com.lojadora.web.rest;

import com.lojadora.domain.Entidades;
import com.lojadora.repository.EntidadesRepository;
import com.lojadora.service.EntidadesService;
import com.lojadora.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.lojadora.domain.Entidades}.
 */
@RestController
@RequestMapping("/api/entidades")
public class EntidadesResource {

    private final Logger log = LoggerFactory.getLogger(EntidadesResource.class);

    private static final String ENTITY_NAME = "entidades";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntidadesService entidadesService;

    private final EntidadesRepository entidadesRepository;

    public EntidadesResource(EntidadesService entidadesService, EntidadesRepository entidadesRepository) {
        this.entidadesService = entidadesService;
        this.entidadesRepository = entidadesRepository;
    }

    /**
     * {@code POST  /entidades} : Create a new entidades.
     *
     * @param entidades the entidades to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entidades, or with status {@code 400 (Bad Request)} if the entidades has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Entidades> createEntidades(@RequestBody Entidades entidades) throws URISyntaxException {
        log.debug("REST request to save Entidades : {}", entidades);
        if (entidades.getId() != null) {
            throw new BadRequestAlertException("A new entidades cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Entidades result = entidadesService.save(entidades);
        return ResponseEntity
            .created(new URI("/api/entidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entidades/:id} : Updates an existing entidades.
     *
     * @param id the id of the entidades to save.
     * @param entidades the entidades to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entidades,
     * or with status {@code 400 (Bad Request)} if the entidades is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entidades couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Entidades> updateEntidades(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Entidades entidades
    ) throws URISyntaxException {
        log.debug("REST request to update Entidades : {}, {}", id, entidades);
        if (entidades.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entidades.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entidadesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Entidades result = entidadesService.update(entidades);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entidades.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entidades/:id} : Partial updates given fields of an existing entidades, field will ignore if it is null
     *
     * @param id the id of the entidades to save.
     * @param entidades the entidades to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entidades,
     * or with status {@code 400 (Bad Request)} if the entidades is not valid,
     * or with status {@code 404 (Not Found)} if the entidades is not found,
     * or with status {@code 500 (Internal Server Error)} if the entidades couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Entidades> partialUpdateEntidades(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Entidades entidades
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entidades partially : {}, {}", id, entidades);
        if (entidades.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entidades.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entidadesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Entidades> result = entidadesService.partialUpdate(entidades);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entidades.getId().toString())
        );
    }

    /**
     * {@code GET  /entidades} : get all the entidades.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entidades in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Entidades>> getAllEntidades(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Entidades");
        Page<Entidades> page = entidadesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entidades/:id} : get the "id" entidades.
     *
     * @param id the id of the entidades to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entidades, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Entidades> getEntidades(@PathVariable("id") Long id) {
        log.debug("REST request to get Entidades : {}", id);
        Optional<Entidades> entidades = entidadesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entidades);
    }

    /**
     * {@code DELETE  /entidades/:id} : delete the "id" entidades.
     *
     * @param id the id of the entidades to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntidades(@PathVariable("id") Long id) {
        log.debug("REST request to delete Entidades : {}", id);
        entidadesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
