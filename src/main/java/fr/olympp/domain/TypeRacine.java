package fr.olympp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TypeRacine.
 */
@Entity
@Table(name = "type_racine")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeRacine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_racine")
    private String typeRacine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeRacine() {
        return typeRacine;
    }

    public TypeRacine typeRacine(String typeRacine) {
        this.typeRacine = typeRacine;
        return this;
    }

    public void setTypeRacine(String typeRacine) {
        this.typeRacine = typeRacine;
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
        TypeRacine typeRacine = (TypeRacine) o;
        if (typeRacine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeRacine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeRacine{" +
            "id=" + getId() +
            ", typeRacine='" + getTypeRacine() + "'" +
            "}";
    }
}
