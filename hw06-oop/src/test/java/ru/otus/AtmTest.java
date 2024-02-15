package ru.otus;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.Denomination.FIFTY;
import static ru.otus.Denomination.FIVE_HUNDRED;
import static ru.otus.Denomination.THOUSAND;

public class AtmTest {

    @Test
    public void shouldCorrectLoadBanknotes() {
        List<Banknote> thousand = generateBanknote(THOUSAND, 10);
        List<Banknote> fiveHundred = generateBanknote(FIVE_HUNDRED, 10);
        List<Banknote> fifty = generateBanknote(FIFTY, 10);

        Atm atm = new Atm();

        atm.loadBanknotes(
                Stream.concat(Stream.concat(thousand.stream(), fiveHundred.stream()), fifty.stream()).toList()
        );

        int cashBalance = 15_500;

        int result = atm.getCashBalance();

        assertThat(result).isNotNull()
                .isEqualTo(cashBalance);
    }

    public List<Banknote> generateBanknote(Denomination denomination, int size) {
        return Stream.generate(() -> new Banknote(denomination, denomination.getValue()))
                .limit(size)
                .toList();
    }

}
