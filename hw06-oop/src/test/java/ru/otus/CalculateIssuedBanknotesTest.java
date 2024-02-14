package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.Denomination.FIFTY;
import static ru.otus.Denomination.FIVE_HUNDRED;
import static ru.otus.Denomination.FIVE_THOUSAND;
import static ru.otus.Denomination.HUNDRED;
import static ru.otus.Denomination.TEN;
import static ru.otus.Denomination.THOUSAND;
import static ru.otus.Denomination.TWO_HUNDRED;
import static ru.otus.Denomination.TWO_THOUSAND;

public class CalculateIssuedBanknotesTest {

    private CalculateIssuedBanknotes calculateIssuedBanknotes;

    @BeforeEach
    public void setUp() {
        calculateIssuedBanknotes = new CalculateIssuedBanknotes(
                List.of(
                        new Banknote(TEN, 10),
                        new Banknote(FIFTY, 50),
                        new Banknote(HUNDRED, 100),
                        new Banknote(TWO_HUNDRED, 200),
                        new Banknote(FIVE_HUNDRED, 500),
                        new Banknote(THOUSAND, 1000),
                        new Banknote(TWO_THOUSAND, 2000),
                        new Banknote(FIVE_THOUSAND, 5000)
                )
        );
    }

    @Test
    public void shouldCorrectReturnMinimumBanknotes_5560() {
        Banknote fiveThousand = new Banknote(FIVE_THOUSAND, 5000);
        Banknote fiveHundred = new Banknote(FIVE_HUNDRED, 500);
        Banknote fifty = new Banknote(FIFTY, 50);
        Banknote ten = new Banknote(TEN, 10);

        Map<Banknote, Integer> requested = Map.of(
                fiveThousand, 1,
                fiveHundred, 1,
                fifty, 1,
                ten, 1
        );

        Map<Banknote, Integer> result = calculateIssuedBanknotes.calculate(5560);

        assertThat(result).isNotNull()
                .hasSize(4)
                .usingRecursiveAssertion()
                .isEqualTo(requested);
    }

}
