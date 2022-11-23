package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CpuRepository;
import com.mycompany.myapp.service.CpuService;
import com.mycompany.myapp.service.dto.CpuDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Cpu}.
 */
@RestController
@RequestMapping("/api")
public class CpuResource {

    private final Logger log = LoggerFactory.getLogger(CpuResource.class);

    private static final String ENTITY_NAME = "cpu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CpuService cpuService;

    private final CpuRepository cpuRepository;

    public CpuResource(CpuService cpuService, CpuRepository cpuRepository) {
        this.cpuService = cpuService;
        this.cpuRepository = cpuRepository;
    }

    /**
     * {@code POST  /cpus} : Create a new cpu.
     *
     * @param cpuDTO the cpuDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cpuDTO, or with status {@code 400 (Bad Request)} if the cpu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cpus")
    public ResponseEntity<CpuDTO> createCpu(@RequestBody CpuDTO cpuDTO) throws URISyntaxException {
        log.debug("REST request to save Cpu : {}", cpuDTO);
        if (cpuDTO.getId() != null) {
            throw new BadRequestAlertException("A new cpu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CpuDTO result = cpuService.save(cpuDTO);
        return ResponseEntity
            .created(new URI("/api/cpus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cpus/:id} : Updates an existing cpu.
     *
     * @param id the id of the cpuDTO to save.
     * @param cpuDTO the cpuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cpuDTO,
     * or with status {@code 400 (Bad Request)} if the cpuDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cpuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cpus/{id}")
    public ResponseEntity<CpuDTO> updateCpu(@PathVariable(value = "id", required = false) final Long id, @RequestBody CpuDTO cpuDTO)
        throws URISyntaxException {
        log.debug("REST request to update Cpu : {}, {}", id, cpuDTO);
        if (cpuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cpuDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cpuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CpuDTO result = cpuService.save(cpuDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cpuDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cpus/:id} : Partial updates given fields of an existing cpu, field will ignore if it is null
     *
     * @param id the id of the cpuDTO to save.
     * @param cpuDTO the cpuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cpuDTO,
     * or with status {@code 400 (Bad Request)} if the cpuDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cpuDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cpuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cpus/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CpuDTO> partialUpdateCpu(@PathVariable(value = "id", required = false) final Long id, @RequestBody CpuDTO cpuDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Cpu partially : {}, {}", id, cpuDTO);
        if (cpuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cpuDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cpuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CpuDTO> result = cpuService.partialUpdate(cpuDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cpuDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cpus} : get all the cpus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cpus in body.
     */
    @GetMapping("/cpus")
    public List<CpuDTO> getAllCpus() {
        log.debug("REST request to get all Cpus");
        return cpuService.findAll();
    }

    /**
     * {@code GET  /cpus/:id} : get the "id" cpu.
     *
     * @param id the id of the cpuDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cpuDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cpus/{id}")
    public ResponseEntity<CpuDTO> getCpu(@PathVariable Long id) {
        log.debug("REST request to get Cpu : {}", id);
        Optional<CpuDTO> cpuDTO = cpuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cpuDTO);
    }

    /**
     * {@code DELETE  /cpus/:id} : delete the "id" cpu.
     *
     * @param id the id of the cpuDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cpus/{id}")
    public ResponseEntity<Void> deleteCpu(@PathVariable Long id) {
        log.debug("REST request to delete Cpu : {}", id);
        cpuService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
