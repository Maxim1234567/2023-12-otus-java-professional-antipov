package ru.otus;

public enum Denomination {

    TEN("Ten", 10),
    FIFTY("Fifty", 50),
    HUNDRED("Hundred", 100),
    TWO_HUNDRED("Two hundred", 200),
    FIVE_HUNDRED("Five Hundred", 500),
    THOUSAND("Thousand", 1000),
    TWO_THOUSAND("Two Thousand", 2000),
    FIVE_THOUSAND("Five Thousand", 5000);

    private String title;

    private int value;

    Denomination(String title, int value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public int getValue() {
        return value;
    }
}
