package ru.otus;

import ru.otus.annotation.After;
import ru.otus.annotation.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FourthTestClass {
    private int first;

    @Test
    public void correctTest() {
        assertEquals(first, 0);
    }

    @Test
    public void incorrectTest() {
        assertEquals(first, -1);
    }

    @After
    public void after() {
        first = 5;
    }
}
