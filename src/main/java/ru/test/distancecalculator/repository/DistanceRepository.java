package ru.test.distancecalculator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.test.distancecalculator.models.City;
import ru.test.distancecalculator.models.Distance;

import java.util.Optional;

public interface DistanceRepository extends CrudRepository<Distance, Long> {
    Optional<Distance> findByFromCityAndToCity(City fromCity, City toCity);
    boolean existsByFromCityAndToCity(City fromCity, City toCity);
}
