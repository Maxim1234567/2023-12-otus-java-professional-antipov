package ru.otus;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestLoggingTest {

    private static final int COUNT_CALL_LOGGING_INFO = 6;

    @Mock
    private Appender appender;

    @Test
    public void shouldCorrectLoggingCallMethodAop() {
        Logger logger = (Logger) LoggerFactory.getLogger(AOPLogging.class);
        logger.addAppender(appender);

        TestLoggingInterface testLoggingInterface = AOPLogging.proxyTestLogging();

        testLoggingInterface.add(1, 2);
        testLoggingInterface.add(1, 2, 3);
        testLoggingInterface.add(1, 2, 3, 4);

        testLoggingInterface.sub(1, 2);
        testLoggingInterface.sub(1, 2, 3);

        testLoggingInterface.mult(1, 2);
        testLoggingInterface.mult(1, 2, 3);
        testLoggingInterface.mult(1);

        ArgumentCaptor<LoggingEvent> eventArgumentCaptor = ArgumentCaptor.forClass(LoggingEvent.class);

        verify(appender, times(COUNT_CALL_LOGGING_INFO)).doAppend(eventArgumentCaptor.capture());

        LoggingEvent addTwoArguments = new LoggingEvent(AOPLogging.class.getName(), logger, Level.INFO, "executed method: {}, param: {}", null, new Object[] {"add", "[1, 2]"});
        LoggingEvent addThreeArguments = new LoggingEvent(AOPLogging.class.getName(), logger, Level.INFO, "executed method: {}, param: {}", null, new Object[] {"add", "[1.0, 2.0, 3.0]"});
        LoggingEvent addFourArguments = new LoggingEvent(AOPLogging.class.getName(), logger, Level.INFO, "executed method: {}, param: {}", null, new Object[] {"add", "[1.0, 2.0, 3.0, 4.0]"});
        LoggingEvent subTwoArguments = new LoggingEvent(AOPLogging.class.getName(), logger, Level.INFO, "executed method: {}, param: {}", null, new Object[] {"sub", "[1.0, 2.0]"});
        LoggingEvent subThreeArguments = new LoggingEvent(AOPLogging.class.getName(), logger, Level.INFO, "executed method: {}, param: {}", null, new Object[] {"sub", "[1.0, 2.0, 3.0]"});
        LoggingEvent multTwoArguments = new LoggingEvent(AOPLogging.class.getName(), logger, Level.INFO, "executed method: {}, param: {}", null, new Object[] {"mult", "[1, 2]"});
        LoggingEvent multThreeArguments = new LoggingEvent(AOPLogging.class.getName(), logger, Level.INFO, "executed method: {}, param: {}", null, new Object[] {"mult", "[1.0, 2.0, 3.0]"});
        LoggingEvent multOneArgument = new LoggingEvent(AOPLogging.class.getName(), logger, Level.INFO, "executed method: {}, param: {}", null, new Object[] {"mult", "[1.0]"});

        assertThat(eventArgumentCaptor.getAllValues()).isNotNull()
                .hasSize(COUNT_CALL_LOGGING_INFO)
                .map(LoggingEvent::toString)
                .contains(
                        addTwoArguments.toString(),
                        addThreeArguments.toString(),
                        addFourArguments.toString(),
                        subThreeArguments.toString(),
                        multThreeArguments.toString(),
                        multOneArgument.toString()
                )
                .doesNotContain(
                        subTwoArguments.toString(),
                        multTwoArguments.toString()
                );
    }
}
