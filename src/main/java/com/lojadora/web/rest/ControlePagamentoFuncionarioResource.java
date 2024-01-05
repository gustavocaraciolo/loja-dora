package com.lojadora.web.rest;

import com.lojadora.domain.ControlePagamentoFuncionario;
import com.lojadora.repository.ControlePagamentoFuncionarioRepository;
import com.lojadora.service.ControlePagamentoFuncionarioService;
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
 * REST controller for managing {@link com.lojadora.domain.ControlePagamentoFuncionario}.
 */
@RestController
@RequestMapping("/api/controle-pagamento-funcionarios")
public class ControlePagamentoFuncionarioResource {

    private final Logger log = LoggerFactory.getLogger(ControlePagamentoFuncionarioResource.class);

    private static final String ENTITY_NAME = "controlePagamentoFuncionario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControlePagamentoFuncionarioService controlePagamentoFuncionarioService;

    private final ControlePagamentoFuncionarioRepository controlePagamentoFuncionarioRepository;

    public ControlePagamentoFuncionarioResource(
        ControlePagamentoFuncionarioService controlePagamentoFuncionarioService,
        ControlePagamentoFuncionarioRepository controlePagamentoFuncionarioRepository
    ) {
        this.controlePagamentoFuncionarioService = controlePagamentoFuncionarioService;
        this.controlePagamentoFuncionarioRepository = controlePagamentoFuncionarioRepository;
    }

    /**
     * {@code POST  /controle-pagamento-funcionarios} : Create a new controlePagamentoFuncionario.
     *
     * @param controlePagamentoFuncionario the controlePagamentoFuncionario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new controlePagamentoFuncionario, or with status {@code 400 (Bad Request)} if the controlePagamentoFuncionario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ControlePagamentoFuncionario> createControlePagamentoFuncionario(
        @RequestBody ControlePagamentoFuncionario controlePagamentoFuncionario
    ) throws URISyntaxException {
        log.debug("REST request to save ControlePagamentoFuncionario : {}", controlePagamentoFuncionario);
        if (controlePagamentoFuncionario.getId() != null) {
            throw new BadRequestAlertException("A new controlePagamentoFuncionario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControlePagamentoFuncionario result = controlePagamentoFuncionarioService.save(controlePagamentoFuncionario);
        return ResponseEntity
            .created(new URI("/api/controle-pagamento-funcionarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /controle-pagamento-funcionarios/:id} : Updates an existing controlePagamentoFuncionario.
     *
     * @param id the id of the controlePagamentoFuncionario to save.
     * @param controlePagamentoFuncionario the controlePagamentoFuncionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controlePagamentoFuncionario,
     * or with status {@code 400 (Bad Request)} if the controlePagamentoFuncionario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the controlePagamentoFuncionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ControlePagamentoFuncionario> updateControlePagamentoFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ControlePagamentoFuncionario controlePagamentoFuncionario
    ) throws URISyntaxException {
        log.debug("REST request to update ControlePagamentoFuncionario : {}, {}", id, controlePagamentoFuncionario);
        if (controlePagamentoFuncionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controlePagamentoFuncionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controlePagamentoFuncionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ControlePagamentoFuncionario result = controlePagamentoFuncionarioService.update(controlePagamentoFuncionario);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controlePagamentoFuncionario.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /controle-pagamento-funcionarios/:id} : Partial updates given fields of an existing controlePagamentoFuncionario, field will ignore if it is null
     *
     * @param id the id of the controlePagamentoFuncionario to save.
     * @param controlePagamentoFuncionario the controlePagamentoFuncionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controlePagamentoFuncionario,
     * or with status {@code 400 (Bad Request)} if the controlePagamentoFuncionario is not valid,
     * or with status {@code 404 (Not Found)} if the controlePagamentoFuncionario is not found,
     * or with status {@code 500 (Internal Server Error)} if the controlePagamentoFuncionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ControlePagamentoFuncionario> partialUpdateControlePagamentoFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ControlePagamentoFuncionario controlePagamentoFuncionario
    ) throws URISyntaxException {
        log.debug("REST request to partial update ControlePagamentoFuncionario partially : {}, {}", id, controlePagamentoFuncionario);
        if (controlePagamentoFuncionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controlePagamentoFuncionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controlePagamentoFuncionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ControlePagamentoFuncionario> result = controlePagamentoFuncionarioService.partialUpdate(controlePagamentoFuncionario);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controlePagamentoFuncionario.getId().toString())
        );
    }

    /**
     * {@code GET  /controle-pagamento-funcionarios} : get all the controlePagamentoFuncionarios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controlePagamentoFuncionarios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ControlePagamentoFuncionario>> getAllControlePagamentoFuncionarios(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ControlePagamentoFuncionarios");
        Page<ControlePagamentoFuncionario> page = controlePagamentoFuncionarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /controle-pagamento-funcionarios/:id} : get the "id" controlePagamentoFuncionario.
     *
     * @param id the id of the controlePagamentoFuncionario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the controlePagamentoFuncionario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ControlePagamentoFuncionario> getControlePagamentoFuncionario(@PathVariable("id") Long id) {
        log.debug("REST request to get ControlePagamentoFuncionario : {}", id);
        Optional<ControlePagamentoFuncionario> controlePagamentoFuncionario = controlePagamentoFuncionarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(controlePagamentoFuncionario);
    }

    /**
     * {@code DELETE  /controle-pagamento-funcionarios/:id} : delete the "id" controlePagamentoFuncionario.
     *
     * @param id the id of the controlePagamentoFuncionario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteControlePagamentoFuncionario(@PathVariable("id") Long id) {
        log.debug("REST request to delete ControlePagamentoFuncionario : {}", id);
        controlePagamentoFuncionarioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
