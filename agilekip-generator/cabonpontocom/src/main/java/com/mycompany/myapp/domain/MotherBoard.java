package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MotherBoard.
 */
@Entity
@Table(name = "mother_board")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MotherBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "mother_board_name")
    private String motherBoardName;

    @Column(name = "mother_board_price")
    private Integer motherBoardPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MotherBoard id(Long id) {
        this.id = id;
        return this;
    }

    public String getMotherBoardName() {
        return this.motherBoardName;
    }

    public MotherBoard motherBoardName(String motherBoardName) {
        this.motherBoardName = motherBoardName;
        return this;
    }

    public void setMotherBoardName(String motherBoardName) {
        this.motherBoardName = motherBoardName;
    }

    public Integer getMotherBoardPrice() {
        return this.motherBoardPrice;
    }

    public MotherBoard motherBoardPrice(Integer motherBoardPrice) {
        this.motherBoardPrice = motherBoardPrice;
        return this;
    }

    public void setMotherBoardPrice(Integer motherBoardPrice) {
        this.motherBoardPrice = motherBoardPrice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MotherBoard)) {
            return false;
        }
        return id != null && id.equals(((MotherBoard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MotherBoard{" +
            "id=" + getId() +
            ", motherBoardName='" + getMotherBoardName() + "'" +
            ", motherBoardPrice=" + getMotherBoardPrice() +
            "}";
    }
}
