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
 * Criteria class for the ClassificationCronquist entity. This class is used in ClassificationCronquistResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /classification-cronquists?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClassificationCronquistCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter ordreId;

    private LongFilter familleId;

    private LongFilter genreId;

    private LongFilter especeId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getOrdreId() {
        return ordreId;
    }

    public void setOrdreId(LongFilter ordreId) {
        this.ordreId = ordreId;
    }

    public LongFilter getFamilleId() {
        return familleId;
    }

    public void setFamilleId(LongFilter familleId) {
        this.familleId = familleId;
    }

    public LongFilter getGenreId() {
        return genreId;
    }

    public void setGenreId(LongFilter genreId) {
        this.genreId = genreId;
    }

    public LongFilter getEspeceId() {
        return especeId;
    }

    public void setEspeceId(LongFilter especeId) {
        this.especeId = especeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClassificationCronquistCriteria that = (ClassificationCronquistCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ordreId, that.ordreId) &&
            Objects.equals(familleId, that.familleId) &&
            Objects.equals(genreId, that.genreId) &&
            Objects.equals(especeId, that.especeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ordreId,
        familleId,
        genreId,
        especeId
        );
    }

    @Override
    public String toString() {
        return "ClassificationCronquistCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ordreId != null ? "ordreId=" + ordreId + ", " : "") +
                (familleId != null ? "familleId=" + familleId + ", " : "") +
                (genreId != null ? "genreId=" + genreId + ", " : "") +
                (especeId != null ? "especeId=" + especeId + ", " : "") +
            "}";
    }

}
