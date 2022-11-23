package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ClientOrder;
import com.mycompany.myapp.repository.ClientOrderRepository;
import com.mycompany.myapp.service.dto.ClientOrderDTO;
import com.mycompany.myapp.service.mapper.ClientOrderMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClientOrder}.
 */
@Service
@Transactional
public class ClientOrderService {

    private final Logger log = LoggerFactory.getLogger(ClientOrderService.class);

    private final ClientOrderRepository clientOrderRepository;

    private final ClientOrderMapper clientOrderMapper;

    public ClientOrderService(ClientOrderRepository clientOrderRepository, ClientOrderMapper clientOrderMapper) {
        this.clientOrderRepository = clientOrderRepository;
        this.clientOrderMapper = clientOrderMapper;
    }

    /**
     * Save a clientOrder.
     *
     * @param clientOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public ClientOrderDTO save(ClientOrderDTO clientOrderDTO) {
        log.debug("Request to save ClientOrder : {}", clientOrderDTO);
        ClientOrder clientOrder = clientOrderMapper.toEntity(clientOrderDTO);
        clientOrder = clientOrderRepository.save(clientOrder);
        return clientOrderMapper.toDto(clientOrder);
    }

    /**
     * Partially update a clientOrder.
     *
     * @param clientOrderDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClientOrderDTO> partialUpdate(ClientOrderDTO clientOrderDTO) {
        log.debug("Request to partially update ClientOrder : {}", clientOrderDTO);

        return clientOrderRepository
            .findById(clientOrderDTO.getId())
            .map(
                existingClientOrder -> {
                    clientOrderMapper.partialUpdate(existingClientOrder, clientOrderDTO);
                    return existingClientOrder;
                }
            )
            .map(clientOrderRepository::save)
            .map(clientOrderMapper::toDto);
    }

    /**
     * Get all the clientOrders.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClientOrderDTO> findAll() {
        log.debug("Request to get all ClientOrders");
        return clientOrderRepository.findAll().stream().map(clientOrderMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one clientOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientOrderDTO> findOne(Long id) {
        log.debug("Request to get ClientOrder : {}", id);
        return clientOrderRepository.findById(id).map(clientOrderMapper::toDto);
    }

    /**
     * Delete the clientOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientOrder : {}", id);
        clientOrderRepository.deleteById(id);
    }
}
