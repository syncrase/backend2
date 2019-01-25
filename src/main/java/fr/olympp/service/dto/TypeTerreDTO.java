package fr.olympp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TypeTerre entity.
 */
public class TypeTerreDTO implements Serializable {

    private Long id;

    private String typeTerre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeTerre() {
        return typeTerre;
    }

    public void setTypeTerre(String typeTerre) {
        this.typeTerre = typeTerre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeTerreDTO typeTerreDTO = (TypeTerreDTO) o;
        if (typeTerreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeTerreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeTerreDTO{" +
            "id=" + getId() +
            ", typeTerre='" + getTypeTerre() + "'" +
            "}";
    }
}
