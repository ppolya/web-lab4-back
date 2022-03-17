package com.lab.weblab4back.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "coordinates")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;
    private double x_value;
    private double y_value;
    private double r_value;
    private String status;
    private String time;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @ToString.Exclude
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Result(double x_value, double y_value, double r_value, User user) {
        this.x_value = x_value;
        this.y_value = y_value;
        this.r_value = r_value;
        this.user = user;
        this.time = generateTime();
        this.status = generateStatus();
    }

    public String generateStatus() {
        status = checkQuarters() ? "точка попала" : "точка не попала";
        return status;
    }

    public String generateTime() {
        String timePattern = "HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timePattern);
        time = LocalDateTime.now().format(formatter);
        return time;
    }

    private boolean checkQuarters() {
        return x_value <= 0 && y_value >= 0 && x_value * x_value + y_value * y_value <= r_value * r_value / 4
                || x_value <= 0 && y_value <= 0 && x_value >= -r_value && y_value >= -r_value / 2
                || x_value >= 0 && y_value <= 0 && y_value >= x_value - r_value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Result result = (Result) o;
        return id != null && Objects.equals(id, result.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
