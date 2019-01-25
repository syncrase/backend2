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
 * Criteria class for the TypeFeuillage entity. This class is used in TypeFeuillageResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /type-feuillages?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TypeFeuillageCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter typeFeuillage;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTypeFeuillage() {
        return typeFeuillage;
    }

    public void setTypeFeuillage(StringFilter typeFeuillage) {
        this.typeFeuillage = typeFeuillage;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TypeFeuillageCriteria that = (TypeFeuillageCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(typeFeuillage, that.typeFeuillage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        typeFeuillage
        );
    }

    @Override
    public String toString() {
        return "TypeFeuillageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (typeFeuillage != null ? "typeFeuillage=" + typeFeuillage + ", " : "") +
            "}";
    }

}
