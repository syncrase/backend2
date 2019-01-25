package fr.olympp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Livre entity.
 */
public class LivreDTO implements Serializable {

    private Long id;

    private String description;

    private String isbn;

    private String auteur;

    private Integer page;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LivreDTO livreDTO = (LivreDTO) o;
        if (livreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), livreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LivreDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", isbn='" + getIsbn() + "'" +
            ", auteur='" + getAuteur() + "'" +
            ", page=" + getPage() +
            "}";
    }
}
