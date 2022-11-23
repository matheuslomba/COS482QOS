package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.MotherBoard;
import com.mycompany.myapp.repository.MotherBoardRepository;
import com.mycompany.myapp.service.dto.MotherBoardDTO;
import com.mycompany.myapp.service.mapper.MotherBoardMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MotherBoard}.
 */
@Service
@Transactional
public class MotherBoardService {

    private final Logger log = LoggerFactory.getLogger(MotherBoardService.class);

    private final MotherBoardRepository motherBoardRepository;

    private final MotherBoardMapper motherBoardMapper;

    public MotherBoardService(MotherBoardRepository motherBoardRepository, MotherBoardMapper motherBoardMapper) {
        this.motherBoardRepository = motherBoardRepository;
        this.motherBoardMapper = motherBoardMapper;
    }

    /**
     * Save a motherBoard.
     *
     * @param motherBoardDTO the entity to save.
     * @return the persisted entity.
     */
    public MotherBoardDTO save(MotherBoardDTO motherBoardDTO) {
        log.debug("Request to save MotherBoard : {}", motherBoardDTO);
        MotherBoard motherBoard = motherBoardMapper.toEntity(motherBoardDTO);
        motherBoard = motherBoardRepository.save(motherBoard);
        return motherBoardMapper.toDto(motherBoard);
    }

    /**
     * Partially update a motherBoard.
     *
     * @param motherBoardDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MotherBoardDTO> partialUpdate(MotherBoardDTO motherBoardDTO) {
        log.debug("Request to partially update MotherBoard : {}", motherBoardDTO);

        return motherBoardRepository
            .findById(motherBoardDTO.getId())
            .map(
                existingMotherBoard -> {
                    motherBoardMapper.partialUpdate(existingMotherBoard, motherBoardDTO);
                    return existingMotherBoard;
                }
            )
            .map(motherBoardRepository::save)
            .map(motherBoardMapper::toDto);
    }

    /**
     * Get all the motherBoards.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MotherBoardDTO> findAll() {
        log.debug("Request to get all MotherBoards");
        return motherBoardRepository.findAll().stream().map(motherBoardMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one motherBoard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MotherBoardDTO> findOne(Long id) {
        log.debug("Request to get MotherBoard : {}", id);
        return motherBoardRepository.findById(id).map(motherBoardMapper::toDto);
    }

    /**
     * Delete the motherBoard by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MotherBoard : {}", id);
        motherBoardRepository.deleteById(id);
    }
}
