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
 * Criteria class for the Livre entity. This class is used in LivreResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /livres?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LivreCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private StringFilter isbn;

    private StringFilter auteur;

    private IntegerFilter page;

    private LongFilter referenceId;

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

    public StringFilter getIsbn() {
        return isbn;
    }

    public void setIsbn(StringFilter isbn) {
        this.isbn = isbn;
    }

    public StringFilter getAuteur() {
        return auteur;
    }

    public void setAuteur(StringFilter auteur) {
        this.auteur = auteur;
    }

    public IntegerFilter getPage() {
        return page;
    }

    public void setPage(IntegerFilter page) {
        this.page = page;
    }

    public LongFilter getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(LongFilter referenceId) {
        this.referenceId = referenceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LivreCriteria that = (LivreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(isbn, that.isbn) &&
            Objects.equals(auteur, that.auteur) &&
            Objects.equals(page, that.page) &&
            Objects.equals(referenceId, that.referenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        isbn,
        auteur,
        page,
        referenceId
        );
    }

    @Override
    public String toString() {
        return "LivreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (isbn != null ? "isbn=" + isbn + ", " : "") +
                (auteur != null ? "auteur=" + auteur + ", " : "") +
                (page != null ? "page=" + page + ", " : "") +
                (referenceId != null ? "referenceId=" + referenceId + ", " : "") +
            "}";
    }

}
