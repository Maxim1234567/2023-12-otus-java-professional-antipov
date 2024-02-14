package ru.otus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CalculateIssuedBanknotes {

    private final Set<Banknote> banknotes;

    public CalculateIssuedBanknotes(List<Banknote> banknotes) {
        this.banknotes = new TreeSet<>(Comparator.reverseOrder());
        this.banknotes.addAll(banknotes);
    }

    public Map<Banknote, Integer> calculate(int sum) {
        Map<Banknote, Integer> requested = new HashMap<>();

        int remainder = sum;
        for (Banknote banknote: banknotes) {
            if (remainder / banknote.value() != 0) {
                requested.put(banknote, remainder / banknote.value());
                remainder -= requested.get(banknote) * banknote.value();
            }
        }

        if (remainder != 0)
            throw new UnableIssueAmountException(sum);

        return requested;
    }
}
