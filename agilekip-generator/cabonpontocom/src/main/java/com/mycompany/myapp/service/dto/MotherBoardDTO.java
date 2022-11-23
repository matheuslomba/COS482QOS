package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.MotherBoard} entity.
 */
public class MotherBoardDTO implements Serializable {

    private Long id;

    private String motherBoardName;

    private Integer motherBoardPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotherBoardName() {
        return motherBoardName;
    }

    public void setMotherBoardName(String motherBoardName) {
        this.motherBoardName = motherBoardName;
    }

    public Integer getMotherBoardPrice() {
        return motherBoardPrice;
    }

    public void setMotherBoardPrice(Integer motherBoardPrice) {
        this.motherBoardPrice = motherBoardPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MotherBoardDTO)) {
            return false;
        }

        MotherBoardDTO motherBoardDTO = (MotherBoardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, motherBoardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MotherBoardDTO{" +
            "id=" + getId() +
            ", motherBoardName='" + getMotherBoardName() + "'" +
            ", motherBoardPrice=" + getMotherBoardPrice() +
            "}";
    }
}
