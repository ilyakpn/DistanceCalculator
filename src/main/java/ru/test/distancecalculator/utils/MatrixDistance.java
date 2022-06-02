package ru.test.distancecalculator.utils;

import lombok.Getter;
import lombok.Setter;
import ru.test.distancecalculator.models.Distance;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "matrixdistance")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
public class MatrixDistance {

    @XmlElement(name = "distanceobject")
    private List<Distance> distanceList;

    public MatrixDistance(List<Distance> distanceList) {
        this.distanceList = distanceList;
    }

    public MatrixDistance() {

    }
}
