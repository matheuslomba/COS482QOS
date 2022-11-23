package com.mycompany.myapp.process.clientOrderProcess;

import com.mycompany.myapp.repository.ClientOrderProcessRepository;
import com.mycompany.myapp.service.ClientOrderService;
import com.mycompany.myapp.service.dto.ClientOrderDTO;
import com.mycompany.myapp.service.dto.ClientOrderProcessDTO;
import com.mycompany.myapp.service.mapper.ClientOrderProcessMapper;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskChooseComponentsService {

    private final TaskInstanceService taskInstanceService;

    private final ClientOrderService clientOrderService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final ClientOrderProcessRepository clientOrderProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskChooseComponentsMapper taskChooseComponentsMapper;

    private final ClientOrderProcessMapper clientOrderProcessMapper;

    public TaskChooseComponentsService(
        TaskInstanceService taskInstanceService,
        ClientOrderService clientOrderService,
        TaskInstanceRepository taskInstanceRepository,
        ClientOrderProcessRepository clientOrderProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskChooseComponentsMapper taskChooseComponentsMapper,
        ClientOrderProcessMapper clientOrderProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.clientOrderService = clientOrderService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.clientOrderProcessRepository = clientOrderProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskChooseComponentsMapper = taskChooseComponentsMapper;
        this.clientOrderProcessMapper = clientOrderProcessMapper;
    }

    public TaskChooseComponentsContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        ClientOrderProcessDTO clientOrderProcess = clientOrderProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskChooseComponentsMapper::toClientOrderProcessDTO)
            .orElseThrow();

        TaskChooseComponentsContextDTO taskChooseComponentsContext = new TaskChooseComponentsContextDTO();
        taskChooseComponentsContext.setTaskInstance(taskInstanceDTO);
        taskChooseComponentsContext.setClientOrderProcess(clientOrderProcess);

        return taskChooseComponentsContext;
    }

    public TaskChooseComponentsContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskChooseComponentsContextDTO taskChooseComponentsContext) {
        ClientOrderDTO clientOrderDTO = clientOrderService
            .findOne(taskChooseComponentsContext.getClientOrderProcess().getClientOrder().getId())
            .orElseThrow();
        clientOrderDTO.setOrderID(taskChooseComponentsContext.getClientOrderProcess().getClientOrder().getOrderID());
        clientOrderDTO.setOrderDate(taskChooseComponentsContext.getClientOrderProcess().getClientOrder().getOrderDate());
        clientOrderDTO.setClientName(taskChooseComponentsContext.getClientOrderProcess().getClientOrder().getClientName());
        clientOrderDTO.setNumComponents(taskChooseComponentsContext.getClientOrderProcess().getClientOrder().getNumComponents());
        clientOrderService.save(clientOrderDTO);
    }

    public void complete(TaskChooseComponentsContextDTO taskChooseComponentsContext) {
        save(taskChooseComponentsContext);
        ClientOrderProcessDTO clientOrderProcess = clientOrderProcessRepository
            .findByProcessInstanceId(taskChooseComponentsContext.getClientOrderProcess().getProcessInstance().getId())
            .map(clientOrderProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskChooseComponentsContext.getTaskInstance(), clientOrderProcess);
    }
}
