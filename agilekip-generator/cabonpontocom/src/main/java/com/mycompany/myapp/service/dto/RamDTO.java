package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Ram} entity.
 */
public class RamDTO implements Serializable {

    private Long id;

    private String ramName;

    private Integer ramPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRamName() {
        return ramName;
    }

    public void setRamName(String ramName) {
        this.ramName = ramName;
    }

    public Integer getRamPrice() {
        return ramPrice;
    }

    public void setRamPrice(Integer ramPrice) {
        this.ramPrice = ramPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RamDTO)) {
            return false;
        }

        RamDTO ramDTO = (RamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ramDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RamDTO{" +
            "id=" + getId() +
            ", ramName='" + getRamName() + "'" +
            ", ramPrice=" + getRamPrice() +
            "}";
    }
}
