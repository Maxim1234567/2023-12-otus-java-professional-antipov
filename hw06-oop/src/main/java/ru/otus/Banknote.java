package ru.otus;

public record Banknote(Denomination denomination, int value) implements Comparable<Banknote> {

    @Override
    public int compareTo(Banknote o) {
        return this.value - o.value;
    }
}
