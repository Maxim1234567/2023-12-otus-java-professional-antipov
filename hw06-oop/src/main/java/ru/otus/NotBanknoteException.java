package ru.otus;

public class NotBanknoteException extends RuntimeException {

    public NotBanknoteException(Denomination denomination, int diff) {
        super(String.format("That is how many %d banknotes are missing %s", diff, denomination.getTitle()));
    }

}
