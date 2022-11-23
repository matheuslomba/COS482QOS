package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.HdRepository;
import com.mycompany.myapp.service.HdService;
import com.mycompany.myapp.service.dto.HdDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Hd}.
 */
@RestController
@RequestMapping("/api")
public class HdResource {

    private final Logger log = LoggerFactory.getLogger(HdResource.class);

    private static final String ENTITY_NAME = "hd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HdService hdService;

    private final HdRepository hdRepository;

    public HdResource(HdService hdService, HdRepository hdRepository) {
        this.hdService = hdService;
        this.hdRepository = hdRepository;
    }

    /**
     * {@code POST  /hds} : Create a new hd.
     *
     * @param hdDTO the hdDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hdDTO, or with status {@code 400 (Bad Request)} if the hd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hds")
    public ResponseEntity<HdDTO> createHd(@RequestBody HdDTO hdDTO) throws URISyntaxException {
        log.debug("REST request to save Hd : {}", hdDTO);
        if (hdDTO.getId() != null) {
            throw new BadRequestAlertException("A new hd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HdDTO result = hdService.save(hdDTO);
        return ResponseEntity
            .created(new URI("/api/hds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hds/:id} : Updates an existing hd.
     *
     * @param id the id of the hdDTO to save.
     * @param hdDTO the hdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hdDTO,
     * or with status {@code 400 (Bad Request)} if the hdDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hds/{id}")
    public ResponseEntity<HdDTO> updateHd(@PathVariable(value = "id", required = false) final Long id, @RequestBody HdDTO hdDTO)
        throws URISyntaxException {
        log.debug("REST request to update Hd : {}, {}", id, hdDTO);
        if (hdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hdDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HdDTO result = hdService.save(hdDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hdDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hds/:id} : Partial updates given fields of an existing hd, field will ignore if it is null
     *
     * @param id the id of the hdDTO to save.
     * @param hdDTO the hdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hdDTO,
     * or with status {@code 400 (Bad Request)} if the hdDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hdDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hds/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HdDTO> partialUpdateHd(@PathVariable(value = "id", required = false) final Long id, @RequestBody HdDTO hdDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Hd partially : {}, {}", id, hdDTO);
        if (hdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hdDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HdDTO> result = hdService.partialUpdate(hdDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hdDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hds} : get all the hds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hds in body.
     */
    @GetMapping("/hds")
    public List<HdDTO> getAllHds() {
        log.debug("REST request to get all Hds");
        return hdService.findAll();
    }

    /**
     * {@code GET  /hds/:id} : get the "id" hd.
     *
     * @param id the id of the hdDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hdDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hds/{id}")
    public ResponseEntity<HdDTO> getHd(@PathVariable Long id) {
        log.debug("REST request to get Hd : {}", id);
        Optional<HdDTO> hdDTO = hdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hdDTO);
    }

    /**
     * {@code DELETE  /hds/:id} : delete the "id" hd.
     *
     * @param id the id of the hdDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hds/{id}")
    public ResponseEntity<Void> deleteHd(@PathVariable Long id) {
        log.debug("REST request to delete Hd : {}", id);
        hdService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
