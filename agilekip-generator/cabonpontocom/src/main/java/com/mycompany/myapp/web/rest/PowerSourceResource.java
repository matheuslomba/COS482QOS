package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PowerSourceRepository;
import com.mycompany.myapp.service.PowerSourceService;
import com.mycompany.myapp.service.dto.PowerSourceDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PowerSource}.
 */
@RestController
@RequestMapping("/api")
public class PowerSourceResource {

    private final Logger log = LoggerFactory.getLogger(PowerSourceResource.class);

    private static final String ENTITY_NAME = "powerSource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PowerSourceService powerSourceService;

    private final PowerSourceRepository powerSourceRepository;

    public PowerSourceResource(PowerSourceService powerSourceService, PowerSourceRepository powerSourceRepository) {
        this.powerSourceService = powerSourceService;
        this.powerSourceRepository = powerSourceRepository;
    }

    /**
     * {@code POST  /power-sources} : Create a new powerSource.
     *
     * @param powerSourceDTO the powerSourceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new powerSourceDTO, or with status {@code 400 (Bad Request)} if the powerSource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/power-sources")
    public ResponseEntity<PowerSourceDTO> createPowerSource(@RequestBody PowerSourceDTO powerSourceDTO) throws URISyntaxException {
        log.debug("REST request to save PowerSource : {}", powerSourceDTO);
        if (powerSourceDTO.getId() != null) {
            throw new BadRequestAlertException("A new powerSource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PowerSourceDTO result = powerSourceService.save(powerSourceDTO);
        return ResponseEntity
            .created(new URI("/api/power-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /power-sources/:id} : Updates an existing powerSource.
     *
     * @param id the id of the powerSourceDTO to save.
     * @param powerSourceDTO the powerSourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated powerSourceDTO,
     * or with status {@code 400 (Bad Request)} if the powerSourceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the powerSourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/power-sources/{id}")
    public ResponseEntity<PowerSourceDTO> updatePowerSource(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PowerSourceDTO powerSourceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PowerSource : {}, {}", id, powerSourceDTO);
        if (powerSourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, powerSourceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!powerSourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PowerSourceDTO result = powerSourceService.save(powerSourceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, powerSourceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /power-sources/:id} : Partial updates given fields of an existing powerSource, field will ignore if it is null
     *
     * @param id the id of the powerSourceDTO to save.
     * @param powerSourceDTO the powerSourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated powerSourceDTO,
     * or with status {@code 400 (Bad Request)} if the powerSourceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the powerSourceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the powerSourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/power-sources/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PowerSourceDTO> partialUpdatePowerSource(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PowerSourceDTO powerSourceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PowerSource partially : {}, {}", id, powerSourceDTO);
        if (powerSourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, powerSourceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!powerSourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PowerSourceDTO> result = powerSourceService.partialUpdate(powerSourceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, powerSourceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /power-sources} : get all the powerSources.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of powerSources in body.
     */
    @GetMapping("/power-sources")
    public List<PowerSourceDTO> getAllPowerSources() {
        log.debug("REST request to get all PowerSources");
        return powerSourceService.findAll();
    }

    /**
     * {@code GET  /power-sources/:id} : get the "id" powerSource.
     *
     * @param id the id of the powerSourceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the powerSourceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/power-sources/{id}")
    public ResponseEntity<PowerSourceDTO> getPowerSource(@PathVariable Long id) {
        log.debug("REST request to get PowerSource : {}", id);
        Optional<PowerSourceDTO> powerSourceDTO = powerSourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(powerSourceDTO);
    }

    /**
     * {@code DELETE  /power-sources/:id} : delete the "id" powerSource.
     *
     * @param id the id of the powerSourceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/power-sources/{id}")
    public ResponseEntity<Void> deletePowerSource(@PathVariable Long id) {
        log.debug("REST request to delete PowerSource : {}", id);
        powerSourceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
