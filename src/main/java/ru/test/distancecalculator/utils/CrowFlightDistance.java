package ru.test.distancecalculator.utils;

import lombok.*;
import ru.test.distancecalculator.models.City;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@ToString
public class CrowFlightDistance {
    // Формула для расчета взята с сайта https://congyuzhou.medium.com/%D1%80%D0%B0%D1%81%D1%81%D1%82%D0%BE%D1%8F%D0%BD%D0%B8%D0%B5-%D0%BC%D0%B5%D0%B6%D0%B4%D1%83-%D0%B4%D0%B2%D1%83%D0%BC%D1%8F-%D1%82%D0%BE%D1%87%D0%BA%D0%B0%D0%BC%D0%B8-%D0%BD%D0%B0-%D0%BF%D0%BE%D0%B2%D0%B5%D1%80%D1%85%D0%BD%D0%BE%D1%81%D1%82%D0%B8-%D0%B7%D0%B5%D0%BC%D0%BB%D0%B8-a398352bfbde

    // Радиус земли в километрах
    private final float EARTH_RADIUS = 6371.0088f;

    private City fromCity;
    private City toCity;

    public CrowFlightDistance(City fromCity, City toCity) {
        this.fromCity = fromCity;
        this.toCity = toCity;
    }

    public double calcDistance() {
        if (fromCity == null || toCity == null) {
            return 0;
        }

        double latitude1 = Math.toRadians(fromCity.getLatitude());
        double longitude1 = Math.toRadians(fromCity.getLongitude());

        double latitude2 = Math.toRadians(toCity.getLatitude());
        double longitude2 = Math.toRadians(toCity.getLongitude());

        double fi = (latitude2 - latitude1)/2;
        double lambda = (longitude2 - longitude1)/2;

        double a = Math.sqrt(Math.pow(Math.sin(fi), 2) + Math.cos(latitude1)*Math.cos(latitude2)*Math.pow(Math.sin(lambda), 2));

        double d = 2*EARTH_RADIUS*Math.atan(a);

        BigDecimal result = new BigDecimal(d);
        result = result.setScale(3, RoundingMode.DOWN);

        return result.doubleValue();
    }
}
