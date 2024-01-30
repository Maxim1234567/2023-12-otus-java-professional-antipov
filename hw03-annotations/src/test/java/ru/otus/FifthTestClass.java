package ru.otus;

import ru.otus.annotation.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FifthTestClass {

    @Test
    public void correctTest() {
        assertTrue(true);
    }

    @Test
    public void incorrectTest() {
        throw new RuntimeException();
    }

    @Test
    public void correctTest2() {
        assertTrue(true);
    }
}
