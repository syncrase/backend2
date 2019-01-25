package fr.olympp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InteractionPlantePlante entity.
 */
public class InteractionPlantePlanteDTO implements Serializable {

    private Long id;

    @Pattern(regexp = "^(-|0|\\+){1}$")
    private String notation;

    private String description;

    private Long dePlanteId;

    private Long versPlanteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDePlanteId() {
        return dePlanteId;
    }

    public void setDePlanteId(Long planteId) {
        this.dePlanteId = planteId;
    }

    public Long getVersPlanteId() {
        return versPlanteId;
    }

    public void setVersPlanteId(Long planteId) {
        this.versPlanteId = planteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InteractionPlantePlanteDTO interactionPlantePlanteDTO = (InteractionPlantePlanteDTO) o;
        if (interactionPlantePlanteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), interactionPlantePlanteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InteractionPlantePlanteDTO{" +
            "id=" + getId() +
            ", notation='" + getNotation() + "'" +
            ", description='" + getDescription() + "'" +
            ", dePlante=" + getDePlanteId() +
            ", versPlante=" + getVersPlanteId() +
            "}";
    }
}
