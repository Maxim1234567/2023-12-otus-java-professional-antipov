package ru.otus;

public record Banknote(Denomination denomination) implements Comparable<Banknote> {

    @Override
    public int compareTo(Banknote o) {
        return denomination.getValue() - o.denomination.getValue();
    }
}
