package fr.olympp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Reference.
 */
@Entity
@Table(name = "reference")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToOne    @JoinColumn(unique = true)
    private Livre livre;

    @OneToOne    @JoinColumn(unique = true)
    private PageWeb pageWeb;

    @ManyToOne
    @JsonIgnoreProperties("references")
    private InteractionPlantePlante interactionPlantePlante;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Reference description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Livre getLivre() {
        return livre;
    }

    public Reference livre(Livre livre) {
        this.livre = livre;
        return this;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public PageWeb getPageWeb() {
        return pageWeb;
    }

    public Reference pageWeb(PageWeb pageWeb) {
        this.pageWeb = pageWeb;
        return this;
    }

    public void setPageWeb(PageWeb pageWeb) {
        this.pageWeb = pageWeb;
    }

    public InteractionPlantePlante getInteractionPlantePlante() {
        return interactionPlantePlante;
    }

    public Reference interactionPlantePlante(InteractionPlantePlante interactionPlantePlante) {
        this.interactionPlantePlante = interactionPlantePlante;
        return this;
    }

    public void setInteractionPlantePlante(InteractionPlantePlante interactionPlantePlante) {
        this.interactionPlantePlante = interactionPlantePlante;
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
        Reference reference = (Reference) o;
        if (reference.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reference.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reference{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
