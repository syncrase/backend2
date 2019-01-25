package fr.olympp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ClassificationCronquist.
 */
@Entity
@Table(name = "classification_cronquist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClassificationCronquist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Ordre ordre;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Famille famille;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Genre genre;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Espece espece;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ordre getOrdre() {
        return ordre;
    }

    public ClassificationCronquist ordre(Ordre ordre) {
        this.ordre = ordre;
        return this;
    }

    public void setOrdre(Ordre ordre) {
        this.ordre = ordre;
    }

    public Famille getFamille() {
        return famille;
    }

    public ClassificationCronquist famille(Famille famille) {
        this.famille = famille;
        return this;
    }

    public void setFamille(Famille famille) {
        this.famille = famille;
    }

    public Genre getGenre() {
        return genre;
    }

    public ClassificationCronquist genre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Espece getEspece() {
        return espece;
    }

    public ClassificationCronquist espece(Espece espece) {
        this.espece = espece;
        return this;
    }

    public void setEspece(Espece espece) {
        this.espece = espece;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClassificationCronquist classificationCronquist = (ClassificationCronquist) o;
        if (classificationCronquist.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classificationCronquist.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassificationCronquist{" +
            "id=" + getId() +
            "}";
    }
}
