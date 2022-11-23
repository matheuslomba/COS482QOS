package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Gpu} entity.
 */
public class GpuDTO implements Serializable {

    private Long id;

    private String gpuName;

    private Integer gpuPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGpuName() {
        return gpuName;
    }

    public void setGpuName(String gpuName) {
        this.gpuName = gpuName;
    }

    public Integer getGpuPrice() {
        return gpuPrice;
    }

    public void setGpuPrice(Integer gpuPrice) {
        this.gpuPrice = gpuPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GpuDTO)) {
            return false;
        }

        GpuDTO gpuDTO = (GpuDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gpuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GpuDTO{" +
            "id=" + getId() +
            ", gpuName='" + getGpuName() + "'" +
            ", gpuPrice=" + getGpuPrice() +
            "}";
    }
}
