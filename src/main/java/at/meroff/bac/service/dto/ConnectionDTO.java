package at.meroff.bac.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Connection entity.
 */
public class ConnectionDTO implements Serializable {

    private Long id;

    private Integer uuid;

    private String name;

    private Integer endPoint1Uuid;

    private Double endPoint1X;

    private Double endPoint1Y;

    private Double endPoint1Angle;

    private Boolean directed1;

    private Integer endPoint2Uuid;

    private Double endPoint2X;

    private Double endPoint2Y;

    private Double endPoint2Angle;

    private Boolean directed2;

    private Long fieldId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEndPoint1Uuid() {
        return endPoint1Uuid;
    }

    public void setEndPoint1Uuid(Integer endPoint1Uuid) {
        this.endPoint1Uuid = endPoint1Uuid;
    }

    public Double getEndPoint1X() {
        return endPoint1X;
    }

    public void setEndPoint1X(Double endPoint1X) {
        this.endPoint1X = endPoint1X;
    }

    public Double getEndPoint1Y() {
        return endPoint1Y;
    }

    public void setEndPoint1Y(Double endPoint1Y) {
        this.endPoint1Y = endPoint1Y;
    }

    public Double getEndPoint1Angle() {
        return endPoint1Angle;
    }

    public void setEndPoint1Angle(Double endPoint1Angle) {
        this.endPoint1Angle = endPoint1Angle;
    }

    public Boolean isDirected1() {
        return directed1;
    }

    public void setDirected1(Boolean directed1) {
        this.directed1 = directed1;
    }

    public Integer getEndPoint2Uuid() {
        return endPoint2Uuid;
    }

    public void setEndPoint2Uuid(Integer endPoint2Uuid) {
        this.endPoint2Uuid = endPoint2Uuid;
    }

    public Double getEndPoint2X() {
        return endPoint2X;
    }

    public void setEndPoint2X(Double endPoint2X) {
        this.endPoint2X = endPoint2X;
    }

    public Double getEndPoint2Y() {
        return endPoint2Y;
    }

    public void setEndPoint2Y(Double endPoint2Y) {
        this.endPoint2Y = endPoint2Y;
    }

    public Double getEndPoint2Angle() {
        return endPoint2Angle;
    }

    public void setEndPoint2Angle(Double endPoint2Angle) {
        this.endPoint2Angle = endPoint2Angle;
    }

    public Boolean isDirected2() {
        return directed2;
    }

    public void setDirected2(Boolean directed2) {
        this.directed2 = directed2;
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

        ConnectionDTO connectionDTO = (ConnectionDTO) o;
        if(connectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), connectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConnectionDTO{" +
            "id=" + getId() +
            ", uuid=" + getUuid() +
            ", name='" + getName() + "'" +
            ", endPoint1Uuid=" + getEndPoint1Uuid() +
            ", endPoint1X=" + getEndPoint1X() +
            ", endPoint1Y=" + getEndPoint1Y() +
            ", endPoint1Angle=" + getEndPoint1Angle() +
            ", directed1='" + isDirected1() + "'" +
            ", endPoint2Uuid=" + getEndPoint2Uuid() +
            ", endPoint2X=" + getEndPoint2X() +
            ", endPoint2Y=" + getEndPoint2Y() +
            ", endPoint2Angle=" + getEndPoint2Angle() +
            ", directed2='" + isDirected2() + "'" +
            "}";
    }
}
