package ru.otus;

import ru.otus.annotation.Log;

public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public int add(int x, int y) {
        return x + y;
    }

    @Log
    @Override
    public float add(float x, float y, float z) {
        return x + y + z;
    }

    @Log
    @Override
    public double add(double x, double y, double z, double k) {
        return x + y + z + k;
    }

    @Override
    public float sub(float x, float y) {
        return x - y;
    }

    @Log
    @Override
    public double sub(double x, double y, double z) {
        return x - y - z;
    }

    @Override
    public int mult(int x, int y) {
        return x * y;
    }

    @Log
    @Override
    public float mult(float x, float y, float z) {
        return x * y * z;
    }

    @Log
    @Override
    public double mult(double x) {
        return x * 2;
    }
}
