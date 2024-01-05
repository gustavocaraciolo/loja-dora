package com.lojadora.web.rest;

import com.lojadora.domain.FluxoCaixa;
import com.lojadora.repository.FluxoCaixaRepository;
import com.lojadora.service.FluxoCaixaService;
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
 * REST controller for managing {@link com.lojadora.domain.FluxoCaixa}.
 */
@RestController
@RequestMapping("/api/fluxo-caixas")
public class FluxoCaixaResource {

    private final Logger log = LoggerFactory.getLogger(FluxoCaixaResource.class);

    private static final String ENTITY_NAME = "fluxoCaixa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FluxoCaixaService fluxoCaixaService;

    private final FluxoCaixaRepository fluxoCaixaRepository;

    public FluxoCaixaResource(FluxoCaixaService fluxoCaixaService, FluxoCaixaRepository fluxoCaixaRepository) {
        this.fluxoCaixaService = fluxoCaixaService;
        this.fluxoCaixaRepository = fluxoCaixaRepository;
    }

    /**
     * {@code POST  /fluxo-caixas} : Create a new fluxoCaixa.
     *
     * @param fluxoCaixa the fluxoCaixa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fluxoCaixa, or with status {@code 400 (Bad Request)} if the fluxoCaixa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FluxoCaixa> createFluxoCaixa(@RequestBody FluxoCaixa fluxoCaixa) throws URISyntaxException {
        log.debug("REST request to save FluxoCaixa : {}", fluxoCaixa);
        if (fluxoCaixa.getId() != null) {
            throw new BadRequestAlertException("A new fluxoCaixa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FluxoCaixa result = fluxoCaixaService.save(fluxoCaixa);
        return ResponseEntity
            .created(new URI("/api/fluxo-caixas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fluxo-caixas/:id} : Updates an existing fluxoCaixa.
     *
     * @param id the id of the fluxoCaixa to save.
     * @param fluxoCaixa the fluxoCaixa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fluxoCaixa,
     * or with status {@code 400 (Bad Request)} if the fluxoCaixa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fluxoCaixa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FluxoCaixa> updateFluxoCaixa(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FluxoCaixa fluxoCaixa
    ) throws URISyntaxException {
        log.debug("REST request to update FluxoCaixa : {}, {}", id, fluxoCaixa);
        if (fluxoCaixa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fluxoCaixa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fluxoCaixaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FluxoCaixa result = fluxoCaixaService.update(fluxoCaixa);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fluxoCaixa.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fluxo-caixas/:id} : Partial updates given fields of an existing fluxoCaixa, field will ignore if it is null
     *
     * @param id the id of the fluxoCaixa to save.
     * @param fluxoCaixa the fluxoCaixa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fluxoCaixa,
     * or with status {@code 400 (Bad Request)} if the fluxoCaixa is not valid,
     * or with status {@code 404 (Not Found)} if the fluxoCaixa is not found,
     * or with status {@code 500 (Internal Server Error)} if the fluxoCaixa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FluxoCaixa> partialUpdateFluxoCaixa(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FluxoCaixa fluxoCaixa
    ) throws URISyntaxException {
        log.debug("REST request to partial update FluxoCaixa partially : {}, {}", id, fluxoCaixa);
        if (fluxoCaixa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fluxoCaixa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fluxoCaixaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FluxoCaixa> result = fluxoCaixaService.partialUpdate(fluxoCaixa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fluxoCaixa.getId().toString())
        );
    }

    /**
     * {@code GET  /fluxo-caixas} : get all the fluxoCaixas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fluxoCaixas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FluxoCaixa>> getAllFluxoCaixas(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of FluxoCaixas");
        Page<FluxoCaixa> page;
        if (eagerload) {
            page = fluxoCaixaService.findAllWithEagerRelationships(pageable);
        } else {
            page = fluxoCaixaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fluxo-caixas/:id} : get the "id" fluxoCaixa.
     *
     * @param id the id of the fluxoCaixa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fluxoCaixa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FluxoCaixa> getFluxoCaixa(@PathVariable("id") Long id) {
        log.debug("REST request to get FluxoCaixa : {}", id);
        Optional<FluxoCaixa> fluxoCaixa = fluxoCaixaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fluxoCaixa);
    }

    /**
     * {@code DELETE  /fluxo-caixas/:id} : delete the "id" fluxoCaixa.
     *
     * @param id the id of the fluxoCaixa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFluxoCaixa(@PathVariable("id") Long id) {
        log.debug("REST request to delete FluxoCaixa : {}", id);
        fluxoCaixaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
