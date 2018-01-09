package at.meroff.bac.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Connection entity. This class is used in ConnectionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /connections?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConnectionCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter uuid;

    private StringFilter name;

    private IntegerFilter endPoint1Uuid;

    private DoubleFilter endPoint1X;

    private DoubleFilter endPoint1Y;

    private DoubleFilter endPoint1Angle;

    private BooleanFilter directed1;

    private IntegerFilter endPoint2Uuid;

    private DoubleFilter endPoint2X;

    private DoubleFilter endPoint2Y;

    private DoubleFilter endPoint2Angle;

    private BooleanFilter directed2;

    private LongFilter fieldId;

    public ConnectionCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getUuid() {
        return uuid;
    }

    public void setUuid(IntegerFilter uuid) {
        this.uuid = uuid;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getEndPoint1Uuid() {
        return endPoint1Uuid;
    }

    public void setEndPoint1Uuid(IntegerFilter endPoint1Uuid) {
        this.endPoint1Uuid = endPoint1Uuid;
    }

    public DoubleFilter getEndPoint1X() {
        return endPoint1X;
    }

    public void setEndPoint1X(DoubleFilter endPoint1X) {
        this.endPoint1X = endPoint1X;
    }

    public DoubleFilter getEndPoint1Y() {
        return endPoint1Y;
    }

    public void setEndPoint1Y(DoubleFilter endPoint1Y) {
        this.endPoint1Y = endPoint1Y;
    }

    public DoubleFilter getEndPoint1Angle() {
        return endPoint1Angle;
    }

    public void setEndPoint1Angle(DoubleFilter endPoint1Angle) {
        this.endPoint1Angle = endPoint1Angle;
    }

    public BooleanFilter getDirected1() {
        return directed1;
    }

    public void setDirected1(BooleanFilter directed1) {
        this.directed1 = directed1;
    }

    public IntegerFilter getEndPoint2Uuid() {
        return endPoint2Uuid;
    }

    public void setEndPoint2Uuid(IntegerFilter endPoint2Uuid) {
        this.endPoint2Uuid = endPoint2Uuid;
    }

    public DoubleFilter getEndPoint2X() {
        return endPoint2X;
    }

    public void setEndPoint2X(DoubleFilter endPoint2X) {
        this.endPoint2X = endPoint2X;
    }

    public DoubleFilter getEndPoint2Y() {
        return endPoint2Y;
    }

    public void setEndPoint2Y(DoubleFilter endPoint2Y) {
        this.endPoint2Y = endPoint2Y;
    }

    public DoubleFilter getEndPoint2Angle() {
        return endPoint2Angle;
    }

    public void setEndPoint2Angle(DoubleFilter endPoint2Angle) {
        this.endPoint2Angle = endPoint2Angle;
    }

    public BooleanFilter getDirected2() {
        return directed2;
    }

    public void setDirected2(BooleanFilter directed2) {
        this.directed2 = directed2;
    }

    public LongFilter getFieldId() {
        return fieldId;
    }

    public void setFieldId(LongFilter fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public String toString() {
        return "ConnectionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uuid != null ? "uuid=" + uuid + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (endPoint1Uuid != null ? "endPoint1Uuid=" + endPoint1Uuid + ", " : "") +
                (endPoint1X != null ? "endPoint1X=" + endPoint1X + ", " : "") +
                (endPoint1Y != null ? "endPoint1Y=" + endPoint1Y + ", " : "") +
                (endPoint1Angle != null ? "endPoint1Angle=" + endPoint1Angle + ", " : "") +
                (directed1 != null ? "directed1=" + directed1 + ", " : "") +
                (endPoint2Uuid != null ? "endPoint2Uuid=" + endPoint2Uuid + ", " : "") +
                (endPoint2X != null ? "endPoint2X=" + endPoint2X + ", " : "") +
                (endPoint2Y != null ? "endPoint2Y=" + endPoint2Y + ", " : "") +
                (endPoint2Angle != null ? "endPoint2Angle=" + endPoint2Angle + ", " : "") +
                (directed2 != null ? "directed2=" + directed2 + ", " : "") +
                (fieldId != null ? "fieldId=" + fieldId + ", " : "") +
            "}";
    }

}
