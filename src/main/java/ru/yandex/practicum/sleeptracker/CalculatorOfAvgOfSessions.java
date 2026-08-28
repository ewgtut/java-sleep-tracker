package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class CalculatorOfAvgOfSessions implements Function<List<SleepSession>,SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> list) {
        return new SleepAnalysisResult("Средняя сессия(мин.)", (int) list.stream()
                .map((ss) -> Duration.between(ss.getStartOfSession(), ss.getEndOfSession()).toMinutes()).mapToInt(Long::intValue).average().getAsDouble());
    }
}
