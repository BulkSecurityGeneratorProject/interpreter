package at.meroff.bac.domain;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import at.meroff.bac.domain.enumeration.CardType;

/**
 * A Card.
 */
@Entity
@Table(name = "card")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_id")
    private Integer cardId;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @Column(name = "x_1")
    private Double x1;

    @Column(name = "y_1")
    private Double y1;

    @Column(name = "x_2")
    private Double x2;

    @Column(name = "y_2")
    private Double y2;

    @Column(name = "x_3")
    private Double x3;

    @Column(name = "y_3")
    private Double y3;

    @Column(name = "x_4")
    private Double x4;

    @Column(name = "y_4")
    private Double y4;

    @ManyToOne
    private Field field;

    @Ignore
    public Set<Card> assignedTasks;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCardId() {
        return cardId;
    }

    public Card cardId(Integer cardId) {
        this.cardId = cardId;
        return this;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getDescription() {
        return description;
    }

    public Card description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Card cardType(CardType cardType) {
        this.cardType = cardType;
        return this;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Double getx1() {
        return x1;
    }

    public Card x1(Double x1) {
        this.x1 = x1;
        return this;
    }

    public void setx1(Double x1) {
        this.x1 = x1;
    }

    public Double gety1() {
        return y1;
    }

    public Card y1(Double y1) {
        this.y1 = y1;
        return this;
    }

    public void sety1(Double y1) {
        this.y1 = y1;
    }

    public Double getx2() {
        return x2;
    }

    public Card x2(Double x2) {
        this.x2 = x2;
        return this;
    }

    public void setx2(Double x2) {
        this.x2 = x2;
    }

    public Double gety2() {
        return y2;
    }

    public Card y2(Double y2) {
        this.y2 = y2;
        return this;
    }

    public void sety2(Double y2) {
        this.y2 = y2;
    }

    public Double getx3() {
        return x3;
    }

    public Card x3(Double x3) {
        this.x3 = x3;
        return this;
    }

    public void setx3(Double x3) {
        this.x3 = x3;
    }

    public Double gety3() {
        return y3;
    }

    public Card y3(Double y3) {
        this.y3 = y3;
        return this;
    }

    public void sety3(Double y3) {
        this.y3 = y3;
    }

    public Double getx4() {
        return x4;
    }

    public Card x4(Double x4) {
        this.x4 = x4;
        return this;
    }

    public void setx4(Double x4) {
        this.x4 = x4;
    }

    public Double gety4() {
        return y4;
    }

    public Card y4(Double y4) {
        this.y4 = y4;
        return this;
    }

    public void sety4(Double y4) {
        this.y4 = y4;
    }

    public Field getField() {
        return field;
    }

    public Card field(Field field) {
        this.field = field;
        return this;
    }

    public void setField(Field field) {
        this.field = field;
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
        Card card = (Card) o;
        if (card.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), card.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Card{" +
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

    /**
     * Calculates the Distance between two cards (measured center to center)
     * @param source source card for calculation
     * @param target target card for calculation
     * @return distance between the cards
     */
    static double getDistance(Card source, Card target) {
        return Math.sqrt(
            Math.pow(target.getCenter().x - source.getCenter().x,2) +
                Math.pow(target.getCenter().y - source.getCenter().y,2)
        );
    }

    /**
     * Method returns the center of the card
     * @return center point
     */
    Point2D.Double getCenter() {
        Point2D pt01 = new Point2D.Double((x1 + x3) / 2, (y1 + y3) / 2);
        Point2D pt02 = new Point2D.Double((x2 + x4) / 2, (y2 + y4) / 2);
        return new Point2D.Double((pt01.getX() + pt02.getX())/2, (pt01.getY() + pt02.getY()) / 2);
    }
}
