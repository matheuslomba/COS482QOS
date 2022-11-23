package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Hd} entity.
 */
public class HdDTO implements Serializable {

    private Long id;

    private String hdName;

    private Integer hdPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHdName() {
        return hdName;
    }

    public void setHdName(String hdName) {
        this.hdName = hdName;
    }

    public Integer getHdPrice() {
        return hdPrice;
    }

    public void setHdPrice(Integer hdPrice) {
        this.hdPrice = hdPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HdDTO)) {
            return false;
        }

        HdDTO hdDTO = (HdDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hdDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HdDTO{" +
            "id=" + getId() +
            ", hdName='" + getHdName() + "'" +
            ", hdPrice=" + getHdPrice() +
            "}";
    }
}
