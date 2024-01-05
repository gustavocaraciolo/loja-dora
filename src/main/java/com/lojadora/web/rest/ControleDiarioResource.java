package com.lojadora.web.rest;

import com.lojadora.domain.ControleDiario;
import com.lojadora.repository.ControleDiarioRepository;
import com.lojadora.service.ControleDiarioService;
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
 * REST controller for managing {@link com.lojadora.domain.ControleDiario}.
 */
@RestController
@RequestMapping("/api/controle-diarios")
public class ControleDiarioResource {

    private final Logger log = LoggerFactory.getLogger(ControleDiarioResource.class);

    private static final String ENTITY_NAME = "controleDiario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControleDiarioService controleDiarioService;

    private final ControleDiarioRepository controleDiarioRepository;

    public ControleDiarioResource(ControleDiarioService controleDiarioService, ControleDiarioRepository controleDiarioRepository) {
        this.controleDiarioService = controleDiarioService;
        this.controleDiarioRepository = controleDiarioRepository;
    }

    /**
     * {@code POST  /controle-diarios} : Create a new controleDiario.
     *
     * @param controleDiario the controleDiario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new controleDiario, or with status {@code 400 (Bad Request)} if the controleDiario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ControleDiario> createControleDiario(@RequestBody ControleDiario controleDiario) throws URISyntaxException {
        log.debug("REST request to save ControleDiario : {}", controleDiario);
        if (controleDiario.getId() != null) {
            throw new BadRequestAlertException("A new controleDiario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControleDiario result = controleDiarioService.save(controleDiario);
        return ResponseEntity
            .created(new URI("/api/controle-diarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /controle-diarios/:id} : Updates an existing controleDiario.
     *
     * @param id the id of the controleDiario to save.
     * @param controleDiario the controleDiario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controleDiario,
     * or with status {@code 400 (Bad Request)} if the controleDiario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the controleDiario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ControleDiario> updateControleDiario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ControleDiario controleDiario
    ) throws URISyntaxException {
        log.debug("REST request to update ControleDiario : {}, {}", id, controleDiario);
        if (controleDiario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controleDiario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controleDiarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ControleDiario result = controleDiarioService.update(controleDiario);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controleDiario.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /controle-diarios/:id} : Partial updates given fields of an existing controleDiario, field will ignore if it is null
     *
     * @param id the id of the controleDiario to save.
     * @param controleDiario the controleDiario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controleDiario,
     * or with status {@code 400 (Bad Request)} if the controleDiario is not valid,
     * or with status {@code 404 (Not Found)} if the controleDiario is not found,
     * or with status {@code 500 (Internal Server Error)} if the controleDiario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ControleDiario> partialUpdateControleDiario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ControleDiario controleDiario
    ) throws URISyntaxException {
        log.debug("REST request to partial update ControleDiario partially : {}, {}", id, controleDiario);
        if (controleDiario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controleDiario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controleDiarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ControleDiario> result = controleDiarioService.partialUpdate(controleDiario);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controleDiario.getId().toString())
        );
    }

    /**
     * {@code GET  /controle-diarios} : get all the controleDiarios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controleDiarios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ControleDiario>> getAllControleDiarios(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ControleDiarios");
        Page<ControleDiario> page = controleDiarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /controle-diarios/:id} : get the "id" controleDiario.
     *
     * @param id the id of the controleDiario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the controleDiario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ControleDiario> getControleDiario(@PathVariable("id") Long id) {
        log.debug("REST request to get ControleDiario : {}", id);
        Optional<ControleDiario> controleDiario = controleDiarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(controleDiario);
    }

    /**
     * {@code DELETE  /controle-diarios/:id} : delete the "id" controleDiario.
     *
     * @param id the id of the controleDiario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteControleDiario(@PathVariable("id") Long id) {
        log.debug("REST request to delete ControleDiario : {}", id);
        controleDiarioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
