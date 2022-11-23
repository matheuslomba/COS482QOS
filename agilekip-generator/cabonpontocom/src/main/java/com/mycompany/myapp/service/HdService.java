package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Hd;
import com.mycompany.myapp.repository.HdRepository;
import com.mycompany.myapp.service.dto.HdDTO;
import com.mycompany.myapp.service.mapper.HdMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Hd}.
 */
@Service
@Transactional
public class HdService {

    private final Logger log = LoggerFactory.getLogger(HdService.class);

    private final HdRepository hdRepository;

    private final HdMapper hdMapper;

    public HdService(HdRepository hdRepository, HdMapper hdMapper) {
        this.hdRepository = hdRepository;
        this.hdMapper = hdMapper;
    }

    /**
     * Save a hd.
     *
     * @param hdDTO the entity to save.
     * @return the persisted entity.
     */
    public HdDTO save(HdDTO hdDTO) {
        log.debug("Request to save Hd : {}", hdDTO);
        Hd hd = hdMapper.toEntity(hdDTO);
        hd = hdRepository.save(hd);
        return hdMapper.toDto(hd);
    }

    /**
     * Partially update a hd.
     *
     * @param hdDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HdDTO> partialUpdate(HdDTO hdDTO) {
        log.debug("Request to partially update Hd : {}", hdDTO);

        return hdRepository
            .findById(hdDTO.getId())
            .map(
                existingHd -> {
                    hdMapper.partialUpdate(existingHd, hdDTO);
                    return existingHd;
                }
            )
            .map(hdRepository::save)
            .map(hdMapper::toDto);
    }

    /**
     * Get all the hds.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<HdDTO> findAll() {
        log.debug("Request to get all Hds");
        return hdRepository.findAll().stream().map(hdMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one hd by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HdDTO> findOne(Long id) {
        log.debug("Request to get Hd : {}", id);
        return hdRepository.findById(id).map(hdMapper::toDto);
    }

    /**
     * Delete the hd by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Hd : {}", id);
        hdRepository.deleteById(id);
    }
}
