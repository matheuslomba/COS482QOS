package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.RamRepository;
import com.mycompany.myapp.service.RamService;
import com.mycompany.myapp.service.dto.RamDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Ram}.
 */
@RestController
@RequestMapping("/api")
public class RamResource {

    private final Logger log = LoggerFactory.getLogger(RamResource.class);

    private static final String ENTITY_NAME = "ram";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RamService ramService;

    private final RamRepository ramRepository;

    public RamResource(RamService ramService, RamRepository ramRepository) {
        this.ramService = ramService;
        this.ramRepository = ramRepository;
    }

    /**
     * {@code POST  /rams} : Create a new ram.
     *
     * @param ramDTO the ramDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ramDTO, or with status {@code 400 (Bad Request)} if the ram has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rams")
    public ResponseEntity<RamDTO> createRam(@RequestBody RamDTO ramDTO) throws URISyntaxException {
        log.debug("REST request to save Ram : {}", ramDTO);
        if (ramDTO.getId() != null) {
            throw new BadRequestAlertException("A new ram cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RamDTO result = ramService.save(ramDTO);
        return ResponseEntity
            .created(new URI("/api/rams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rams/:id} : Updates an existing ram.
     *
     * @param id the id of the ramDTO to save.
     * @param ramDTO the ramDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ramDTO,
     * or with status {@code 400 (Bad Request)} if the ramDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ramDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rams/{id}")
    public ResponseEntity<RamDTO> updateRam(@PathVariable(value = "id", required = false) final Long id, @RequestBody RamDTO ramDTO)
        throws URISyntaxException {
        log.debug("REST request to update Ram : {}, {}", id, ramDTO);
        if (ramDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ramDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ramRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RamDTO result = ramService.save(ramDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ramDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rams/:id} : Partial updates given fields of an existing ram, field will ignore if it is null
     *
     * @param id the id of the ramDTO to save.
     * @param ramDTO the ramDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ramDTO,
     * or with status {@code 400 (Bad Request)} if the ramDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ramDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ramDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rams/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RamDTO> partialUpdateRam(@PathVariable(value = "id", required = false) final Long id, @RequestBody RamDTO ramDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Ram partially : {}, {}", id, ramDTO);
        if (ramDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ramDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ramRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RamDTO> result = ramService.partialUpdate(ramDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ramDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rams} : get all the rams.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rams in body.
     */
    @GetMapping("/rams")
    public List<RamDTO> getAllRams() {
        log.debug("REST request to get all Rams");
        return ramService.findAll();
    }

    /**
     * {@code GET  /rams/:id} : get the "id" ram.
     *
     * @param id the id of the ramDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ramDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rams/{id}")
    public ResponseEntity<RamDTO> getRam(@PathVariable Long id) {
        log.debug("REST request to get Ram : {}", id);
        Optional<RamDTO> ramDTO = ramService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ramDTO);
    }

    /**
     * {@code DELETE  /rams/:id} : delete the "id" ram.
     *
     * @param id the id of the ramDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rams/{id}")
    public ResponseEntity<Void> deleteRam(@PathVariable Long id) {
        log.debug("REST request to delete Ram : {}", id);
        ramService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
