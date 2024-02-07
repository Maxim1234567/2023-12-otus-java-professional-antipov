package ru.otus;

public class Main {
    public static void main(String[] args) {
        TestLoggingInterface testLoggingInterface = AOPLogging.proxyTestLogging();
        testLoggingInterface.add(1, 2);
        testLoggingInterface.mult(1, 2);
    }
}
