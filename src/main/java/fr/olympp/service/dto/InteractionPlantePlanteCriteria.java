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
 * Criteria class for the InteractionPlantePlante entity. This class is used in InteractionPlantePlanteResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /interaction-plante-plantes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InteractionPlantePlanteCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter notation;

    private StringFilter description;

    private LongFilter referenceId;

    private LongFilter dePlanteId;

    private LongFilter versPlanteId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNotation() {
        return notation;
    }

    public void setNotation(StringFilter notation) {
        this.notation = notation;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(LongFilter referenceId) {
        this.referenceId = referenceId;
    }

    public LongFilter getDePlanteId() {
        return dePlanteId;
    }

    public void setDePlanteId(LongFilter dePlanteId) {
        this.dePlanteId = dePlanteId;
    }

    public LongFilter getVersPlanteId() {
        return versPlanteId;
    }

    public void setVersPlanteId(LongFilter versPlanteId) {
        this.versPlanteId = versPlanteId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InteractionPlantePlanteCriteria that = (InteractionPlantePlanteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(notation, that.notation) &&
            Objects.equals(description, that.description) &&
            Objects.equals(referenceId, that.referenceId) &&
            Objects.equals(dePlanteId, that.dePlanteId) &&
            Objects.equals(versPlanteId, that.versPlanteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        notation,
        description,
        referenceId,
        dePlanteId,
        versPlanteId
        );
    }

    @Override
    public String toString() {
        return "InteractionPlantePlanteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (notation != null ? "notation=" + notation + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (referenceId != null ? "referenceId=" + referenceId + ", " : "") +
                (dePlanteId != null ? "dePlanteId=" + dePlanteId + ", " : "") +
                (versPlanteId != null ? "versPlanteId=" + versPlanteId + ", " : "") +
            "}";
    }

}
