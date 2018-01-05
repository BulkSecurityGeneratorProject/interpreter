package at.meroff.bac.service.dto;


import at.meroff.bac.domain.enumeration.LayoutType;

import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Field entity.
 */
public class FieldDTOSmall implements Serializable {

    private Long id;

    private String description;

    private LayoutType layoutType;

    private Set<CardDTO> cards;

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

    public LayoutType getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(LayoutType layoutType) {
        this.layoutType = layoutType;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }

    public void setCards(Set<CardDTO> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FieldDTOSmall fieldDTO = (FieldDTOSmall) o;
        if(fieldDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fieldDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FieldDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", layoutType='" + getLayoutType() + "'" +
            "}";
    }
}
