package fr.olympp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VitesseCroissance entity.
 */
public class VitesseCroissanceDTO implements Serializable {

    private Long id;

    private String vitesseCroissance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVitesseCroissance() {
        return vitesseCroissance;
    }

    public void setVitesseCroissance(String vitesseCroissance) {
        this.vitesseCroissance = vitesseCroissance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VitesseCroissanceDTO vitesseCroissanceDTO = (VitesseCroissanceDTO) o;
        if (vitesseCroissanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vitesseCroissanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VitesseCroissanceDTO{" +
            "id=" + getId() +
            ", vitesseCroissance='" + getVitesseCroissance() + "'" +
            "}";
    }
}
