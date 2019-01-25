package fr.olympp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Mois entity.
 */
public class MoisDTO implements Serializable {

    private Long id;

    private String mois;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MoisDTO moisDTO = (MoisDTO) o;
        if (moisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MoisDTO{" +
            "id=" + getId() +
            ", mois='" + getMois() + "'" +
            "}";
    }
}
