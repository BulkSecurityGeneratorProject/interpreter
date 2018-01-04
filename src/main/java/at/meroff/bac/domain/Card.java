package at.meroff.bac.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.javafx.geom.Vec2d;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Card> tasks = new HashSet<>();

    @ManyToOne
    private Card subject;

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

    public Set<Card> getTasks() {
        return tasks;
    }

    public Card tasks(Set<Card> cards) {
        this.tasks = cards;
        return this;
    }

    public Card addTasks(Card card) {
        this.tasks.add(card);
        card.setSubject(this);
        return this;
    }

    public Card removeTasks(Card card) {
        this.tasks.remove(card);
        card.setSubject(null);
        return this;
    }

    public void setTasks(Set<Card> cards) {
        this.tasks = cards;
    }

    public Card getSubject() {
        return subject;
    }

    public Card subject(Card card) {
        this.subject = card;
        return this;
    }

    public void setSubject(Card card) {
        this.subject = card;
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
    public static double getDistance(Card source, Card target) {
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

    /**
     * Calculate the max cosine similarity based on two cards. The dimension of the first card is used as a reference dimension.
     * @param subject subject as base for calculation
     * @param sourceTask task to determine the cosine similarity
     * @return max cosine similarity
     */
    public static double getMaxCosineSimilarity(Card subject, Card sourceTask) {

        double x = Card.getBiggestLength(subject) / 2;
        double y = Card.getDistance(subject, sourceTask);
        double alpha = Math.asin(x/y);
        return Math.cos(alpha);

    }

    /**
     * Returns the biggest side length for a given card
     * @param card Card for Calcualation
     * @return biggest side length
     */
    private static Double getBiggestLength(Card card) {
        double length = 0;
        double l1 = card.getPoint1().distance(card.getPoint2());
        if (l1 > length) length = l1;
        double l2 = card.getPoint2().distance(card.getPoint3());
        if (l2 > length) length = l2;
        double l3 = card.getPoint3().distance(card.getPoint4());
        if (l3 > length) length = l3;
        double l4 = card.getPoint4().distance(card.getPoint1());
        if (l4 > length) length = l4;

        return length;
    }

    private Point2D getPoint1() {
        return new Point2D.Double(x1,y1);
    }

    private Point2D getPoint2() {
        return new Point2D.Double(x2,y2);
    }

    private Point2D getPoint3() {
        return new Point2D.Double(x3,y3);
    }

    private Point2D getPoint4() {
        return new Point2D.Double(x4,y4);
    }

    /**
     * Returns the vector between two cards
     * @param source source card for calculation
     * @param target target card for calculation
     * @return vector between two cards
     */
    public static Vec2d getVector(Card source, Card target) {
        return new Vec2d(target.getCenter().x - source.getCenter().x, target.getCenter().y - source.getCenter().y);
    }
}
