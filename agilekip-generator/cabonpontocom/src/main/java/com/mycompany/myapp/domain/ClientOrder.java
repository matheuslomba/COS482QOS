package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ClientOrder.
 */
@Entity
@Table(name = "client_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClientOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "order_id")
    private String orderID;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_email")
    private String clientEmail;

    @Column(name = "order_price")
    private Integer orderPrice;

    @Column(name = "num_components")
    private Integer numComponents;

    @Column(name = "payment")
    private String payment;

    @Column(name = "proceed_to_checkout")
    private Boolean proceedToCheckout;

    @Column(name = "assembly_pc")
    private Boolean assemblyPC;

    @Column(name = "delivery_add")
    private String deliveryAdd;

    @Column(name = "is_compatible")
    private Boolean isCompatible;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientOrder id(Long id) {
        this.id = id;
        return this;
    }

    public String getOrderID() {
        return this.orderID;
    }

    public ClientOrder orderID(String orderID) {
        this.orderID = orderID;
        return this;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public ClientOrder orderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getClientName() {
        return this.clientName;
    }

    public ClientOrder clientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return this.clientEmail;
    }

    public ClientOrder clientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
        return this;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Integer getOrderPrice() {
        return this.orderPrice;
    }

    public ClientOrder orderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
        return this;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getNumComponents() {
        return this.numComponents;
    }

    public ClientOrder numComponents(Integer numComponents) {
        this.numComponents = numComponents;
        return this;
    }

    public void setNumComponents(Integer numComponents) {
        this.numComponents = numComponents;
    }

    public String getPayment() {
        return this.payment;
    }

    public ClientOrder payment(String payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Boolean getProceedToCheckout() {
        return this.proceedToCheckout;
    }

    public ClientOrder proceedToCheckout(Boolean proceedToCheckout) {
        this.proceedToCheckout = proceedToCheckout;
        return this;
    }

    public void setProceedToCheckout(Boolean proceedToCheckout) {
        this.proceedToCheckout = proceedToCheckout;
    }

    public Boolean getAssemblyPC() {
        return this.assemblyPC;
    }

    public ClientOrder assemblyPC(Boolean assemblyPC) {
        this.assemblyPC = assemblyPC;
        return this;
    }

    public void setAssemblyPC(Boolean assemblyPC) {
        this.assemblyPC = assemblyPC;
    }

    public String getDeliveryAdd() {
        return this.deliveryAdd;
    }

    public ClientOrder deliveryAdd(String deliveryAdd) {
        this.deliveryAdd = deliveryAdd;
        return this;
    }

    public void setDeliveryAdd(String deliveryAdd) {
        this.deliveryAdd = deliveryAdd;
    }

    public Boolean getIsCompatible() {
        return this.isCompatible;
    }

    public ClientOrder isCompatible(Boolean isCompatible) {
        this.isCompatible = isCompatible;
        return this;
    }

    public void setIsCompatible(Boolean isCompatible) {
        this.isCompatible = isCompatible;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientOrder)) {
            return false;
        }
        return id != null && id.equals(((ClientOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientOrder{" +
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
