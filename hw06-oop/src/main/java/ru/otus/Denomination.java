package ru.otus;

public enum Denomination {

    TEN("Десять"),
    FIFTY("Пятьдесят"),
    HUNDRED("Сто"),
    TWO_HUNDRED("Двести"),
    FIVE_HUNDRED("Пятьсот"),
    THOUSAND("Тысяча"),
    TWO_THOUSAND("Две тысячи"),
    FIVE_THOUSAND("Пять тысяч");

    private String title;

    Denomination(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
