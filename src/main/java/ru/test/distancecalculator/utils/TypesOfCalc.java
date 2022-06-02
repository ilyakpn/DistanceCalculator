package ru.test.distancecalculator.utils;

import lombok.Getter;

@Getter
public enum TypesOfCalc {
    CROWFLIGHT("Расстояние по прямой"),
    DISTANCE_MATRIX("Матрица расстояний"),
    ALL("Все");

    private final String name;

    TypesOfCalc(String name) {
        this.name = name;
    }
}
