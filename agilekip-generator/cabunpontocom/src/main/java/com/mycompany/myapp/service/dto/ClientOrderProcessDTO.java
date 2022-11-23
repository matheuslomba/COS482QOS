package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.akip.service.dto.ProcessInstanceDTO;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ClientOrderProcess} entity.
 */
public class ClientOrderProcessDTO implements Serializable {

    private Long id;

    private ProcessInstanceDTO processInstance;

    private ClientOrderDTO clientOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcessInstanceDTO getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstanceDTO processInstance) {
        this.processInstance = processInstance;
    }

    public ClientOrderDTO getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(ClientOrderDTO clientOrder) {
        this.clientOrder = clientOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientOrderProcessDTO)) {
            return false;
        }

        ClientOrderProcessDTO clientOrderProcessDTO = (ClientOrderProcessDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clientOrderProcessDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientOrderProcessDTO{" +
            "id=" + getId() +
            ", processInstance=" + getProcessInstance() +
            ", clientOrder=" + getClientOrder() +
            "}";
    }
}
