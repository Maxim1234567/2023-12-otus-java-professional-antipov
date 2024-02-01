package ru.otus;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SummatorTest {
    private final int standardSum = 887459712;
    private final int standardPrevValue = 99999999;
    private final int standardPrevPrevValue = 99999998;
    private final int standardSumLastThreeValues = 299999994;
    private final int standardSomeValue = 655761157;

    @Test
    public void shouldCorrectReturnValueAfterChangeCodeSummator() {
        long counter = 100_000_000;
        var summator = new Summator();

        for (var idx = 0; idx < counter; idx++) {
            var data = new Data(idx);
            summator.calc(data);
        }

        assertThat(summator).isNotNull()
                .matches(s -> s.getSum() == standardSum)
                .matches(s -> s.getPrevValue() == standardPrevValue)
                .matches(s -> s.getPrevPrevValue() == standardPrevPrevValue)
                .matches(s -> s.getSomeValue() == standardSomeValue)
                .matches(s -> s.getSumLastThreeValues() == standardSumLastThreeValues);
    }
}
