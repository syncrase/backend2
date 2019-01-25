package fr.olympp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ClassificationCronquist entity.
 */
public class ClassificationCronquistDTO implements Serializable {

    private Long id;

    private Long ordreId;

    private String ordreName;

    private Long familleId;

    private String familleName;

    private Long genreId;

    private String genreName;

    private Long especeId;

    private String especeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrdreId() {
        return ordreId;
    }

    public void setOrdreId(Long ordreId) {
        this.ordreId = ordreId;
    }

    public String getOrdreName() {
        return ordreName;
    }

    public void setOrdreName(String ordreName) {
        this.ordreName = ordreName;
    }

    public Long getFamilleId() {
        return familleId;
    }

    public void setFamilleId(Long familleId) {
        this.familleId = familleId;
    }

    public String getFamilleName() {
        return familleName;
    }

    public void setFamilleName(String familleName) {
        this.familleName = familleName;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Long getEspeceId() {
        return especeId;
    }

    public void setEspeceId(Long especeId) {
        this.especeId = especeId;
    }

    public String getEspeceName() {
        return especeName;
    }

    public void setEspeceName(String especeName) {
        this.especeName = especeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassificationCronquistDTO classificationCronquistDTO = (ClassificationCronquistDTO) o;
        if (classificationCronquistDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classificationCronquistDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassificationCronquistDTO{" +
            "id=" + getId() +
            ", ordre=" + getOrdreId() +
            ", ordre='" + getOrdreName() + "'" +
            ", famille=" + getFamilleId() +
            ", famille='" + getFamilleName() + "'" +
            ", genre=" + getGenreId() +
            ", genre='" + getGenreName() + "'" +
            ", espece=" + getEspeceId() +
            ", espece='" + getEspeceName() + "'" +
            "}";
    }
}
