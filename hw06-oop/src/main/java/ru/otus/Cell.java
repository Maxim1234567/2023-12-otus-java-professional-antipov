package ru.otus;

import java.util.List;
import java.util.stream.Stream;

public class Cell {

    private Banknote banknote;

    private List<Banknote> banknotes;

    public Cell(Banknote banknote, int size) {
        this.banknote = banknote;

        banknotes = Stream.generate(() -> this.banknote)
                .limit(size)
                .toList();
    }

    public List<Banknote> getBanknotes(int size) {

        if (size > banknotes.size())
            throw new NotBanknoteException(banknote.denomination(), size - banknotes.size());

        return Stream.generate(this::getBanknote)
                .limit(size)
                .toList();
    }

    public Banknote getBanknote() {
        return banknotes.removeFirst();
    }
}
