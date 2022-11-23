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
public class TaskOfferAssemblyService {

    private final TaskInstanceService taskInstanceService;

    private final ClientOrderService clientOrderService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final ClientOrderProcessRepository clientOrderProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskOfferAssemblyMapper taskOfferAssemblyMapper;

    private final ClientOrderProcessMapper clientOrderProcessMapper;

    public TaskOfferAssemblyService(
        TaskInstanceService taskInstanceService,
        ClientOrderService clientOrderService,
        TaskInstanceRepository taskInstanceRepository,
        ClientOrderProcessRepository clientOrderProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskOfferAssemblyMapper taskOfferAssemblyMapper,
        ClientOrderProcessMapper clientOrderProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.clientOrderService = clientOrderService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.clientOrderProcessRepository = clientOrderProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskOfferAssemblyMapper = taskOfferAssemblyMapper;
        this.clientOrderProcessMapper = clientOrderProcessMapper;
    }

    public TaskOfferAssemblyContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        ClientOrderProcessDTO clientOrderProcess = clientOrderProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskOfferAssemblyMapper::toClientOrderProcessDTO)
            .orElseThrow();

        TaskOfferAssemblyContextDTO taskOfferAssemblyContext = new TaskOfferAssemblyContextDTO();
        taskOfferAssemblyContext.setTaskInstance(taskInstanceDTO);
        taskOfferAssemblyContext.setClientOrderProcess(clientOrderProcess);

        return taskOfferAssemblyContext;
    }

    public TaskOfferAssemblyContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskOfferAssemblyContextDTO taskOfferAssemblyContext) {
        ClientOrderDTO clientOrderDTO = clientOrderService
            .findOne(taskOfferAssemblyContext.getClientOrderProcess().getClientOrder().getId())
            .orElseThrow();
        clientOrderDTO.setOrderID(taskOfferAssemblyContext.getClientOrderProcess().getClientOrder().getOrderID());
        clientOrderDTO.setOrderDate(taskOfferAssemblyContext.getClientOrderProcess().getClientOrder().getOrderDate());
        clientOrderDTO.setClientName(taskOfferAssemblyContext.getClientOrderProcess().getClientOrder().getClientName());
        clientOrderDTO.setNumComponents(taskOfferAssemblyContext.getClientOrderProcess().getClientOrder().getNumComponents());
        clientOrderDTO.setAssemblyPC(taskOfferAssemblyContext.getClientOrderProcess().getClientOrder().getAssemblyPC());
        clientOrderService.save(clientOrderDTO);
    }

    public void complete(TaskOfferAssemblyContextDTO taskOfferAssemblyContext) {
        save(taskOfferAssemblyContext);
        ClientOrderProcessDTO clientOrderProcess = clientOrderProcessRepository
            .findByProcessInstanceId(taskOfferAssemblyContext.getClientOrderProcess().getProcessInstance().getId())
            .map(clientOrderProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskOfferAssemblyContext.getTaskInstance(), clientOrderProcess);
    }
}
