package at.meroff.bac.service.dto;

import java.io.Serializable;
import at.meroff.bac.domain.enumeration.CardType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Card entity. This class is used in CardResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /cards?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CardCriteria implements Serializable {
    /**
     * Class for filtering CardType
     */
    public static class CardTypeFilter extends Filter<CardType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter cardId;

    private StringFilter description;

    private CardTypeFilter cardType;

    private DoubleFilter x1;

    private DoubleFilter y1;

    private DoubleFilter x2;

    private DoubleFilter y2;

    private DoubleFilter x3;

    private DoubleFilter y3;

    private DoubleFilter x4;

    private DoubleFilter y4;

    private LongFilter fieldId;

    public CardCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getCardId() {
        return cardId;
    }

    public void setCardId(IntegerFilter cardId) {
        this.cardId = cardId;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public CardTypeFilter getCardType() {
        return cardType;
    }

    public void setCardType(CardTypeFilter cardType) {
        this.cardType = cardType;
    }

    public DoubleFilter getx1() {
        return x1;
    }

    public void setx1(DoubleFilter x1) {
        this.x1 = x1;
    }

    public DoubleFilter gety1() {
        return y1;
    }

    public void sety1(DoubleFilter y1) {
        this.y1 = y1;
    }

    public DoubleFilter getx2() {
        return x2;
    }

    public void setx2(DoubleFilter x2) {
        this.x2 = x2;
    }

    public DoubleFilter gety2() {
        return y2;
    }

    public void sety2(DoubleFilter y2) {
        this.y2 = y2;
    }

    public DoubleFilter getx3() {
        return x3;
    }

    public void setx3(DoubleFilter x3) {
        this.x3 = x3;
    }

    public DoubleFilter gety3() {
        return y3;
    }

    public void sety3(DoubleFilter y3) {
        this.y3 = y3;
    }

    public DoubleFilter getx4() {
        return x4;
    }

    public void setx4(DoubleFilter x4) {
        this.x4 = x4;
    }

    public DoubleFilter gety4() {
        return y4;
    }

    public void sety4(DoubleFilter y4) {
        this.y4 = y4;
    }

    public LongFilter getFieldId() {
        return fieldId;
    }

    public void setFieldId(LongFilter fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public String toString() {
        return "CardCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cardId != null ? "cardId=" + cardId + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (cardType != null ? "cardType=" + cardType + ", " : "") +
                (x1 != null ? "x1=" + x1 + ", " : "") +
                (y1 != null ? "y1=" + y1 + ", " : "") +
                (x2 != null ? "x2=" + x2 + ", " : "") +
                (y2 != null ? "y2=" + y2 + ", " : "") +
                (x3 != null ? "x3=" + x3 + ", " : "") +
                (y3 != null ? "y3=" + y3 + ", " : "") +
                (x4 != null ? "x4=" + x4 + ", " : "") +
                (y4 != null ? "y4=" + y4 + ", " : "") +
                (fieldId != null ? "fieldId=" + fieldId + ", " : "") +
            "}";
    }

}
