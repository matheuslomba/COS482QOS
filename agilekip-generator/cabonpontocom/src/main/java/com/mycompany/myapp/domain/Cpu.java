package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cpu.
 */
@Entity
@Table(name = "cpu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cpu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cpu_name")
    private String cpuName;

    @Column(name = "cpu_price")
    private Integer cpuPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cpu id(Long id) {
        this.id = id;
        return this;
    }

    public String getCpuName() {
        return this.cpuName;
    }

    public Cpu cpuName(String cpuName) {
        this.cpuName = cpuName;
        return this;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public Integer getCpuPrice() {
        return this.cpuPrice;
    }

    public Cpu cpuPrice(Integer cpuPrice) {
        this.cpuPrice = cpuPrice;
        return this;
    }

    public void setCpuPrice(Integer cpuPrice) {
        this.cpuPrice = cpuPrice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cpu)) {
            return false;
        }
        return id != null && id.equals(((Cpu) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cpu{" +
            "id=" + getId() +
            ", cpuName='" + getCpuName() + "'" +
            ", cpuPrice=" + getCpuPrice() +
            "}";
    }
}
