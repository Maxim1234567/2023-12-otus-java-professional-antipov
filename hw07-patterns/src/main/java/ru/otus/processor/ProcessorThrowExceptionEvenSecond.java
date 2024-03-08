package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class ProcessorThrowExceptionEvenSecond implements Processor {

    private final DateTimeProvider time;

    public ProcessorThrowExceptionEvenSecond(DateTimeProvider time) {
        this.time = time;
    }

    @Override
    public Message process(Message message) {

        LocalDateTime localDateTime = time.getDate();
        int second = localDateTime.getSecond();

        if (second % 2 == 0)
            throw new RuntimeException(String.format("Not even seconds: %d", second));

        return message.toBuilder().build();
    }
}
