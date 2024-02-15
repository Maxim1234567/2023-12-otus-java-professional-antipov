package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.exception.UnableIssueAmountException;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.otus.Denomination.FIFTY;
import static ru.otus.Denomination.FIVE_HUNDRED;
import static ru.otus.Denomination.FIVE_THOUSAND;
import static ru.otus.Denomination.HUNDRED;
import static ru.otus.Denomination.TEN;
import static ru.otus.Denomination.THOUSAND;
import static ru.otus.Denomination.TWO_HUNDRED;
import static ru.otus.Denomination.TWO_THOUSAND;

public class CalculateIssuedBanknotesTest {

    private CalculateIssuedBanknotesImpl calculateIssuedAllBanknotes;

    private CalculateIssuedBanknotesImpl calculateIssuedPartOfBanknotes;

    @BeforeEach
    public void setUp() {
        calculateIssuedAllBanknotes = new CalculateIssuedBanknotesImpl(
                List.of(
                        TEN, FIFTY, HUNDRED, TWO_HUNDRED, FIVE_HUNDRED, THOUSAND, TWO_THOUSAND, FIVE_THOUSAND
                )
        );

        calculateIssuedPartOfBanknotes = new CalculateIssuedBanknotesImpl(
                List.of(
                        TEN, HUNDRED, FIVE_HUNDRED, TWO_THOUSAND
                )
        );
    }

    @Test
    public void shouldCorrectReturnMinimumBanknotes_5560_withAllBanknotes() {
        Map<Denomination, Integer> requested = Map.of(
                FIVE_THOUSAND, 1,
                FIVE_HUNDRED, 1,
                FIFTY, 1,
                TEN, 1
        );

        Map<Denomination, Integer> result = calculateIssuedAllBanknotes.calculate(5560);

        assertThat(result).isNotNull()
                .hasSize(4)
                .usingRecursiveAssertion()
                .isEqualTo(requested);
    }

    @Test
    public void shouldCorrectReturnMinimumBanknotes_5560_withPartOfBanknotes() {
        Map<Denomination, Integer> requested = Map.of(
                TWO_THOUSAND, 2,
                FIVE_HUNDRED, 3,
                TEN, 6
        );

        Map<Denomination, Integer> result = calculateIssuedPartOfBanknotes.calculate(5560);

        assertThat(result).isNotNull()
                .hasSize(3)
                .usingRecursiveAssertion()
                .isEqualTo(requested);
    }

    @Test
    public void shouldCorrectReturnMinimumBanknotes_3480_withAllBanknotes() {
        Map<Denomination, Integer> requested = Map.of(
                TWO_THOUSAND, 1,
                THOUSAND, 1,
                TWO_HUNDRED, 2,
                FIFTY, 1,
                TEN, 3
        );

        Map<Denomination, Integer> result = calculateIssuedAllBanknotes.calculate(3480);

        assertThat(result).isNotNull()
                .hasSize(5)
                .usingRecursiveAssertion()
                .isEqualTo(requested);
    }

    @Test
    public void shouldCorrectReturnMinimumBanknotes_3480_withPartOfBanknotes() {
        Map<Denomination, Integer> requested = Map.of(
                TWO_THOUSAND, 1,
                FIVE_HUNDRED, 2,
                HUNDRED, 4,
                TEN, 8
        );

        Map<Denomination, Integer> result = calculateIssuedPartOfBanknotes.calculate(3480);

        assertThat(result).isNotNull()
                .hasSize(4)
                .usingRecursiveAssertion()
                .isEqualTo(requested);
    }

    @Test
    public void shouldCorrectReturnMinimumBanknotes_120_withAllBanknotes() {
        Map<Denomination, Integer> requested = Map.of(
                HUNDRED, 1,
                TEN, 2
        );

        Map<Denomination, Integer> result = calculateIssuedAllBanknotes.calculate(120);

        assertThat(result).isNotNull()
                .hasSize(2)
                .usingRecursiveAssertion()
                .isEqualTo(requested);
    }

    @Test
    public void shouldCorrectReturnMinimumBanknotes_120_withPartOfBanknotes() {
        Map<Denomination, Integer> requested = Map.of(
                HUNDRED, 1,
                TEN, 2
        );

        Map<Denomination, Integer> result = calculateIssuedAllBanknotes.calculate(120);

        assertThat(result).isNotNull()
                .hasSize(2)
                .usingRecursiveAssertion()
                .isEqualTo(requested);
    }

    @Test
    public void shouldThrowUnableIssueAmountExceptionIfIncorrectAmount() {
        String message = assertThrows(UnableIssueAmountException.class, () -> calculateIssuedAllBanknotes.calculate(125))
                .getMessage();

        assertThat(message)
                .isNotNull()
                .isEqualTo("Amount 125 can't be issued");
    }
}
