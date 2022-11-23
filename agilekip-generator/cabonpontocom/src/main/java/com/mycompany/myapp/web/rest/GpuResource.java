package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.GpuRepository;
import com.mycompany.myapp.service.GpuService;
import com.mycompany.myapp.service.dto.GpuDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Gpu}.
 */
@RestController
@RequestMapping("/api")
public class GpuResource {

    private final Logger log = LoggerFactory.getLogger(GpuResource.class);

    private static final String ENTITY_NAME = "gpu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GpuService gpuService;

    private final GpuRepository gpuRepository;

    public GpuResource(GpuService gpuService, GpuRepository gpuRepository) {
        this.gpuService = gpuService;
        this.gpuRepository = gpuRepository;
    }

    /**
     * {@code POST  /gpus} : Create a new gpu.
     *
     * @param gpuDTO the gpuDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gpuDTO, or with status {@code 400 (Bad Request)} if the gpu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gpus")
    public ResponseEntity<GpuDTO> createGpu(@RequestBody GpuDTO gpuDTO) throws URISyntaxException {
        log.debug("REST request to save Gpu : {}", gpuDTO);
        if (gpuDTO.getId() != null) {
            throw new BadRequestAlertException("A new gpu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GpuDTO result = gpuService.save(gpuDTO);
        return ResponseEntity
            .created(new URI("/api/gpus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gpus/:id} : Updates an existing gpu.
     *
     * @param id the id of the gpuDTO to save.
     * @param gpuDTO the gpuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gpuDTO,
     * or with status {@code 400 (Bad Request)} if the gpuDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gpuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gpus/{id}")
    public ResponseEntity<GpuDTO> updateGpu(@PathVariable(value = "id", required = false) final Long id, @RequestBody GpuDTO gpuDTO)
        throws URISyntaxException {
        log.debug("REST request to update Gpu : {}, {}", id, gpuDTO);
        if (gpuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gpuDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gpuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GpuDTO result = gpuService.save(gpuDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gpuDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gpus/:id} : Partial updates given fields of an existing gpu, field will ignore if it is null
     *
     * @param id the id of the gpuDTO to save.
     * @param gpuDTO the gpuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gpuDTO,
     * or with status {@code 400 (Bad Request)} if the gpuDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gpuDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gpuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gpus/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GpuDTO> partialUpdateGpu(@PathVariable(value = "id", required = false) final Long id, @RequestBody GpuDTO gpuDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Gpu partially : {}, {}", id, gpuDTO);
        if (gpuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gpuDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gpuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GpuDTO> result = gpuService.partialUpdate(gpuDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gpuDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gpus} : get all the gpus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gpus in body.
     */
    @GetMapping("/gpus")
    public List<GpuDTO> getAllGpus() {
        log.debug("REST request to get all Gpus");
        return gpuService.findAll();
    }

    /**
     * {@code GET  /gpus/:id} : get the "id" gpu.
     *
     * @param id the id of the gpuDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gpuDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gpus/{id}")
    public ResponseEntity<GpuDTO> getGpu(@PathVariable Long id) {
        log.debug("REST request to get Gpu : {}", id);
        Optional<GpuDTO> gpuDTO = gpuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gpuDTO);
    }

    /**
     * {@code DELETE  /gpus/:id} : delete the "id" gpu.
     *
     * @param id the id of the gpuDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gpus/{id}")
    public ResponseEntity<Void> deleteGpu(@PathVariable Long id) {
        log.debug("REST request to delete Gpu : {}", id);
        gpuService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
