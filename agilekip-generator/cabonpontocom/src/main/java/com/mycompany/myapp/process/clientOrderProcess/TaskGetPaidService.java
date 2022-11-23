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
public class TaskGetPaidService {

    private final TaskInstanceService taskInstanceService;

    private final ClientOrderService clientOrderService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final ClientOrderProcessRepository clientOrderProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskGetPaidMapper taskGetPaidMapper;

    private final ClientOrderProcessMapper clientOrderProcessMapper;

    public TaskGetPaidService(
        TaskInstanceService taskInstanceService,
        ClientOrderService clientOrderService,
        TaskInstanceRepository taskInstanceRepository,
        ClientOrderProcessRepository clientOrderProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskGetPaidMapper taskGetPaidMapper,
        ClientOrderProcessMapper clientOrderProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.clientOrderService = clientOrderService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.clientOrderProcessRepository = clientOrderProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskGetPaidMapper = taskGetPaidMapper;
        this.clientOrderProcessMapper = clientOrderProcessMapper;
    }

    public TaskGetPaidContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        ClientOrderProcessDTO clientOrderProcess = clientOrderProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskGetPaidMapper::toClientOrderProcessDTO)
            .orElseThrow();

        TaskGetPaidContextDTO taskGetPaidContext = new TaskGetPaidContextDTO();
        taskGetPaidContext.setTaskInstance(taskInstanceDTO);
        taskGetPaidContext.setClientOrderProcess(clientOrderProcess);

        return taskGetPaidContext;
    }

    public TaskGetPaidContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskGetPaidContextDTO taskGetPaidContext) {
        ClientOrderDTO clientOrderDTO = clientOrderService
            .findOne(taskGetPaidContext.getClientOrderProcess().getClientOrder().getId())
            .orElseThrow();
        clientOrderDTO.setOrderID(taskGetPaidContext.getClientOrderProcess().getClientOrder().getOrderID());
        clientOrderDTO.setOrderDate(taskGetPaidContext.getClientOrderProcess().getClientOrder().getOrderDate());
        clientOrderDTO.setClientName(taskGetPaidContext.getClientOrderProcess().getClientOrder().getClientName());
        clientOrderDTO.setDeliveryAdd(taskGetPaidContext.getClientOrderProcess().getClientOrder().getDeliveryAdd());
        clientOrderDTO.setOrderPrice(taskGetPaidContext.getClientOrderProcess().getClientOrder().getOrderPrice());
        clientOrderDTO.setPayment(taskGetPaidContext.getClientOrderProcess().getClientOrder().getPayment());
        clientOrderService.save(clientOrderDTO);
    }

    public void complete(TaskGetPaidContextDTO taskGetPaidContext) {
        save(taskGetPaidContext);
        ClientOrderProcessDTO clientOrderProcess = clientOrderProcessRepository
            .findByProcessInstanceId(taskGetPaidContext.getClientOrderProcess().getProcessInstance().getId())
            .map(clientOrderProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskGetPaidContext.getTaskInstance(), clientOrderProcess);
    }
}
