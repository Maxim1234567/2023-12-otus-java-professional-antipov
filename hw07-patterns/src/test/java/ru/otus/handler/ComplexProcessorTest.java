package ru.otus.handler;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.mockito.internal.creation.MockSettingsImpl;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.processor.DateTimeProvider;
import ru.otus.processor.Processor;
import ru.otus.processor.ProcessorConcatFields;
import ru.otus.processor.ProcessorSwapField11AndField12;
import ru.otus.processor.ProcessorThrowExceptionEvenSecond;
import ru.otus.processor.ProcessorUpperField10;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.withSettings;

class ComplexProcessorTest {

    @Test
    @DisplayName("Тестируем вызовы процессоров")
    void handleProcessorsTest() {
        //given
        var message = new Message.Builder(1L)
                .field1("join_me_1")
                .field2("join_me_2")
                .field3("join_me_3")
                .field4("concat: join_me_1 join_me_2 join_me_3")
                .field5("UPPER_ME!")
                .field10("upper_me!")
                .field11("swap_me_field11")
                .field12("swap_me_field12")
                .build();


        var processMessage = new Message.Builder(1L)
                .field1("join_me_1")
                .field2("join_me_2")
                .field3("join_me_3")
                .field10("upper_me!")
                .field11("swap_me_field12")
                .field12("swap_me_field11")
                .build();

        var processor1 = mock(ProcessorConcatFields.class);
        given(processor1.process(any())).willCallRealMethod();

        var processor2 = mock(ProcessorUpperField10.class);
        given(processor2.process(any())).willCallRealMethod();

        var processor3 = mock(ProcessorSwapField11AndField12.class);
        given(processor3.process(any())).willCallRealMethod();

        var processors = List.of(processor1, processor2, processor3);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
        });

        //when
        var result = complexProcessor.handle(processMessage);

        //then
        verify(processor1).process(processMessage);
        verify(processor2).process(processMessage);
        verify(processor3).process(processMessage);

        assertThat(result).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(message);
    }

    @Test
    @DisplayName("Тестируем обработку исключения")
    void handleExceptionTest() {
        //given
        var message = new Message.Builder(1L).field8("field8").build();

        var timeEvenSecond = mock(DateTimeProvider.class);
        given(timeEvenSecond.getDate())
                .willReturn(LocalDateTime.of(2024, 3, 2, 12, 40, 10));

        var timeOddSecond = mock(DateTimeProvider.class);
        given(timeOddSecond.getDate())
                .willReturn(LocalDateTime.of(2024, 3, 2, 12, 40, 11));

        var processor1 = mock(
                ProcessorThrowExceptionEvenSecond.class, withSettings().useConstructor(timeEvenSecond)
        );
        given(processor1.process(any()))
                .willCallRealMethod();

        var processor2 = mock(
                ProcessorThrowExceptionEvenSecond.class, withSettings().useConstructor(timeOddSecond)
        );
        given(processor2.process(any()))
                .willCallRealMethod();

        List<Processor> processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
            throw new TestException(ex.getMessage());
        });

        //when
        assertThatExceptionOfType(TestException.class)
                .isThrownBy(() -> complexProcessor.handle(message))
                .withMessage("Not even seconds: 10");

        //then
        verify(processor1, times(1)).process(message);
        verify(processor2, never()).process(message);
    }

    @Test
    @DisplayName("Тестируем уведомления")
    void notifyTest() {
        //given
        var message = new Message.Builder(1L).field9("field9").build();

        var listener = mock(Listener.class);

        var complexProcessor = new ComplexProcessor(new ArrayList<>(), (ex) -> {
        });

        complexProcessor.addListener(listener);

        //when
        complexProcessor.handle(message);
        complexProcessor.removeListener(listener);
        complexProcessor.handle(message);

        //then
        verify(listener, times(1)).onUpdated(message);
    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}