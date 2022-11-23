package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ClientOrderProcess;
import com.mycompany.myapp.repository.ClientOrderProcessRepository;
import com.mycompany.myapp.repository.ClientOrderRepository;
import com.mycompany.myapp.service.dto.ClientOrderProcessDTO;
import com.mycompany.myapp.service.mapper.ClientOrderProcessMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.akip.domain.ProcessInstance;
import org.akip.service.ProcessInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClientOrderProcess}.
 */
@Service
@Transactional
public class ClientOrderProcessService {

    public static final String BPMN_PROCESS_DEFINITION_ID = "ClientOrderProcess";

    private final Logger log = LoggerFactory.getLogger(ClientOrderProcessService.class);

    private final ProcessInstanceService processInstanceService;

    private final ClientOrderRepository clientOrderRepository;

    private final ClientOrderProcessRepository clientOrderProcessRepository;

    private final ClientOrderProcessMapper clientOrderProcessMapper;

    public ClientOrderProcessService(
        ProcessInstanceService processInstanceService,
        ClientOrderRepository clientOrderRepository,
        ClientOrderProcessRepository clientOrderProcessRepository,
        ClientOrderProcessMapper clientOrderProcessMapper
    ) {
        this.processInstanceService = processInstanceService;
        this.clientOrderRepository = clientOrderRepository;
        this.clientOrderProcessRepository = clientOrderProcessRepository;
        this.clientOrderProcessMapper = clientOrderProcessMapper;
    }

    /**
     * Save a clientOrderProcess.
     *
     * @param clientOrderProcessDTO the entity to save.
     * @return the persisted entity.
     */
    public ClientOrderProcessDTO create(ClientOrderProcessDTO clientOrderProcessDTO) {
        log.debug("Request to save ClientOrderProcess : {}", clientOrderProcessDTO);

        ClientOrderProcess clientOrderProcess = clientOrderProcessMapper.toEntity(clientOrderProcessDTO);

        //Saving the domainEntity
        clientOrderRepository.save(clientOrderProcess.getClientOrder());

        //Creating the process instance in the Camunda and setting it in the process entity
        ProcessInstance processInstance = processInstanceService.create(
            BPMN_PROCESS_DEFINITION_ID,
            "ClientOrder#" + clientOrderProcess.getClientOrder().getId(),
            clientOrderProcess
        );
        clientOrderProcess.setProcessInstance(processInstance);

        //Saving the process entity
        clientOrderProcess = clientOrderProcessRepository.save(clientOrderProcess);
        return clientOrderProcessMapper.toDto(clientOrderProcess);
    }

    /**
     * Get all the clientOrderProcesss.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClientOrderProcessDTO> findAll() {
        log.debug("Request to get all ClientOrderProcesss");
        return clientOrderProcessRepository
            .findAll()
            .stream()
            .map(clientOrderProcessMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one clientOrderProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientOrderProcessDTO> findOne(Long id) {
        log.debug("Request to get ClientOrderProcess : {}", id);
        return clientOrderProcessRepository.findById(id).map(clientOrderProcessMapper::toDto);
    }

    /**
     * Get one clientOrderProcess by id.
     *
     * @param processInstanceId the id of the processInstance associated to the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientOrderProcessDTO> findByProcessInstanceId(Long processInstanceId) {
        log.debug("Request to get ClientOrderProcess by  processInstanceId: {}", processInstanceId);
        return clientOrderProcessRepository.findByProcessInstanceId(processInstanceId).map(clientOrderProcessMapper::toDto);
    }
}
