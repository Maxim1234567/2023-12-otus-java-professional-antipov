package ru.otus;

import ru.otus.exception.NotBanknoteException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cell {

    private Denomination denomination;

    private List<Banknote> banknotes;

    public Cell(Denomination denomination, int size) {
        this.denomination = denomination;

        banknotes = Stream.generate(() -> new Banknote(denomination, denomination.getValue()))
                .limit(size)
                .collect(Collectors.toList());
    }

    public List<Banknote> getBanknotes(int size) {

        if (size > banknotes.size())
            throw new NotBanknoteException(denomination, size - banknotes.size());

        return Stream.generate(this::getBanknote)
                .limit(size)
                .toList();
    }

    public void load(List<Banknote> banknotes) {
        this.banknotes.addAll(banknotes);
    }

    public Banknote getBanknote() {
        return banknotes.removeFirst();
    }

    public boolean isEmpty() {
        return banknotes.isEmpty();
    }

    public int getCashBalance() {
        return denomination.getValue() * banknotes.size();
    }
}
