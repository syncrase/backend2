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
 * Criteria class for the Floraison entity. This class is used in FloraisonResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /floraisons?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FloraisonCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter planteId;

    private LongFilter moisId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getPlanteId() {
        return planteId;
    }

    public void setPlanteId(LongFilter planteId) {
        this.planteId = planteId;
    }

    public LongFilter getMoisId() {
        return moisId;
    }

    public void setMoisId(LongFilter moisId) {
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
        final FloraisonCriteria that = (FloraisonCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(planteId, that.planteId) &&
            Objects.equals(moisId, that.moisId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        planteId,
        moisId
        );
    }

    @Override
    public String toString() {
        return "FloraisonCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (planteId != null ? "planteId=" + planteId + ", " : "") +
                (moisId != null ? "moisId=" + moisId + ", " : "") +
            "}";
    }

}
