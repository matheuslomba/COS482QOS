package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Ram;
import com.mycompany.myapp.repository.RamRepository;
import com.mycompany.myapp.service.dto.RamDTO;
import com.mycompany.myapp.service.mapper.RamMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ram}.
 */
@Service
@Transactional
public class RamService {

    private final Logger log = LoggerFactory.getLogger(RamService.class);

    private final RamRepository ramRepository;

    private final RamMapper ramMapper;

    public RamService(RamRepository ramRepository, RamMapper ramMapper) {
        this.ramRepository = ramRepository;
        this.ramMapper = ramMapper;
    }

    /**
     * Save a ram.
     *
     * @param ramDTO the entity to save.
     * @return the persisted entity.
     */
    public RamDTO save(RamDTO ramDTO) {
        log.debug("Request to save Ram : {}", ramDTO);
        Ram ram = ramMapper.toEntity(ramDTO);
        ram = ramRepository.save(ram);
        return ramMapper.toDto(ram);
    }

    /**
     * Partially update a ram.
     *
     * @param ramDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RamDTO> partialUpdate(RamDTO ramDTO) {
        log.debug("Request to partially update Ram : {}", ramDTO);

        return ramRepository
            .findById(ramDTO.getId())
            .map(
                existingRam -> {
                    ramMapper.partialUpdate(existingRam, ramDTO);
                    return existingRam;
                }
            )
            .map(ramRepository::save)
            .map(ramMapper::toDto);
    }

    /**
     * Get all the rams.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RamDTO> findAll() {
        log.debug("Request to get all Rams");
        return ramRepository.findAll().stream().map(ramMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ram by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RamDTO> findOne(Long id) {
        log.debug("Request to get Ram : {}", id);
        return ramRepository.findById(id).map(ramMapper::toDto);
    }

    /**
     * Delete the ram by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ram : {}", id);
        ramRepository.deleteById(id);
    }
}
