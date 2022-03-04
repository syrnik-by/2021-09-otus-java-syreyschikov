package ru.otus.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ProcessorEvenSecondsException;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcessorEvenSecondsExceptionTest {
    @Test
    @DisplayName("Тестируем выполнение процессора в четную секунду")
    void processAtEvenSecond() {
        Processor processor = new ProcessorEvenSecondsException(
                () -> LocalDateTime.of(2020, Month.JANUARY, 8, 23, 45, 30)
        );
        assertThrows(IllegalStateException.class, () -> processor.process(null));
    }

    @Test
    @DisplayName("Тестируем выполнение процессора в нечетную секунду")
    void processAtOddSecond() {
        Processor processor = new ProcessorEvenSecondsException(
                () -> LocalDateTime.of(2020, Month.JANUARY, 8, 23, 45, 29)
        );
        assertDoesNotThrow(() -> processor.process(null));
    }
}
