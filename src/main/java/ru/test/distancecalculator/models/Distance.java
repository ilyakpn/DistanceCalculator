package ru.test.distancecalculator.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@Entity
@Table(name = "Distance")
public class Distance {

    @XmlTransient
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @XmlElement(name = "fromcity")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="from_city_id")
    private City fromCity;

    @XmlElement(name = "tocity")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="to_city_id")
    private City toCity;

    @XmlElement(name = "distance")
    @Column(name = "distance")
    private double distance;

    public Distance() {

    }
}
