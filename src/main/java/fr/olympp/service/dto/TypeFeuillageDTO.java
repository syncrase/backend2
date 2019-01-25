package fr.olympp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TypeFeuillage entity.
 */
public class TypeFeuillageDTO implements Serializable {

    private Long id;

    private String typeFeuillage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeFeuillage() {
        return typeFeuillage;
    }

    public void setTypeFeuillage(String typeFeuillage) {
        this.typeFeuillage = typeFeuillage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeFeuillageDTO typeFeuillageDTO = (TypeFeuillageDTO) o;
        if (typeFeuillageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeFeuillageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeFeuillageDTO{" +
            "id=" + getId() +
            ", typeFeuillage='" + getTypeFeuillage() + "'" +
            "}";
    }
}
