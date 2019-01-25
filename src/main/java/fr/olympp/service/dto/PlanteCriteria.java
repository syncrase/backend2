package fr.olympp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Plante entity. This class is used in PlanteResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /plantes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PlanteCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter phMin;

    private StringFilter phMax;

    private IntegerFilter tempMin;

    private IntegerFilter tempMax;

    private StringFilter description;

    private LongFilter classificationCronquistId;

    private LongFilter strateId;

    private LongFilter vitesseCroissanceId;

    private LongFilter ensoleillementId;

    private LongFilter richesseSolId;

    private LongFilter typeTerreId;

    private LongFilter typeFeuillageId;

    private LongFilter typeRacineId;

    private LongFilter plantCommonNameId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPhMin() {
        return phMin;
    }

    public void setPhMin(StringFilter phMin) {
        this.phMin = phMin;
    }

    public StringFilter getPhMax() {
        return phMax;
    }

    public void setPhMax(StringFilter phMax) {
        this.phMax = phMax;
    }

    public IntegerFilter getTempMin() {
        return tempMin;
    }

    public void setTempMin(IntegerFilter tempMin) {
        this.tempMin = tempMin;
    }

    public IntegerFilter getTempMax() {
        return tempMax;
    }

    public void setTempMax(IntegerFilter tempMax) {
        this.tempMax = tempMax;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getClassificationCronquistId() {
        return classificationCronquistId;
    }

    public void setClassificationCronquistId(LongFilter classificationCronquistId) {
        this.classificationCronquistId = classificationCronquistId;
    }

    public LongFilter getStrateId() {
        return strateId;
    }

    public void setStrateId(LongFilter strateId) {
        this.strateId = strateId;
    }

    public LongFilter getVitesseCroissanceId() {
        return vitesseCroissanceId;
    }

    public void setVitesseCroissanceId(LongFilter vitesseCroissanceId) {
        this.vitesseCroissanceId = vitesseCroissanceId;
    }

    public LongFilter getEnsoleillementId() {
        return ensoleillementId;
    }

    public void setEnsoleillementId(LongFilter ensoleillementId) {
        this.ensoleillementId = ensoleillementId;
    }

    public LongFilter getRichesseSolId() {
        return richesseSolId;
    }

    public void setRichesseSolId(LongFilter richesseSolId) {
        this.richesseSolId = richesseSolId;
    }

    public LongFilter getTypeTerreId() {
        return typeTerreId;
    }

    public void setTypeTerreId(LongFilter typeTerreId) {
        this.typeTerreId = typeTerreId;
    }

    public LongFilter getTypeFeuillageId() {
        return typeFeuillageId;
    }

    public void setTypeFeuillageId(LongFilter typeFeuillageId) {
        this.typeFeuillageId = typeFeuillageId;
    }

    public LongFilter getTypeRacineId() {
        return typeRacineId;
    }

    public void setTypeRacineId(LongFilter typeRacineId) {
        this.typeRacineId = typeRacineId;
    }

    public LongFilter getPlantCommonNameId() {
        return plantCommonNameId;
    }

    public void setPlantCommonNameId(LongFilter plantCommonNameId) {
        this.plantCommonNameId = plantCommonNameId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PlanteCriteria that = (PlanteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(phMin, that.phMin) &&
            Objects.equals(phMax, that.phMax) &&
            Objects.equals(tempMin, that.tempMin) &&
            Objects.equals(tempMax, that.tempMax) &&
            Objects.equals(description, that.description) &&
            Objects.equals(classificationCronquistId, that.classificationCronquistId) &&
            Objects.equals(strateId, that.strateId) &&
            Objects.equals(vitesseCroissanceId, that.vitesseCroissanceId) &&
            Objects.equals(ensoleillementId, that.ensoleillementId) &&
            Objects.equals(richesseSolId, that.richesseSolId) &&
            Objects.equals(typeTerreId, that.typeTerreId) &&
            Objects.equals(typeFeuillageId, that.typeFeuillageId) &&
            Objects.equals(typeRacineId, that.typeRacineId) &&
            Objects.equals(plantCommonNameId, that.plantCommonNameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        phMin,
        phMax,
        tempMin,
        tempMax,
        description,
        classificationCronquistId,
        strateId,
        vitesseCroissanceId,
        ensoleillementId,
        richesseSolId,
        typeTerreId,
        typeFeuillageId,
        typeRacineId,
        plantCommonNameId
        );
    }

    @Override
    public String toString() {
        return "PlanteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (phMin != null ? "phMin=" + phMin + ", " : "") +
                (phMax != null ? "phMax=" + phMax + ", " : "") +
                (tempMin != null ? "tempMin=" + tempMin + ", " : "") +
                (tempMax != null ? "tempMax=" + tempMax + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (classificationCronquistId != null ? "classificationCronquistId=" + classificationCronquistId + ", " : "") +
                (strateId != null ? "strateId=" + strateId + ", " : "") +
                (vitesseCroissanceId != null ? "vitesseCroissanceId=" + vitesseCroissanceId + ", " : "") +
                (ensoleillementId != null ? "ensoleillementId=" + ensoleillementId + ", " : "") +
                (richesseSolId != null ? "richesseSolId=" + richesseSolId + ", " : "") +
                (typeTerreId != null ? "typeTerreId=" + typeTerreId + ", " : "") +
                (typeFeuillageId != null ? "typeFeuillageId=" + typeFeuillageId + ", " : "") +
                (typeRacineId != null ? "typeRacineId=" + typeRacineId + ", " : "") +
                (plantCommonNameId != null ? "plantCommonNameId=" + plantCommonNameId + ", " : "") +
            "}";
    }

}
