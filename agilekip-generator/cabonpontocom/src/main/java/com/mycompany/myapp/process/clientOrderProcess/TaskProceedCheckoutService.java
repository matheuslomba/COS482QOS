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
public class TaskProceedCheckoutService {

    private final TaskInstanceService taskInstanceService;

    private final ClientOrderService clientOrderService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final ClientOrderProcessRepository clientOrderProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskProceedCheckoutMapper taskProceedCheckoutMapper;

    private final ClientOrderProcessMapper clientOrderProcessMapper;

    public TaskProceedCheckoutService(
        TaskInstanceService taskInstanceService,
        ClientOrderService clientOrderService,
        TaskInstanceRepository taskInstanceRepository,
        ClientOrderProcessRepository clientOrderProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskProceedCheckoutMapper taskProceedCheckoutMapper,
        ClientOrderProcessMapper clientOrderProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.clientOrderService = clientOrderService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.clientOrderProcessRepository = clientOrderProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskProceedCheckoutMapper = taskProceedCheckoutMapper;
        this.clientOrderProcessMapper = clientOrderProcessMapper;
    }

    public TaskProceedCheckoutContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        ClientOrderProcessDTO clientOrderProcess = clientOrderProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskProceedCheckoutMapper::toClientOrderProcessDTO)
            .orElseThrow();

        TaskProceedCheckoutContextDTO taskProceedCheckoutContext = new TaskProceedCheckoutContextDTO();
        taskProceedCheckoutContext.setTaskInstance(taskInstanceDTO);
        taskProceedCheckoutContext.setClientOrderProcess(clientOrderProcess);

        return taskProceedCheckoutContext;
    }

    public TaskProceedCheckoutContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskProceedCheckoutContextDTO taskProceedCheckoutContext) {
        ClientOrderDTO clientOrderDTO = clientOrderService
            .findOne(taskProceedCheckoutContext.getClientOrderProcess().getClientOrder().getId())
            .orElseThrow();
        clientOrderDTO.setOrderID(taskProceedCheckoutContext.getClientOrderProcess().getClientOrder().getOrderID());
        clientOrderDTO.setOrderDate(taskProceedCheckoutContext.getClientOrderProcess().getClientOrder().getOrderDate());
        clientOrderDTO.setClientName(taskProceedCheckoutContext.getClientOrderProcess().getClientOrder().getClientName());
        clientOrderDTO.setNumComponents(taskProceedCheckoutContext.getClientOrderProcess().getClientOrder().getNumComponents());
        clientOrderDTO.setDeliveryAdd(taskProceedCheckoutContext.getClientOrderProcess().getClientOrder().getDeliveryAdd());
        clientOrderDTO.setProceedToCheckout(taskProceedCheckoutContext.getClientOrderProcess().getClientOrder().getProceedToCheckout());
        clientOrderService.save(clientOrderDTO);
    }

    public void complete(TaskProceedCheckoutContextDTO taskProceedCheckoutContext) {
        save(taskProceedCheckoutContext);
        ClientOrderProcessDTO clientOrderProcess = clientOrderProcessRepository
            .findByProcessInstanceId(taskProceedCheckoutContext.getClientOrderProcess().getProcessInstance().getId())
            .map(clientOrderProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskProceedCheckoutContext.getTaskInstance(), clientOrderProcess);
    }
}
