package at.meroff.bac.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import at.meroff.bac.domain.enumeration.CardType;

/**
 * A DTO for the Card entity.
 */
public class CardDTO implements Serializable {

    private Long id;

    private Integer cardId;

    private String description;

    private CardType cardType;

    private Double x1;

    private Double y1;

    private Double x2;

    private Double y2;

    private Double x3;

    private Double y3;

    private Double x4;

    private Double y4;

    private Long fieldId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Double getx1() {
        return x1;
    }

    public void setx1(Double x1) {
        this.x1 = x1;
    }

    public Double gety1() {
        return y1;
    }

    public void sety1(Double y1) {
        this.y1 = y1;
    }

    public Double getx2() {
        return x2;
    }

    public void setx2(Double x2) {
        this.x2 = x2;
    }

    public Double gety2() {
        return y2;
    }

    public void sety2(Double y2) {
        this.y2 = y2;
    }

    public Double getx3() {
        return x3;
    }

    public void setx3(Double x3) {
        this.x3 = x3;
    }

    public Double gety3() {
        return y3;
    }

    public void sety3(Double y3) {
        this.y3 = y3;
    }

    public Double getx4() {
        return x4;
    }

    public void setx4(Double x4) {
        this.x4 = x4;
    }

    public Double gety4() {
        return y4;
    }

    public void sety4(Double y4) {
        this.y4 = y4;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CardDTO cardDTO = (CardDTO) o;
        if(cardDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cardDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CardDTO{" +
            "id=" + getId() +
            ", cardId=" + getCardId() +
            ", description='" + getDescription() + "'" +
            ", cardType='" + getCardType() + "'" +
            ", x1=" + getx1() +
            ", y1=" + gety1() +
            ", x2=" + getx2() +
            ", y2=" + gety2() +
            ", x3=" + getx3() +
            ", y3=" + gety3() +
            ", x4=" + getx4() +
            ", y4=" + gety4() +
            "}";
    }
}
