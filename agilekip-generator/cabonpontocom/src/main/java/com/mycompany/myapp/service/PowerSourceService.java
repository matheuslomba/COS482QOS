package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PowerSource;
import com.mycompany.myapp.repository.PowerSourceRepository;
import com.mycompany.myapp.service.dto.PowerSourceDTO;
import com.mycompany.myapp.service.mapper.PowerSourceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PowerSource}.
 */
@Service
@Transactional
public class PowerSourceService {

    private final Logger log = LoggerFactory.getLogger(PowerSourceService.class);

    private final PowerSourceRepository powerSourceRepository;

    private final PowerSourceMapper powerSourceMapper;

    public PowerSourceService(PowerSourceRepository powerSourceRepository, PowerSourceMapper powerSourceMapper) {
        this.powerSourceRepository = powerSourceRepository;
        this.powerSourceMapper = powerSourceMapper;
    }

    /**
     * Save a powerSource.
     *
     * @param powerSourceDTO the entity to save.
     * @return the persisted entity.
     */
    public PowerSourceDTO save(PowerSourceDTO powerSourceDTO) {
        log.debug("Request to save PowerSource : {}", powerSourceDTO);
        PowerSource powerSource = powerSourceMapper.toEntity(powerSourceDTO);
        powerSource = powerSourceRepository.save(powerSource);
        return powerSourceMapper.toDto(powerSource);
    }

    /**
     * Partially update a powerSource.
     *
     * @param powerSourceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PowerSourceDTO> partialUpdate(PowerSourceDTO powerSourceDTO) {
        log.debug("Request to partially update PowerSource : {}", powerSourceDTO);

        return powerSourceRepository
            .findById(powerSourceDTO.getId())
            .map(
                existingPowerSource -> {
                    powerSourceMapper.partialUpdate(existingPowerSource, powerSourceDTO);
                    return existingPowerSource;
                }
            )
            .map(powerSourceRepository::save)
            .map(powerSourceMapper::toDto);
    }

    /**
     * Get all the powerSources.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PowerSourceDTO> findAll() {
        log.debug("Request to get all PowerSources");
        return powerSourceRepository.findAll().stream().map(powerSourceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one powerSource by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PowerSourceDTO> findOne(Long id) {
        log.debug("Request to get PowerSource : {}", id);
        return powerSourceRepository.findById(id).map(powerSourceMapper::toDto);
    }

    /**
     * Delete the powerSource by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PowerSource : {}", id);
        powerSourceRepository.deleteById(id);
    }
}
