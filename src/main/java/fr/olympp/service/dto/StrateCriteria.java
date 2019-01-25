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
 * Criteria class for the Strate entity. This class is used in StrateResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /strates?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StrateCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter strate;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStrate() {
        return strate;
    }

    public void setStrate(StringFilter strate) {
        this.strate = strate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StrateCriteria that = (StrateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(strate, that.strate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        strate
        );
    }

    @Override
    public String toString() {
        return "StrateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (strate != null ? "strate=" + strate + ", " : "") +
            "}";
    }

}
