package ru.test.distancecalculator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.test.distancecalculator.models.City;
import ru.test.distancecalculator.models.Distance;
import ru.test.distancecalculator.repository.CityRepository;
import ru.test.distancecalculator.repository.DistanceRepository;
import ru.test.distancecalculator.utils.MatrixDistance;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UploadDataController {

    @Autowired
    private DistanceRepository distanceRepository;

    @Autowired
    private CityRepository cityRepository;

    @GetMapping(value="/distanceCalculator/uploadData")
    public String uploadData() {

        return "upload-data";
    }

    @PostMapping(value="/distanceCalculator/uploadData")
    public ResponseEntity<String> loadFile(@RequestParam("file") MultipartFile multipartFile) {

        try {

            JAXBContext context = JAXBContext.newInstance(MatrixDistance.class);
            Unmarshaller um = context.createUnmarshaller();
            MatrixDistance md = (MatrixDistance) um.unmarshal(multipartFile.getInputStream());

            Set<City> citiesSet = new HashSet<>();

            // Получаем distinct городов из файла
            md.getDistanceList().forEach(d -> citiesSet.add(d.getFromCity()));
            md.getDistanceList().forEach(d -> citiesSet.add(d.getToCity()));

            // Получаем список городов, к-ых нет в базе
            List<City> citiesForInsert = citiesSet.stream()
                    .filter(c -> !cityRepository.existsByName(c.getName()))
                    .collect(Collectors.toList());

            // Вставляем города в базу (таблица city)
            cityRepository.saveAll(citiesForInsert);

            // Проставляем id для полей класса Distance fromCity и toCity
            for (Distance d : md.getDistanceList()) {
                Optional<City> cityDb1 = cityRepository.findByName(d.getFromCity().getName());
                cityDb1.ifPresent(city -> d.getFromCity().setId(city.getId()));

                Optional<City> cityDb2 = cityRepository.findByName(d.getToCity().getName());
                cityDb2.ifPresent(city -> d.getToCity().setId(city.getId()));
            }

            // Получаем список Distance, к-ых нет в базе
            List<Distance> distancesForInsert = md.getDistanceList().stream()
                    .filter(d -> !distanceRepository.existsByFromCityAndToCity(d.getFromCity(), d.getToCity()))
                    .collect(Collectors.toList());

            // Вставляем список Distance в базу (таблица distance)
            if (distancesForInsert.size() > 0 ) {

                distanceRepository.saveAll(distancesForInsert);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (JAXBException | IOException e) {

            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
