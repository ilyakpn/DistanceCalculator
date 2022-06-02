package ru.test.distancecalculator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.test.distancecalculator.models.City;
import ru.test.distancecalculator.models.Distance;
import ru.test.distancecalculator.repository.CityRepository;
import ru.test.distancecalculator.repository.DistanceRepository;
import ru.test.distancecalculator.utils.CrowFlightDistance;
import ru.test.distancecalculator.utils.TypesOfCalc;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class CalcDistanceController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistanceRepository distanceRepository;

    @GetMapping(value="/distanceCalculator")
    public String distanceCalculator(Model model) {

        Iterable<City> cities;

        cities = cityRepository.findAll();

        model.addAttribute("cities", cities);
        model.addAttribute("typesCalc", Arrays.asList(TypesOfCalc.values()));

        return "distance-calculator";
    }

    @PostMapping(value="/distanceCalculator")
    public String calculation(@RequestParam int fromCity, @RequestParam int toCity, Model model) {

        return "distance-calculator";
    }

    @GetMapping("/distanceCalculator/calculationResult")
    public ModelAndView calculationResult(String typeOfCalc, long fromCityId, long toCityId) {
        ModelAndView modelAndView = new ModelAndView("calculation-result :: calcResultFragment");

        try {
            String message = "City with id = %d not found";

            City fromCity = cityRepository.findById(fromCityId).orElseThrow(() -> new EntityNotFoundException(String.format(message, fromCityId)));
            City toCity = cityRepository.findById(toCityId).orElseThrow(() -> new EntityNotFoundException(String.format(message, toCityId)));

            if (typeOfCalc.equals(TypesOfCalc.CROWFLIGHT.getName()) || typeOfCalc.equals(TypesOfCalc.ALL.getName())) {

                CrowFlightDistance crowFlightDistance = new CrowFlightDistance(fromCity, toCity);

                double d = crowFlightDistance.calcDistance();

                modelAndView.addObject("crowflightResult", String.format("Расстояние по прямой = %.3f км", d));
            }

            if (typeOfCalc.equals(TypesOfCalc.DISTANCE_MATRIX.getName()) || typeOfCalc.equals(TypesOfCalc.ALL.getName())) {

                Optional<Distance> optionalDistance = distanceRepository.findByFromCityAndToCity(fromCity, toCity);

                try {
                    if (optionalDistance.isPresent()) {

                        Distance distance = optionalDistance.get();

                        modelAndView.addObject("distanceMatrix", String.format("Матрица расстояний = %.3f км", distance.getDistance()));

                    } else {
                        throw new EntityNotFoundException("Distance cannot be calculated, because it is not in the distance table!");
                    }
                } catch (EntityNotFoundException e) {

                    modelAndView.addObject("exception", "Расстояние не может быть рассчитано, потому что нет данных в таблице расстояний!");

                }
            }

        } catch (EntityNotFoundException e) {
            modelAndView.addObject("exception1", "Расстояние не может быть рассчитано, потому что нет данных в таблице городов!");
        }

        return modelAndView;
    }
}
