package fr.olympp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Strate entity.
 */
public class StrateDTO implements Serializable {

    private Long id;

    private String strate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStrate() {
        return strate;
    }

    public void setStrate(String strate) {
        this.strate = strate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StrateDTO strateDTO = (StrateDTO) o;
        if (strateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), strateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StrateDTO{" +
            "id=" + getId() +
            ", strate='" + getStrate() + "'" +
            "}";
    }
}
