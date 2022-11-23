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

    @Column(name = "order_components")
    private String orderComponents;

    @Column(name = "order_price")
    private String orderPrice;

    @Column(name = "num_items")
    private Integer numItems;

    @Column(name = "assembly_pc")
    private Boolean assemblyPC;

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

    public String getOrderComponents() {
        return this.orderComponents;
    }

    public ClientOrder orderComponents(String orderComponents) {
        this.orderComponents = orderComponents;
        return this;
    }

    public void setOrderComponents(String orderComponents) {
        this.orderComponents = orderComponents;
    }

    public String getOrderPrice() {
        return this.orderPrice;
    }

    public ClientOrder orderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
        return this;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getNumItems() {
        return this.numItems;
    }

    public ClientOrder numItems(Integer numItems) {
        this.numItems = numItems;
        return this;
    }

    public void setNumItems(Integer numItems) {
        this.numItems = numItems;
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
            ", orderComponents='" + getOrderComponents() + "'" +
            ", orderPrice='" + getOrderPrice() + "'" +
            ", numItems=" + getNumItems() +
            ", assemblyPC='" + getAssemblyPC() + "'" +
            "}";
    }
}
