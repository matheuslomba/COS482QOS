package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ClientOrderProcessService;
import com.mycompany.myapp.service.dto.ClientOrderProcessDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ClientOrderProcess}.
 */
@RestController
@RequestMapping("/api")
public class ClientOrderProcessResource {

    private final Logger log = LoggerFactory.getLogger(ClientOrderProcessResource.class);

    private static final String ENTITY_NAME = "clientOrderProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientOrderProcessService clientOrderProcessService;

    public ClientOrderProcessResource(ClientOrderProcessService clientOrderProcessService) {
        this.clientOrderProcessService = clientOrderProcessService;
    }

    /**
     * {@code POST  /client-order-processes} : Create a new clientOrderProcess.
     *
     * @param clientOrderProcessDTO the clientOrderProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientOrderProcessDTO, or with status {@code 400 (Bad Request)} if the clientOrderProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-order-processes")
    public ResponseEntity<ClientOrderProcessDTO> create(@RequestBody ClientOrderProcessDTO clientOrderProcessDTO)
        throws URISyntaxException {
        log.debug("REST request to save ClientOrderProcess : {}", clientOrderProcessDTO);
        if (clientOrderProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientOrderProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientOrderProcessDTO result = clientOrderProcessService.create(clientOrderProcessDTO);
        return ResponseEntity
            .created(new URI("/api/client-order-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /client-order-processes} : get all the clientOrderProcesss.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientOrderProcesss in body.
     */
    @GetMapping("/client-order-processes")
    public List<ClientOrderProcessDTO> getAllClientOrderProcesss() {
        log.debug("REST request to get all ClientOrderProcesss");
        return clientOrderProcessService.findAll();
    }

    /**
     * {@code GET  /client-order-processes/:id} : get the "id" clientOrderProcess.
     *
     * @param processInstanceId the id of the clientOrderProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientOrderProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-order-processes/{processInstanceId}")
    public ResponseEntity<ClientOrderProcessDTO> getByProcessInstanceId(@PathVariable Long processInstanceId) {
        log.debug("REST request to get ClientOrderProcess by processInstanceId : {}", processInstanceId);
        Optional<ClientOrderProcessDTO> clientOrderProcessDTO = clientOrderProcessService.findByProcessInstanceId(processInstanceId);
        return ResponseUtil.wrapOrNotFound(clientOrderProcessDTO);
    }
}
