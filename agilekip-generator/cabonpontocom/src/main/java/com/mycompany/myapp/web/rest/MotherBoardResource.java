package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.MotherBoardRepository;
import com.mycompany.myapp.service.MotherBoardService;
import com.mycompany.myapp.service.dto.MotherBoardDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.MotherBoard}.
 */
@RestController
@RequestMapping("/api")
public class MotherBoardResource {

    private final Logger log = LoggerFactory.getLogger(MotherBoardResource.class);

    private static final String ENTITY_NAME = "motherBoard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MotherBoardService motherBoardService;

    private final MotherBoardRepository motherBoardRepository;

    public MotherBoardResource(MotherBoardService motherBoardService, MotherBoardRepository motherBoardRepository) {
        this.motherBoardService = motherBoardService;
        this.motherBoardRepository = motherBoardRepository;
    }

    /**
     * {@code POST  /mother-boards} : Create a new motherBoard.
     *
     * @param motherBoardDTO the motherBoardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new motherBoardDTO, or with status {@code 400 (Bad Request)} if the motherBoard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mother-boards")
    public ResponseEntity<MotherBoardDTO> createMotherBoard(@RequestBody MotherBoardDTO motherBoardDTO) throws URISyntaxException {
        log.debug("REST request to save MotherBoard : {}", motherBoardDTO);
        if (motherBoardDTO.getId() != null) {
            throw new BadRequestAlertException("A new motherBoard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MotherBoardDTO result = motherBoardService.save(motherBoardDTO);
        return ResponseEntity
            .created(new URI("/api/mother-boards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mother-boards/:id} : Updates an existing motherBoard.
     *
     * @param id the id of the motherBoardDTO to save.
     * @param motherBoardDTO the motherBoardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motherBoardDTO,
     * or with status {@code 400 (Bad Request)} if the motherBoardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the motherBoardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mother-boards/{id}")
    public ResponseEntity<MotherBoardDTO> updateMotherBoard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MotherBoardDTO motherBoardDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MotherBoard : {}, {}", id, motherBoardDTO);
        if (motherBoardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, motherBoardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!motherBoardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MotherBoardDTO result = motherBoardService.save(motherBoardDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, motherBoardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mother-boards/:id} : Partial updates given fields of an existing motherBoard, field will ignore if it is null
     *
     * @param id the id of the motherBoardDTO to save.
     * @param motherBoardDTO the motherBoardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motherBoardDTO,
     * or with status {@code 400 (Bad Request)} if the motherBoardDTO is not valid,
     * or with status {@code 404 (Not Found)} if the motherBoardDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the motherBoardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mother-boards/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MotherBoardDTO> partialUpdateMotherBoard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MotherBoardDTO motherBoardDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MotherBoard partially : {}, {}", id, motherBoardDTO);
        if (motherBoardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, motherBoardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!motherBoardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MotherBoardDTO> result = motherBoardService.partialUpdate(motherBoardDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, motherBoardDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mother-boards} : get all the motherBoards.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of motherBoards in body.
     */
    @GetMapping("/mother-boards")
    public List<MotherBoardDTO> getAllMotherBoards() {
        log.debug("REST request to get all MotherBoards");
        return motherBoardService.findAll();
    }

    /**
     * {@code GET  /mother-boards/:id} : get the "id" motherBoard.
     *
     * @param id the id of the motherBoardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the motherBoardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mother-boards/{id}")
    public ResponseEntity<MotherBoardDTO> getMotherBoard(@PathVariable Long id) {
        log.debug("REST request to get MotherBoard : {}", id);
        Optional<MotherBoardDTO> motherBoardDTO = motherBoardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(motherBoardDTO);
    }

    /**
     * {@code DELETE  /mother-boards/:id} : delete the "id" motherBoard.
     *
     * @param id the id of the motherBoardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mother-boards/{id}")
    public ResponseEntity<Void> deleteMotherBoard(@PathVariable Long id) {
        log.debug("REST request to delete MotherBoard : {}", id);
        motherBoardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
