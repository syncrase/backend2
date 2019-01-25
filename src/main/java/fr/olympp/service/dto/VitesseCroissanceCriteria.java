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
 * Criteria class for the VitesseCroissance entity. This class is used in VitesseCroissanceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /vitesse-croissances?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VitesseCroissanceCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter vitesseCroissance;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVitesseCroissance() {
        return vitesseCroissance;
    }

    public void setVitesseCroissance(StringFilter vitesseCroissance) {
        this.vitesseCroissance = vitesseCroissance;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VitesseCroissanceCriteria that = (VitesseCroissanceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(vitesseCroissance, that.vitesseCroissance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        vitesseCroissance
        );
    }

    @Override
    public String toString() {
        return "VitesseCroissanceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (vitesseCroissance != null ? "vitesseCroissance=" + vitesseCroissance + ", " : "") +
            "}";
    }

}
