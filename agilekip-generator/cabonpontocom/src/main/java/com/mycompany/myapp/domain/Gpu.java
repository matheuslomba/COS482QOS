package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Gpu.
 */
@Entity
@Table(name = "gpu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Gpu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "gpu_name")
    private String gpuName;

    @Column(name = "gpu_price")
    private Integer gpuPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gpu id(Long id) {
        this.id = id;
        return this;
    }

    public String getGpuName() {
        return this.gpuName;
    }

    public Gpu gpuName(String gpuName) {
        this.gpuName = gpuName;
        return this;
    }

    public void setGpuName(String gpuName) {
        this.gpuName = gpuName;
    }

    public Integer getGpuPrice() {
        return this.gpuPrice;
    }

    public Gpu gpuPrice(Integer gpuPrice) {
        this.gpuPrice = gpuPrice;
        return this;
    }

    public void setGpuPrice(Integer gpuPrice) {
        this.gpuPrice = gpuPrice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gpu)) {
            return false;
        }
        return id != null && id.equals(((Gpu) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gpu{" +
            "id=" + getId() +
            ", gpuName='" + getGpuName() + "'" +
            ", gpuPrice=" + getGpuPrice() +
            "}";
    }
}
