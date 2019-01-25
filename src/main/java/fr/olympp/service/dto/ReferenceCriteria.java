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
 * Criteria class for the Reference entity. This class is used in ReferenceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /references?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReferenceCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private LongFilter livreId;

    private LongFilter pageWebId;

    private LongFilter interactionPlantePlanteId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getLivreId() {
        return livreId;
    }

    public void setLivreId(LongFilter livreId) {
        this.livreId = livreId;
    }

    public LongFilter getPageWebId() {
        return pageWebId;
    }

    public void setPageWebId(LongFilter pageWebId) {
        this.pageWebId = pageWebId;
    }

    public LongFilter getInteractionPlantePlanteId() {
        return interactionPlantePlanteId;
    }

    public void setInteractionPlantePlanteId(LongFilter interactionPlantePlanteId) {
        this.interactionPlantePlanteId = interactionPlantePlanteId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReferenceCriteria that = (ReferenceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(livreId, that.livreId) &&
            Objects.equals(pageWebId, that.pageWebId) &&
            Objects.equals(interactionPlantePlanteId, that.interactionPlantePlanteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        livreId,
        pageWebId,
        interactionPlantePlanteId
        );
    }

    @Override
    public String toString() {
        return "ReferenceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (livreId != null ? "livreId=" + livreId + ", " : "") +
                (pageWebId != null ? "pageWebId=" + pageWebId + ", " : "") +
                (interactionPlantePlanteId != null ? "interactionPlantePlanteId=" + interactionPlantePlanteId + ", " : "") +
            "}";
    }

}
