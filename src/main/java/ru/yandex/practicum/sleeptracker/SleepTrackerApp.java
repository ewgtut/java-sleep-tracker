package ru.yandex.practicum.sleeptracker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SleepTrackerApp {

    public static void main(String[] args) throws IOException {
        // исправить на чтение из кс
        String filePath = "src\\main\\resources\\sleep_log.txt";
        //Чтение сессий из файла
        File file = new File(filePath);
        try (Stream<String> linesStream = Files.lines(file.toPath())) {

            List<SleepSession> sleepList = linesStream.map((line) -> new SleepSession(line.split(";")[0],
                    line.split(";")[1], line.split(";")[2])).collect(Collectors.toList());
            //Список функций для анализ списка сессий сна
            List<Function<List<SleepSession>, SleepAnalysisResult>> functionList = new ArrayList<>();
            // добавление функций для анализа
            functionList.add(new CounterOfSessions());
            functionList.add(new CalculatorOfMinSession());
            functionList.add(new CalculatorOfMaxSession());
            functionList.add(new CalculatorOfAvgOfSessions());
            functionList.add(new CalculatorOfBadSessions());
            functionList.add(new CalculateSleeplessNights());
            functionList.add(new CalculatorOfCategoryOfUser());
            //выполнение функций
            functionList.stream().map(f -> f.apply(sleepList)).forEach((n) -> System.out.printf("%s - %d.%n", n.description, n.result));
        }
    }
}
