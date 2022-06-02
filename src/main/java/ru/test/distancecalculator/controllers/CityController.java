package ru.test.distancecalculator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.test.distancecalculator.models.City;
import ru.test.distancecalculator.repository.CityRepository;

@Controller
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @GetMapping(value="/distanceCalculator/cities")
    public String cities(Model model) {
        Iterable<City> cities;

        cities = cityRepository.findAll();

        model.addAttribute("cities", cities);

        return "cities";
    }

}
