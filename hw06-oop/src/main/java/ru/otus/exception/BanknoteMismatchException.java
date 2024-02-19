package ru.otus.exception;

import ru.otus.Denomination;

public class BanknoteMismatchException extends RuntimeException {
    public BanknoteMismatchException(Denomination current, Denomination load) {
        super(String.format("Unable to load %s into cell with %s", current.getTitle(), load.getTitle()));
    }
}
