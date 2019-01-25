package fr.olympp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PlantCommonName.
 */
@Entity
@Table(name = "plant_common_name")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlantCommonName implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "common_name")
    private String commonName;

    @ManyToMany(mappedBy = "plantCommonNames")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Plante> plantes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public PlantCommonName commonName(String commonName) {
        this.commonName = commonName;
        return this;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public Set<Plante> getPlantes() {
        return plantes;
    }

    public PlantCommonName plantes(Set<Plante> plantes) {
        this.plantes = plantes;
        return this;
    }

    public PlantCommonName addPlante(Plante plante) {
        this.plantes.add(plante);
        plante.getPlantCommonNames().add(this);
        return this;
    }

    public PlantCommonName removePlante(Plante plante) {
        this.plantes.remove(plante);
        plante.getPlantCommonNames().remove(this);
        return this;
    }

    public void setPlantes(Set<Plante> plantes) {
        this.plantes = plantes;
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
        PlantCommonName plantCommonName = (PlantCommonName) o;
        if (plantCommonName.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plantCommonName.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlantCommonName{" +
            "id=" + getId() +
            ", commonName='" + getCommonName() + "'" +
            "}";
    }
}
