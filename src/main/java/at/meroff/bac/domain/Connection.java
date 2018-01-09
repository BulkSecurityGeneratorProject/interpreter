package at.meroff.bac.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Connection.
 */
@Entity
@Table(name = "connection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Connection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private Integer uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "end_point_1_uuid")
    private Integer endPoint1Uuid;

    @Column(name = "end_point_1_x")
    private Double endPoint1X;

    @Column(name = "end_point_1_y")
    private Double endPoint1Y;

    @Column(name = "end_point_1_angle")
    private Double endPoint1Angle;

    @Column(name = "directed_1")
    private Boolean directed1;

    @Column(name = "end_point_2_uuid")
    private Integer endPoint2Uuid;

    @Column(name = "end_point_2_x")
    private Double endPoint2X;

    @Column(name = "end_point_2_y")
    private Double endPoint2Y;

    @Column(name = "end_point_2_angle")
    private Double endPoint2Angle;

    @Column(name = "directed_2")
    private Boolean directed2;

    @ManyToOne
    private Field field;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUuid() {
        return uuid;
    }

    public Connection uuid(Integer uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public Connection name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEndPoint1Uuid() {
        return endPoint1Uuid;
    }

    public Connection endPoint1Uuid(Integer endPoint1Uuid) {
        this.endPoint1Uuid = endPoint1Uuid;
        return this;
    }

    public void setEndPoint1Uuid(Integer endPoint1Uuid) {
        this.endPoint1Uuid = endPoint1Uuid;
    }

    public Double getEndPoint1X() {
        return endPoint1X;
    }

    public Connection endPoint1X(Double endPoint1X) {
        this.endPoint1X = endPoint1X;
        return this;
    }

    public void setEndPoint1X(Double endPoint1X) {
        this.endPoint1X = endPoint1X;
    }

    public Double getEndPoint1Y() {
        return endPoint1Y;
    }

    public Connection endPoint1Y(Double endPoint1Y) {
        this.endPoint1Y = endPoint1Y;
        return this;
    }

    public void setEndPoint1Y(Double endPoint1Y) {
        this.endPoint1Y = endPoint1Y;
    }

    public Double getEndPoint1Angle() {
        return endPoint1Angle;
    }

    public Connection endPoint1Angle(Double endPoint1Angle) {
        this.endPoint1Angle = endPoint1Angle;
        return this;
    }

    public void setEndPoint1Angle(Double endPoint1Angle) {
        this.endPoint1Angle = endPoint1Angle;
    }

    public Boolean isDirected1() {
        return directed1;
    }

    public Connection directed1(Boolean directed1) {
        this.directed1 = directed1;
        return this;
    }

    public void setDirected1(Boolean directed1) {
        this.directed1 = directed1;
    }

    public Integer getEndPoint2Uuid() {
        return endPoint2Uuid;
    }

    public Connection endPoint2Uuid(Integer endPoint2Uuid) {
        this.endPoint2Uuid = endPoint2Uuid;
        return this;
    }

    public void setEndPoint2Uuid(Integer endPoint2Uuid) {
        this.endPoint2Uuid = endPoint2Uuid;
    }

    public Double getEndPoint2X() {
        return endPoint2X;
    }

    public Connection endPoint2X(Double endPoint2X) {
        this.endPoint2X = endPoint2X;
        return this;
    }

    public void setEndPoint2X(Double endPoint2X) {
        this.endPoint2X = endPoint2X;
    }

    public Double getEndPoint2Y() {
        return endPoint2Y;
    }

    public Connection endPoint2Y(Double endPoint2Y) {
        this.endPoint2Y = endPoint2Y;
        return this;
    }

    public void setEndPoint2Y(Double endPoint2Y) {
        this.endPoint2Y = endPoint2Y;
    }

    public Double getEndPoint2Angle() {
        return endPoint2Angle;
    }

    public Connection endPoint2Angle(Double endPoint2Angle) {
        this.endPoint2Angle = endPoint2Angle;
        return this;
    }

    public void setEndPoint2Angle(Double endPoint2Angle) {
        this.endPoint2Angle = endPoint2Angle;
    }

    public Boolean isDirected2() {
        return directed2;
    }

    public Connection directed2(Boolean directed2) {
        this.directed2 = directed2;
        return this;
    }

    public void setDirected2(Boolean directed2) {
        this.directed2 = directed2;
    }

    public Field getField() {
        return field;
    }

    public Connection field(Field field) {
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
        Connection connection = (Connection) o;
        if (connection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), connection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Connection{" +
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
