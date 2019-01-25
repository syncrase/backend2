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
 * Criteria class for the Ensoleillement entity. This class is used in EnsoleillementResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /ensoleillements?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnsoleillementCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ensoleillement;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEnsoleillement() {
        return ensoleillement;
    }

    public void setEnsoleillement(StringFilter ensoleillement) {
        this.ensoleillement = ensoleillement;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EnsoleillementCriteria that = (EnsoleillementCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ensoleillement, that.ensoleillement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ensoleillement
        );
    }

    @Override
    public String toString() {
        return "EnsoleillementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ensoleillement != null ? "ensoleillement=" + ensoleillement + ", " : "") +
            "}";
    }

}
