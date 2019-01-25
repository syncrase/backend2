package fr.olympp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TypeFeuillage.
 */
@Entity
@Table(name = "type_feuillage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeFeuillage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_feuillage")
    private String typeFeuillage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeFeuillage() {
        return typeFeuillage;
    }

    public TypeFeuillage typeFeuillage(String typeFeuillage) {
        this.typeFeuillage = typeFeuillage;
        return this;
    }

    public void setTypeFeuillage(String typeFeuillage) {
        this.typeFeuillage = typeFeuillage;
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
        TypeFeuillage typeFeuillage = (TypeFeuillage) o;
        if (typeFeuillage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeFeuillage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeFeuillage{" +
            "id=" + getId() +
            ", typeFeuillage='" + getTypeFeuillage() + "'" +
            "}";
    }
}
