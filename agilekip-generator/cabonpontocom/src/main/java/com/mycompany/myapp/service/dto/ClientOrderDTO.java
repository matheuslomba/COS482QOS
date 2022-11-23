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

    private String clientEmail;

    private Integer orderPrice;

    private Integer numComponents;

    private String payment;

    private Boolean proceedToCheckout;

    private Boolean assemblyPC;

    private String deliveryAdd;

    private Boolean isCompatible;

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

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getNumComponents() {
        return numComponents;
    }

    public void setNumComponents(Integer numComponents) {
        this.numComponents = numComponents;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Boolean getProceedToCheckout() {
        return proceedToCheckout;
    }

    public void setProceedToCheckout(Boolean proceedToCheckout) {
        this.proceedToCheckout = proceedToCheckout;
    }

    public Boolean getAssemblyPC() {
        return assemblyPC;
    }

    public void setAssemblyPC(Boolean assemblyPC) {
        this.assemblyPC = assemblyPC;
    }

    public String getDeliveryAdd() {
        return deliveryAdd;
    }

    public void setDeliveryAdd(String deliveryAdd) {
        this.deliveryAdd = deliveryAdd;
    }

    public Boolean getIsCompatible() {
        return isCompatible;
    }

    public void setIsCompatible(Boolean isCompatible) {
        this.isCompatible = isCompatible;
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
            ", clientEmail='" + getClientEmail() + "'" +
            ", orderPrice=" + getOrderPrice() +
            ", numComponents=" + getNumComponents() +
            ", payment='" + getPayment() + "'" +
            ", proceedToCheckout='" + getProceedToCheckout() + "'" +
            ", assemblyPC='" + getAssemblyPC() + "'" +
            ", deliveryAdd='" + getDeliveryAdd() + "'" +
            ", isCompatible='" + getIsCompatible() + "'" +
            "}";
    }
}
