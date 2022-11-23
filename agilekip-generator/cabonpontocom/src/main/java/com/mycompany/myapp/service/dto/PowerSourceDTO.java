package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PowerSource} entity.
 */
public class PowerSourceDTO implements Serializable {

    private Long id;

    private String powerSourceName;

    private Integer powerSourcePrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPowerSourceName() {
        return powerSourceName;
    }

    public void setPowerSourceName(String powerSourceName) {
        this.powerSourceName = powerSourceName;
    }

    public Integer getPowerSourcePrice() {
        return powerSourcePrice;
    }

    public void setPowerSourcePrice(Integer powerSourcePrice) {
        this.powerSourcePrice = powerSourcePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PowerSourceDTO)) {
            return false;
        }

        PowerSourceDTO powerSourceDTO = (PowerSourceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, powerSourceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PowerSourceDTO{" +
            "id=" + getId() +
            ", powerSourceName='" + getPowerSourceName() + "'" +
            ", powerSourcePrice=" + getPowerSourcePrice() +
            "}";
    }
}
