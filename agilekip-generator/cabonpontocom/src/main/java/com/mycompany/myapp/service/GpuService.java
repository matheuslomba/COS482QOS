package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Gpu;
import com.mycompany.myapp.repository.GpuRepository;
import com.mycompany.myapp.service.dto.GpuDTO;
import com.mycompany.myapp.service.mapper.GpuMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Gpu}.
 */
@Service
@Transactional
public class GpuService {

    private final Logger log = LoggerFactory.getLogger(GpuService.class);

    private final GpuRepository gpuRepository;

    private final GpuMapper gpuMapper;

    public GpuService(GpuRepository gpuRepository, GpuMapper gpuMapper) {
        this.gpuRepository = gpuRepository;
        this.gpuMapper = gpuMapper;
    }

    /**
     * Save a gpu.
     *
     * @param gpuDTO the entity to save.
     * @return the persisted entity.
     */
    public GpuDTO save(GpuDTO gpuDTO) {
        log.debug("Request to save Gpu : {}", gpuDTO);
        Gpu gpu = gpuMapper.toEntity(gpuDTO);
        gpu = gpuRepository.save(gpu);
        return gpuMapper.toDto(gpu);
    }

    /**
     * Partially update a gpu.
     *
     * @param gpuDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GpuDTO> partialUpdate(GpuDTO gpuDTO) {
        log.debug("Request to partially update Gpu : {}", gpuDTO);

        return gpuRepository
            .findById(gpuDTO.getId())
            .map(
                existingGpu -> {
                    gpuMapper.partialUpdate(existingGpu, gpuDTO);
                    return existingGpu;
                }
            )
            .map(gpuRepository::save)
            .map(gpuMapper::toDto);
    }

    /**
     * Get all the gpus.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GpuDTO> findAll() {
        log.debug("Request to get all Gpus");
        return gpuRepository.findAll().stream().map(gpuMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one gpu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GpuDTO> findOne(Long id) {
        log.debug("Request to get Gpu : {}", id);
        return gpuRepository.findById(id).map(gpuMapper::toDto);
    }

    /**
     * Delete the gpu by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Gpu : {}", id);
        gpuRepository.deleteById(id);
    }
}
