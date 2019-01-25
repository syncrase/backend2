package fr.olympp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PlantCommonName entity.
 */
public class PlantCommonNameDTO implements Serializable {

    private Long id;

    private String commonName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlantCommonNameDTO plantCommonNameDTO = (PlantCommonNameDTO) o;
        if (plantCommonNameDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plantCommonNameDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlantCommonNameDTO{" +
            "id=" + getId() +
            ", commonName='" + getCommonName() + "'" +
            "}";
    }
}
