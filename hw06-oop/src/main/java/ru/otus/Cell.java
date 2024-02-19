package ru.otus;

import ru.otus.exception.BanknoteMismatchException;
import ru.otus.exception.NotBanknoteException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Cell {

    private Denomination denomination;

    private List<Banknote> banknotes;

    public Cell(Denomination denomination) {
        this.denomination = denomination;
        banknotes = new ArrayList<>();
    }

    public List<Banknote> getBanknotes(int size) {

        if (size > banknotes.size())
            throw new NotBanknoteException(denomination, size - banknotes.size());

        return Stream.generate(this::getBanknote)
                .limit(size)
                .toList();
    }

    public void load(List<Banknote> banknotes) {
        checkMismatchBanknote(banknotes);

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

    private void checkMismatchBanknote(List<Banknote> banknotes) {
        banknotes.stream()
                .filter(b -> !b.denomination().equals(denomination))
                .findAny()
                .ifPresent((b) -> {throw new BanknoteMismatchException(denomination, b.denomination());});
    }
}
