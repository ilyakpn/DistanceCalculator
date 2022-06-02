package ru.test.distancecalculator.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@Entity
@Table(name = "city")
public class City {

    @XmlTransient
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @XmlElement(name = "cityname")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @XmlElement(name = "latitude")
    @Column(name = "latitude")
    private double latitude;

    @XmlElement(name = "longitude")
    @Column(name = "longitude")
    private double longitude;

    @XmlTransient
    @OneToMany(mappedBy = "fromCity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Distance> fromCities = new ArrayList<>();

    @XmlTransient
    @OneToMany(mappedBy = "toCity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Distance> toCities = new ArrayList<>();

    public City() {

    }

    public City(String name, float latitude, float longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return getName().equals(city.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
