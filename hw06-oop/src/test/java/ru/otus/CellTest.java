package ru.otus;

import org.junit.jupiter.api.Test;
import ru.otus.exception.NotBanknoteException;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.otus.Denomination.THOUSAND;

public class CellTest {

    @Test
    public void shouldCorrectReturnHundredBanknotes() {
        Banknote thousand = new Banknote(THOUSAND, THOUSAND.getValue());
        Cell cell = new Cell(THOUSAND, 1000);

        List<Banknote> banknotes = Stream.generate(() -> thousand)
                .limit(100)
                .toList();

        List<Banknote> result = cell.getBanknotes(100);

        assertThat(result).isNotNull()
                .hasSize(100)
                .isEqualTo(banknotes);
    }

    @Test
    public void shouldThrowsNotBanknoteExceptionIfCellEmpty() {
        Banknote thousand = new Banknote(THOUSAND, THOUSAND.getValue());
        Cell cell = new Cell(THOUSAND, 0);

        String message = assertThrows(NotBanknoteException.class, () -> cell.getBanknotes(100))
                .getMessage();

        assertThat(message).isNotNull()
                .isEqualTo("That is how many 100 banknotes are missing Thousand");
    }

    @Test
    public void shouldCorrectReturnFalseIfCellIsNotEmpty() {
        Banknote thousand = new Banknote(THOUSAND, THOUSAND.getValue());
        Cell cell = new Cell(THOUSAND, 1000);

        assertFalse(cell.isEmpty());
    }

    @Test
    public void shouldCorrectReturnTrueIfCellIsEmpty() {
        Banknote thousand = new Banknote(THOUSAND, THOUSAND.getValue());
        Cell cell = new Cell(THOUSAND, 0);

        assertTrue(cell.isEmpty());
    }

    @Test
    public void shouldCorrectLoadBanknotes() {
        Banknote thousand = new Banknote(THOUSAND, THOUSAND.getValue());
        Cell cell = new Cell(THOUSAND, 10);

        List<Banknote> generateBanknotes = Stream.generate(() -> thousand)
                .limit(1000)
                .toList();

        cell.load(generateBanknotes);

        List<Banknote> banknotes = assertDoesNotThrow(() -> cell.getBanknotes(1010));

        assertTrue(cell.isEmpty());
        assertThat(banknotes).isNotNull()
                .hasSize(1010);
    }

    @Test
    public void shouldCorrectReturnCashBalance() {
        Cell cell = new Cell(THOUSAND, 10);

        int cash = 10_000;

        int result = cell.getCashBalance();

        assertThat(result).isNotNull()
                .isEqualTo(cash);
    }

    @Test
    public void shouldCorrectReturnCashBalanceIfCellIsEmpty() {
        Cell cell = new Cell(THOUSAND, 0);

        int cash = 0;

        int result = cell.getCashBalance();

        assertThat(result).isNotNull()
                .isEqualTo(cash);
    }
}
