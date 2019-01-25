package fr.olympp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TypeRacine entity.
 */
public class TypeRacineDTO implements Serializable {

    private Long id;

    private String typeRacine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeRacine() {
        return typeRacine;
    }

    public void setTypeRacine(String typeRacine) {
        this.typeRacine = typeRacine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeRacineDTO typeRacineDTO = (TypeRacineDTO) o;
        if (typeRacineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeRacineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeRacineDTO{" +
            "id=" + getId() +
            ", typeRacine='" + getTypeRacine() + "'" +
            "}";
    }
}
