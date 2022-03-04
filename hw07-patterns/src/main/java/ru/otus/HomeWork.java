package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ProcessorChangeField;
import ru.otus.processor.homework.ProcessorEvenSecondsException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяьться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
        List<Processor> processors = new ArrayList<>();
        processors.add(new ProcessorEvenSecondsException(LocalDateTime::now));
        processors.add(new ProcessorChangeField());

        var complexProcessor = new ComplexProcessor(processors, (ex) -> System.out.println(ex.getMessage()));
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list,"01", "02", "03");
        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(list);

        var message = new Message.Builder(171L)
                .field11("11")
                .field12("12")
                .field13(objectForMessage)
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        list = new ArrayList<>();
        Collections.addAll(list,"101", "102", "103");
        objectForMessage = new ObjectForMessage();
        objectForMessage.setData(list);
        message = message.toBuilder().field13(objectForMessage).build();
        result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(historyListener);
    }
}
