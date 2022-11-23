package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Cpu} entity.
 */
public class CpuDTO implements Serializable {

    private Long id;

    private String cpuName;

    private Integer cpuPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public Integer getCpuPrice() {
        return cpuPrice;
    }

    public void setCpuPrice(Integer cpuPrice) {
        this.cpuPrice = cpuPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CpuDTO)) {
            return false;
        }

        CpuDTO cpuDTO = (CpuDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cpuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CpuDTO{" +
            "id=" + getId() +
            ", cpuName='" + getCpuName() + "'" +
            ", cpuPrice=" + getCpuPrice() +
            "}";
    }
}
