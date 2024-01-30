package ru.otus;

import ru.otus.annotation.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecondTestClass {

    @Test
    public void correctTest1() {
        assertTrue(true);
    }

    @Test
    public void correctTest2() {
        assertTrue(true);
    }

    @Test
    public void incorrectTest3() {
        assertTrue(false);
    }

}
