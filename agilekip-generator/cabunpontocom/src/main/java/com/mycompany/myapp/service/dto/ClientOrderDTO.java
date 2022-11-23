package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ClientOrder} entity.
 */
public class ClientOrderDTO implements Serializable {

    private Long id;

    private String orderID;

    private LocalDate orderDate;

    private String clientName;

    private String orderComponents;

    private String orderPrice;

    private Integer numItems;

    private Boolean assemblyPC;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOrderComponents() {
        return orderComponents;
    }

    public void setOrderComponents(String orderComponents) {
        this.orderComponents = orderComponents;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getNumItems() {
        return numItems;
    }

    public void setNumItems(Integer numItems) {
        this.numItems = numItems;
    }

    public Boolean getAssemblyPC() {
        return assemblyPC;
    }

    public void setAssemblyPC(Boolean assemblyPC) {
        this.assemblyPC = assemblyPC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientOrderDTO)) {
            return false;
        }

        ClientOrderDTO clientOrderDTO = (ClientOrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clientOrderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientOrderDTO{" +
            "id=" + getId() +
            ", orderID='" + getOrderID() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", clientName='" + getClientName() + "'" +
            ", orderComponents='" + getOrderComponents() + "'" +
            ", orderPrice='" + getOrderPrice() + "'" +
            ", numItems=" + getNumItems() +
            ", assemblyPC='" + getAssemblyPC() + "'" +
            "}";
    }
}
