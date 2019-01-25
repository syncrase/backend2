package fr.olympp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Strate.
 */
@Entity
@Table(name = "strate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Strate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "strate")
    private String strate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStrate() {
        return strate;
    }

    public Strate strate(String strate) {
        this.strate = strate;
        return this;
    }

    public void setStrate(String strate) {
        this.strate = strate;
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
        Strate strate = (Strate) o;
        if (strate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), strate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Strate{" +
            "id=" + getId() +
            ", strate='" + getStrate() + "'" +
            "}";
    }
}
