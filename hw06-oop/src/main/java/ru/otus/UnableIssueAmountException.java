package ru.otus;

public class UnableIssueAmountException extends RuntimeException {

    public UnableIssueAmountException(int sum) {
        super(String.format("Amount %d can't be issued", sum));
    }

}
