package ru.otus.fabrica;

import ru.otus.CalculateIssuedBanknotesImpl;
import ru.otus.Denomination;
import ru.otus.service.CalculateIssuedBanknotes;

import java.util.List;

public class Fabrica {
    public static CalculateIssuedBanknotes createCalculateIssuedBanknotes(List<Denomination> denominations) {
        return new CalculateIssuedBanknotesImpl(denominations);
    }
}
