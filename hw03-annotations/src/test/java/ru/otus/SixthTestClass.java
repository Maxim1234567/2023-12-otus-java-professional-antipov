package ru.otus;

import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SixthTestClass {

    @Before
    public void setUp() {
        throw new RuntimeException();
    }

    @Test
    public void correctTest1() {
        assertTrue(true);
    }

    @Test
    public void correctTest2() {
        assertTrue(true);
    }

    @Test
    public void correctTest3() {
        assertTrue(true);
    }

}
