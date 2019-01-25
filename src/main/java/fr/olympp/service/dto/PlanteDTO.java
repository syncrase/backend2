package fr.olympp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Plante entity.
 */
public class PlanteDTO implements Serializable {

    private Long id;

    @Pattern(regexp = "^\\d{0,1}(,\\d){0,1}$")
    private String phMin;

    @Pattern(regexp = "^\\d{0,1}(,\\d){0,1}$")
    private String phMax;

    private Integer tempMin;

    private Integer tempMax;

    private String description;

    private Long classificationCronquistId;

    private Long strateId;

    private Long vitesseCroissanceId;

    private Long ensoleillementId;

    private Long richesseSolId;

    private Long typeTerreId;

    private Long typeFeuillageId;

    private Long typeRacineId;

    private Set<PlantCommonNameDTO> plantCommonNames = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhMin() {
        return phMin;
    }

    public void setPhMin(String phMin) {
        this.phMin = phMin;
    }

    public String getPhMax() {
        return phMax;
    }

    public void setPhMax(String phMax) {
        this.phMax = phMax;
    }

    public Integer getTempMin() {
        return tempMin;
    }

    public void setTempMin(Integer tempMin) {
        this.tempMin = tempMin;
    }

    public Integer getTempMax() {
        return tempMax;
    }

    public void setTempMax(Integer tempMax) {
        this.tempMax = tempMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getClassificationCronquistId() {
        return classificationCronquistId;
    }

    public void setClassificationCronquistId(Long classificationCronquistId) {
        this.classificationCronquistId = classificationCronquistId;
    }

    public Long getStrateId() {
        return strateId;
    }

    public void setStrateId(Long strateId) {
        this.strateId = strateId;
    }

    public Long getVitesseCroissanceId() {
        return vitesseCroissanceId;
    }

    public void setVitesseCroissanceId(Long vitesseCroissanceId) {
        this.vitesseCroissanceId = vitesseCroissanceId;
    }

    public Long getEnsoleillementId() {
        return ensoleillementId;
    }

    public void setEnsoleillementId(Long ensoleillementId) {
        this.ensoleillementId = ensoleillementId;
    }

    public Long getRichesseSolId() {
        return richesseSolId;
    }

    public void setRichesseSolId(Long richesseSolId) {
        this.richesseSolId = richesseSolId;
    }

    public Long getTypeTerreId() {
        return typeTerreId;
    }

    public void setTypeTerreId(Long typeTerreId) {
        this.typeTerreId = typeTerreId;
    }

    public Long getTypeFeuillageId() {
        return typeFeuillageId;
    }

    public void setTypeFeuillageId(Long typeFeuillageId) {
        this.typeFeuillageId = typeFeuillageId;
    }

    public Long getTypeRacineId() {
        return typeRacineId;
    }

    public void setTypeRacineId(Long typeRacineId) {
        this.typeRacineId = typeRacineId;
    }

    public Set<PlantCommonNameDTO> getPlantCommonNames() {
        return plantCommonNames;
    }

    public void setPlantCommonNames(Set<PlantCommonNameDTO> plantCommonNames) {
        this.plantCommonNames = plantCommonNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlanteDTO planteDTO = (PlanteDTO) o;
        if (planteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlanteDTO{" +
            "id=" + getId() +
            ", phMin='" + getPhMin() + "'" +
            ", phMax='" + getPhMax() + "'" +
            ", tempMin=" + getTempMin() +
            ", tempMax=" + getTempMax() +
            ", description='" + getDescription() + "'" +
            ", classificationCronquist=" + getClassificationCronquistId() +
            ", strate=" + getStrateId() +
            ", vitesseCroissance=" + getVitesseCroissanceId() +
            ", ensoleillement=" + getEnsoleillementId() +
            ", richesseSol=" + getRichesseSolId() +
            ", typeTerre=" + getTypeTerreId() +
            ", typeFeuillage=" + getTypeFeuillageId() +
            ", typeRacine=" + getTypeRacineId() +
            "}";
    }
}
