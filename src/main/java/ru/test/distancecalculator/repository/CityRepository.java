package ru.test.distancecalculator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.test.distancecalculator.models.City;

import java.util.Optional;

public interface CityRepository extends CrudRepository<City, Long> {
    boolean existsByName(String name);
    Optional<City> findByName(String name);
}
