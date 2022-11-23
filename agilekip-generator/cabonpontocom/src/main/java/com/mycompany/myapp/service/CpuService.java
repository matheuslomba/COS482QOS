package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Cpu;
import com.mycompany.myapp.repository.CpuRepository;
import com.mycompany.myapp.service.dto.CpuDTO;
import com.mycompany.myapp.service.mapper.CpuMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cpu}.
 */
@Service
@Transactional
public class CpuService {

    private final Logger log = LoggerFactory.getLogger(CpuService.class);

    private final CpuRepository cpuRepository;

    private final CpuMapper cpuMapper;

    public CpuService(CpuRepository cpuRepository, CpuMapper cpuMapper) {
        this.cpuRepository = cpuRepository;
        this.cpuMapper = cpuMapper;
    }

    /**
     * Save a cpu.
     *
     * @param cpuDTO the entity to save.
     * @return the persisted entity.
     */
    public CpuDTO save(CpuDTO cpuDTO) {
        log.debug("Request to save Cpu : {}", cpuDTO);
        Cpu cpu = cpuMapper.toEntity(cpuDTO);
        cpu = cpuRepository.save(cpu);
        return cpuMapper.toDto(cpu);
    }

    /**
     * Partially update a cpu.
     *
     * @param cpuDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CpuDTO> partialUpdate(CpuDTO cpuDTO) {
        log.debug("Request to partially update Cpu : {}", cpuDTO);

        return cpuRepository
            .findById(cpuDTO.getId())
            .map(
                existingCpu -> {
                    cpuMapper.partialUpdate(existingCpu, cpuDTO);
                    return existingCpu;
                }
            )
            .map(cpuRepository::save)
            .map(cpuMapper::toDto);
    }

    /**
     * Get all the cpus.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CpuDTO> findAll() {
        log.debug("Request to get all Cpus");
        return cpuRepository.findAll().stream().map(cpuMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one cpu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CpuDTO> findOne(Long id) {
        log.debug("Request to get Cpu : {}", id);
        return cpuRepository.findById(id).map(cpuMapper::toDto);
    }

    /**
     * Delete the cpu by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cpu : {}", id);
        cpuRepository.deleteById(id);
    }
}
