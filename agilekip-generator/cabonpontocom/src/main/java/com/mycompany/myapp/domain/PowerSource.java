package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PowerSource.
 */
@Entity
@Table(name = "power_source")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PowerSource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "power_source_name")
    private String powerSourceName;

    @Column(name = "power_source_price")
    private Integer powerSourcePrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PowerSource id(Long id) {
        this.id = id;
        return this;
    }

    public String getPowerSourceName() {
        return this.powerSourceName;
    }

    public PowerSource powerSourceName(String powerSourceName) {
        this.powerSourceName = powerSourceName;
        return this;
    }

    public void setPowerSourceName(String powerSourceName) {
        this.powerSourceName = powerSourceName;
    }

    public Integer getPowerSourcePrice() {
        return this.powerSourcePrice;
    }

    public PowerSource powerSourcePrice(Integer powerSourcePrice) {
        this.powerSourcePrice = powerSourcePrice;
        return this;
    }

    public void setPowerSourcePrice(Integer powerSourcePrice) {
        this.powerSourcePrice = powerSourcePrice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PowerSource)) {
            return false;
        }
        return id != null && id.equals(((PowerSource) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PowerSource{" +
            "id=" + getId() +
            ", powerSourceName='" + getPowerSourceName() + "'" +
            ", powerSourcePrice=" + getPowerSourcePrice() +
            "}";
    }
}
