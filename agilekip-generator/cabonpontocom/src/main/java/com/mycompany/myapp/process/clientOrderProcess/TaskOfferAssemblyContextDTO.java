package com.mycompany.myapp.process.clientOrderProcess;

import com.mycompany.myapp.service.dto.ClientOrderProcessDTO;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskOfferAssemblyContextDTO {

    private ClientOrderProcessDTO clientOrderProcess;
    private TaskInstanceDTO taskInstance;

    public ClientOrderProcessDTO getClientOrderProcess() {
        return clientOrderProcess;
    }

    public void setClientOrderProcess(ClientOrderProcessDTO clientOrderProcess) {
        this.clientOrderProcess = clientOrderProcess;
    }

    public TaskInstanceDTO getTaskInstance() {
        return taskInstance;
    }

    public void setTaskInstance(TaskInstanceDTO taskInstance) {
        this.taskInstance = taskInstance;
    }
}
