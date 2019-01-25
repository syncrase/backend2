package fr.olympp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Recolte entity.
 */
public class RecolteDTO implements Serializable {

    private Long id;

    private Long planteId;

    private Long moisId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanteId() {
        return planteId;
    }

    public void setPlanteId(Long planteId) {
        this.planteId = planteId;
    }

    public Long getMoisId() {
        return moisId;
    }

    public void setMoisId(Long moisId) {
        this.moisId = moisId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecolteDTO recolteDTO = (RecolteDTO) o;
        if (recolteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recolteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecolteDTO{" +
            "id=" + getId() +
            ", plante=" + getPlanteId() +
            ", mois=" + getMoisId() +
            "}";
    }
}
