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
 * Criteria class for the RichesseSol entity. This class is used in RichesseSolResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /richesse-sols?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RichesseSolCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter richesseSol;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRichesseSol() {
        return richesseSol;
    }

    public void setRichesseSol(StringFilter richesseSol) {
        this.richesseSol = richesseSol;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RichesseSolCriteria that = (RichesseSolCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(richesseSol, that.richesseSol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        richesseSol
        );
    }

    @Override
    public String toString() {
        return "RichesseSolCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (richesseSol != null ? "richesseSol=" + richesseSol + ", " : "") +
            "}";
    }

}
