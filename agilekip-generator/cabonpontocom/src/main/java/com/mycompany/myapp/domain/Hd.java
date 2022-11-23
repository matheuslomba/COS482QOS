package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Hd.
 */
@Entity
@Table(name = "hd")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "hd_name")
    private String hdName;

    @Column(name = "hd_price")
    private Integer hdPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hd id(Long id) {
        this.id = id;
        return this;
    }

    public String getHdName() {
        return this.hdName;
    }

    public Hd hdName(String hdName) {
        this.hdName = hdName;
        return this;
    }

    public void setHdName(String hdName) {
        this.hdName = hdName;
    }

    public Integer getHdPrice() {
        return this.hdPrice;
    }

    public Hd hdPrice(Integer hdPrice) {
        this.hdPrice = hdPrice;
        return this;
    }

    public void setHdPrice(Integer hdPrice) {
        this.hdPrice = hdPrice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hd)) {
            return false;
        }
        return id != null && id.equals(((Hd) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hd{" +
            "id=" + getId() +
            ", hdName='" + getHdName() + "'" +
            ", hdPrice=" + getHdPrice() +
            "}";
    }
}
