package ru.otus;

import org.junit.jupiter.api.Test;
import ru.otus.exception.NotBanknoteException;
import ru.otus.exception.UnableIssueAmountException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.otus.Denomination.FIFTY;
import static ru.otus.Denomination.FIVE_HUNDRED;
import static ru.otus.Denomination.FIVE_THOUSAND;
import static ru.otus.Denomination.HUNDRED;
import static ru.otus.Denomination.TEN;
import static ru.otus.Denomination.THOUSAND;
import static ru.otus.Utils.generateBanknote;
import static ru.otus.Utils.unionList;

public class AtmTest {

    @Test
    public void shouldCorrectLoadBanknotes() {
        List<Banknote> thousand = generateBanknote(THOUSAND, 10);
        List<Banknote> fiveHundred = generateBanknote(FIVE_HUNDRED, 10);
        List<Banknote> fifty = generateBanknote(FIFTY, 10);

        Atm atm = new Atm();

        atm.loadBanknotes(
                unionList(thousand, fiveHundred, fifty)
        );

        int cashBalance = 15_500;

        int result = atm.getCashBalance();

        assertThat(result).isNotNull()
                .isEqualTo(cashBalance);
    }

    @Test
    public void shouldCorrectReturnBanknotes_5670() {
        List<Banknote> fiveThousand = generateBanknote(FIVE_THOUSAND, 10);
        List<Banknote> fiveHundred = generateBanknote(FIVE_HUNDRED, 10);
        List<Banknote> hundred = generateBanknote(HUNDRED, 10);
        List<Banknote> fifty = generateBanknote(FIFTY, 10);
        List<Banknote> ten = generateBanknote(TEN, 10);

        Atm atm = new Atm();

        atm.loadBanknotes(
                unionList(fiveThousand, fiveHundred, hundred, fifty, ten)
        );

        List<Banknote> result = atm.getBanknotes(5670);

        assertThat(result).isNotNull()
                .hasSize(6)
                .contains(
                        new Banknote(FIVE_THOUSAND, FIVE_THOUSAND.getValue()),
                        new Banknote(FIVE_HUNDRED, FIVE_HUNDRED.getValue()),
                        new Banknote(HUNDRED, HUNDRED.getValue()),
                        new Banknote(FIFTY, FIFTY.getValue()),
                        new Banknote(TEN, TEN.getValue())
                );
    }

    @Test
    public void shouldThrowUnableIssueAmountExceptionIfUnableIssue() {
        List<Banknote> fiveThousand = generateBanknote(FIVE_THOUSAND, 10);
        List<Banknote> fiveHundred = generateBanknote(FIVE_HUNDRED, 10);
        List<Banknote> hundred = generateBanknote(HUNDRED, 10);
        List<Banknote> fifty = generateBanknote(FIFTY, 10);
        List<Banknote> ten = generateBanknote(TEN, 10);

        Atm atm = new Atm();

        atm.loadBanknotes(
                unionList(fiveThousand, fiveHundred, hundred, fifty, ten)
        );

        String message = assertThrows(UnableIssueAmountException.class, () -> atm.getBanknotes(5671)).getMessage();

        assertThat(message).isNotNull()
                .isEqualTo("Amount 5671 can't be issued");
    }

    @Test
    public void shouldThrowNotBanknoteExceptionIfNotBanknotes() {
        List<Banknote> fiveThousand = generateBanknote(FIVE_THOUSAND, 10);
        List<Banknote> fiveHundred = generateBanknote(FIVE_HUNDRED, 10);
        List<Banknote> hundred = generateBanknote(HUNDRED, 10);
        List<Banknote> fifty = generateBanknote(FIFTY, 10);
        List<Banknote> ten = generateBanknote(TEN, 1);

        Atm atm = new Atm();

        atm.loadBanknotes(
                unionList(fiveThousand, fiveHundred, hundred, fifty, ten)
        );

        String message = assertThrows(NotBanknoteException.class, () -> atm.getBanknotes(5670)).getMessage();

        assertThat(message).isNotNull()
                .isEqualTo("That is how many 1 banknotes are missing Ten");
    }
}
