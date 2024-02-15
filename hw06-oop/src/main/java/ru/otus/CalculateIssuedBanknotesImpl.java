package ru.otus;

import ru.otus.exception.UnableIssueAmountException;
import ru.otus.service.CalculateIssuedBanknotes;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CalculateIssuedBanknotesImpl implements CalculateIssuedBanknotes {

    private final Set<Denomination> banknotes;

    public CalculateIssuedBanknotesImpl(List<Denomination> banknotes) {
        this.banknotes = new TreeSet<>(Comparator.reverseOrder());
        this.banknotes.addAll(banknotes);
    }

    public Map<Denomination, Integer> calculate(int sum) {
        Map<Denomination, Integer> requested = new HashMap<>();

        int remainder = sum;
        for (Denomination denomination: banknotes) {
            if (remainder / denomination.getValue() != 0) {
                requested.put(denomination, remainder / denomination.getValue());
                remainder -= requested.get(denomination) * denomination.getValue();
            }
        }

        if (remainder != 0)
            throw new UnableIssueAmountException(sum);

        return requested;
    }
}
