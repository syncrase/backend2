package fr.olympp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InteractionPlantePlante.
 */
@Entity
@Table(name = "interaction_plante_plante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InteractionPlantePlante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^(-|0|\\+){1}$")
    @Column(name = "notation")
    private String notation;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "interactionPlantePlante")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reference> references = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Plante dePlante;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Plante versPlante;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotation() {
        return notation;
    }

    public InteractionPlantePlante notation(String notation) {
        this.notation = notation;
        return this;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public String getDescription() {
        return description;
    }

    public InteractionPlantePlante description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Reference> getReferences() {
        return references;
    }

    public InteractionPlantePlante references(Set<Reference> references) {
        this.references = references;
        return this;
    }

    public InteractionPlantePlante addReference(Reference reference) {
        this.references.add(reference);
        reference.setInteractionPlantePlante(this);
        return this;
    }

    public InteractionPlantePlante removeReference(Reference reference) {
        this.references.remove(reference);
        reference.setInteractionPlantePlante(null);
        return this;
    }

    public void setReferences(Set<Reference> references) {
        this.references = references;
    }

    public Plante getDePlante() {
        return dePlante;
    }

    public InteractionPlantePlante dePlante(Plante plante) {
        this.dePlante = plante;
        return this;
    }

    public void setDePlante(Plante plante) {
        this.dePlante = plante;
    }

    public Plante getVersPlante() {
        return versPlante;
    }

    public InteractionPlantePlante versPlante(Plante plante) {
        this.versPlante = plante;
        return this;
    }

    public void setVersPlante(Plante plante) {
        this.versPlante = plante;
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
        InteractionPlantePlante interactionPlantePlante = (InteractionPlantePlante) o;
        if (interactionPlantePlante.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), interactionPlantePlante.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InteractionPlantePlante{" +
            "id=" + getId() +
            ", notation='" + getNotation() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
