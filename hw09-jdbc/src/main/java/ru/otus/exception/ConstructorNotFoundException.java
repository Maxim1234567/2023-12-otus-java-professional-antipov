package ru.otus.exception;

public class ConstructorNotFoundException extends RuntimeException {
    public ConstructorNotFoundException(String className, Exception e) {
        super(String.format("In class %s constructor not found", className), e);
    }
}
