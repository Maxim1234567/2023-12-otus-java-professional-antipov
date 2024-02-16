package ru.otus;

import ru.otus.service.CalculateIssuedBanknotes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.otus.fabrica.Fabrica.createCalculateIssuedBanknotes;

public class Atm {
    private final Map<Denomination, Cell> cells;

    public Atm() {
        this.cells = new HashMap<>();
    }

    public void loadBanknotes(List<Banknote> banknotes) {
        Map<Denomination, List<Banknote>> groupByBanknote = banknotes.stream()
                .collect(Collectors.groupingBy(Banknote::denomination));

        groupByBanknote
                .forEach((key, value) -> {
                    cells.put(key, new Cell(key));
                    cells.get(key).load(value);
                });
    }

    public List<Banknote> getBanknotes(int sum) {
        List<Denomination> availableDenominations = cells.entrySet().stream()
                .filter(e -> !e.getValue().isEmpty())
                .map(Map.Entry::getKey)
                .toList();

        CalculateIssuedBanknotes calculateIssuedBanknotes = createCalculateIssuedBanknotes(availableDenominations);
        Map<Denomination, Integer> banknotes = calculateIssuedBanknotes.calculate(sum);

        return banknotes.entrySet().stream()
                .map(e -> cells.get(e.getKey()).getBanknotes(e.getValue()))
                .flatMap(List::stream)
                .toList();
    }

    public int getCashBalance() {
        return cells.values().stream()
                .map(Cell::getCashBalance)
                .reduce(0, Integer::sum);
    }
}
