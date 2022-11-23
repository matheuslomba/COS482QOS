package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ram.
 */
@Entity
@Table(name = "ram")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ram_name")
    private String ramName;

    @Column(name = "ram_price")
    private Integer ramPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ram id(Long id) {
        this.id = id;
        return this;
    }

    public String getRamName() {
        return this.ramName;
    }

    public Ram ramName(String ramName) {
        this.ramName = ramName;
        return this;
    }

    public void setRamName(String ramName) {
        this.ramName = ramName;
    }

    public Integer getRamPrice() {
        return this.ramPrice;
    }

    public Ram ramPrice(Integer ramPrice) {
        this.ramPrice = ramPrice;
        return this;
    }

    public void setRamPrice(Integer ramPrice) {
        this.ramPrice = ramPrice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ram)) {
            return false;
        }
        return id != null && id.equals(((Ram) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ram{" +
            "id=" + getId() +
            ", ramName='" + getRamName() + "'" +
            ", ramPrice=" + getRamPrice() +
            "}";
    }
}
