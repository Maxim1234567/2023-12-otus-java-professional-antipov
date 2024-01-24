package ru.otus;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstTestClass {
    private int first;
    private int second;

    @Before
    public void setUp() {
        first = 5;
        second = 6;
    }

    @Test
    public void correctTest() {
        int sum = first + second;

        assertEquals(sum, 11);
    }

    @Test
    public void incorrectTest() {
        int sum = first + second;

        assertEquals(sum, 10);
    }

    @After
    public void after() {
        first = 0;
        second = 0;
    }
}
