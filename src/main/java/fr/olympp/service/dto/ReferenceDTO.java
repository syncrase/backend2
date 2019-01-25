package fr.olympp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Reference entity.
 */
public class ReferenceDTO implements Serializable {

    private Long id;

    private String description;

    private Long livreId;

    private Long pageWebId;

    private Long interactionPlantePlanteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLivreId() {
        return livreId;
    }

    public void setLivreId(Long livreId) {
        this.livreId = livreId;
    }

    public Long getPageWebId() {
        return pageWebId;
    }

    public void setPageWebId(Long pageWebId) {
        this.pageWebId = pageWebId;
    }

    public Long getInteractionPlantePlanteId() {
        return interactionPlantePlanteId;
    }

    public void setInteractionPlantePlanteId(Long interactionPlantePlanteId) {
        this.interactionPlantePlanteId = interactionPlantePlanteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReferenceDTO referenceDTO = (ReferenceDTO) o;
        if (referenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), referenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReferenceDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", livre=" + getLivreId() +
            ", pageWeb=" + getPageWebId() +
            ", interactionPlantePlante=" + getInteractionPlantePlanteId() +
            "}";
    }
}
