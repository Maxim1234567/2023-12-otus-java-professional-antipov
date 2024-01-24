package ru.otus;

import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThirdTestClass {

    private int first;

    @Before
    public void setUp() {
        first = 5;
    }

    @Test
    public void correctTest() {
        assertEquals(first, 5);
    }

    @Test
    public void incorrectTest() {
        assertEquals(first, 6);
    }

}
