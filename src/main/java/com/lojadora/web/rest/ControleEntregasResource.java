package com.lojadora.web.rest;

import com.lojadora.domain.ControleEntregas;
import com.lojadora.repository.ControleEntregasRepository;
import com.lojadora.service.ControleEntregasService;
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
 * REST controller for managing {@link com.lojadora.domain.ControleEntregas}.
 */
@RestController
@RequestMapping("/api/controle-entregases")
public class ControleEntregasResource {

    private final Logger log = LoggerFactory.getLogger(ControleEntregasResource.class);

    private static final String ENTITY_NAME = "controleEntregas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControleEntregasService controleEntregasService;

    private final ControleEntregasRepository controleEntregasRepository;

    public ControleEntregasResource(
        ControleEntregasService controleEntregasService,
        ControleEntregasRepository controleEntregasRepository
    ) {
        this.controleEntregasService = controleEntregasService;
        this.controleEntregasRepository = controleEntregasRepository;
    }

    /**
     * {@code POST  /controle-entregases} : Create a new controleEntregas.
     *
     * @param controleEntregas the controleEntregas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new controleEntregas, or with status {@code 400 (Bad Request)} if the controleEntregas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ControleEntregas> createControleEntregas(@RequestBody ControleEntregas controleEntregas)
        throws URISyntaxException {
        log.debug("REST request to save ControleEntregas : {}", controleEntregas);
        if (controleEntregas.getId() != null) {
            throw new BadRequestAlertException("A new controleEntregas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControleEntregas result = controleEntregasService.save(controleEntregas);
        return ResponseEntity
            .created(new URI("/api/controle-entregases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /controle-entregases/:id} : Updates an existing controleEntregas.
     *
     * @param id the id of the controleEntregas to save.
     * @param controleEntregas the controleEntregas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controleEntregas,
     * or with status {@code 400 (Bad Request)} if the controleEntregas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the controleEntregas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ControleEntregas> updateControleEntregas(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ControleEntregas controleEntregas
    ) throws URISyntaxException {
        log.debug("REST request to update ControleEntregas : {}, {}", id, controleEntregas);
        if (controleEntregas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controleEntregas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controleEntregasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ControleEntregas result = controleEntregasService.update(controleEntregas);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controleEntregas.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /controle-entregases/:id} : Partial updates given fields of an existing controleEntregas, field will ignore if it is null
     *
     * @param id the id of the controleEntregas to save.
     * @param controleEntregas the controleEntregas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controleEntregas,
     * or with status {@code 400 (Bad Request)} if the controleEntregas is not valid,
     * or with status {@code 404 (Not Found)} if the controleEntregas is not found,
     * or with status {@code 500 (Internal Server Error)} if the controleEntregas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ControleEntregas> partialUpdateControleEntregas(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ControleEntregas controleEntregas
    ) throws URISyntaxException {
        log.debug("REST request to partial update ControleEntregas partially : {}, {}", id, controleEntregas);
        if (controleEntregas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controleEntregas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controleEntregasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ControleEntregas> result = controleEntregasService.partialUpdate(controleEntregas);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controleEntregas.getId().toString())
        );
    }

    /**
     * {@code GET  /controle-entregases} : get all the controleEntregases.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controleEntregases in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ControleEntregas>> getAllControleEntregases(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of ControleEntregases");
        Page<ControleEntregas> page;
        if (eagerload) {
            page = controleEntregasService.findAllWithEagerRelationships(pageable);
        } else {
            page = controleEntregasService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /controle-entregases/:id} : get the "id" controleEntregas.
     *
     * @param id the id of the controleEntregas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the controleEntregas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ControleEntregas> getControleEntregas(@PathVariable("id") Long id) {
        log.debug("REST request to get ControleEntregas : {}", id);
        Optional<ControleEntregas> controleEntregas = controleEntregasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(controleEntregas);
    }

    /**
     * {@code DELETE  /controle-entregases/:id} : delete the "id" controleEntregas.
     *
     * @param id the id of the controleEntregas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteControleEntregas(@PathVariable("id") Long id) {
        log.debug("REST request to delete ControleEntregas : {}", id);
        controleEntregasService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
