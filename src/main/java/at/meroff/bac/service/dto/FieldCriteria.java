package at.meroff.bac.service.dto;

import java.io.Serializable;
import at.meroff.bac.domain.enumeration.LayoutType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Field entity. This class is used in FieldResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /fields?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FieldCriteria implements Serializable {
    /**
     * Class for filtering LayoutType
     */
    public static class LayoutTypeFilter extends Filter<LayoutType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter description;

    private LayoutTypeFilter layoutType;

    private LongFilter cardId;

    private LongFilter connectionId;

    public FieldCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LayoutTypeFilter getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(LayoutTypeFilter layoutType) {
        this.layoutType = layoutType;
    }

    public LongFilter getCardId() {
        return cardId;
    }

    public void setCardId(LongFilter cardId) {
        this.cardId = cardId;
    }

    public LongFilter getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(LongFilter connectionId) {
        this.connectionId = connectionId;
    }

    @Override
    public String toString() {
        return "FieldCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (layoutType != null ? "layoutType=" + layoutType + ", " : "") +
                (cardId != null ? "cardId=" + cardId + ", " : "") +
                (connectionId != null ? "connectionId=" + connectionId + ", " : "") +
            "}";
    }

}
