package fr.olympp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RichesseSol entity.
 */
public class RichesseSolDTO implements Serializable {

    private Long id;

    private String richesseSol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRichesseSol() {
        return richesseSol;
    }

    public void setRichesseSol(String richesseSol) {
        this.richesseSol = richesseSol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RichesseSolDTO richesseSolDTO = (RichesseSolDTO) o;
        if (richesseSolDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), richesseSolDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RichesseSolDTO{" +
            "id=" + getId() +
            ", richesseSol='" + getRichesseSol() + "'" +
            "}";
    }
}
