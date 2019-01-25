package fr.olympp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Ensoleillement entity.
 */
public class EnsoleillementDTO implements Serializable {

    private Long id;

    private String ensoleillement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnsoleillement() {
        return ensoleillement;
    }

    public void setEnsoleillement(String ensoleillement) {
        this.ensoleillement = ensoleillement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnsoleillementDTO ensoleillementDTO = (EnsoleillementDTO) o;
        if (ensoleillementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ensoleillementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnsoleillementDTO{" +
            "id=" + getId() +
            ", ensoleillement='" + getEnsoleillement() + "'" +
            "}";
    }
}
