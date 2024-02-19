package ru.otus.service;

import ru.otus.Denomination;

import java.util.Map;

public interface CalculateIssuedBanknotes {
    Map<Denomination, Integer> calculate(int sum);
}
