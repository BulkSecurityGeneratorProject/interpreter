package at.meroff.bac.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Field entity.
 */
public class FieldDTO implements Serializable {

    private Long id;

    private String description;

    @Lob
    private byte[] origImage;
    private String origImageContentType;

    @Lob
    private byte[] svgImage;
    private String svgImageContentType;

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

    public byte[] getOrigImage() {
        return origImage;
    }

    public void setOrigImage(byte[] origImage) {
        this.origImage = origImage;
    }

    public String getOrigImageContentType() {
        return origImageContentType;
    }

    public void setOrigImageContentType(String origImageContentType) {
        this.origImageContentType = origImageContentType;
    }

    public byte[] getSvgImage() {
        return svgImage;
    }

    public void setSvgImage(byte[] svgImage) {
        this.svgImage = svgImage;
    }

    public String getSvgImageContentType() {
        return svgImageContentType;
    }

    public void setSvgImageContentType(String svgImageContentType) {
        this.svgImageContentType = svgImageContentType;
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

        FieldDTO fieldDTO = (FieldDTO) o;
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
            ", origImage='" + getOrigImage() + "'" +
            ", svgImage='" + getSvgImage() + "'" +
            "}";
    }
}
