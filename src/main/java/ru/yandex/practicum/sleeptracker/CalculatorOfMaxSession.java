package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class CalculatorOfMaxSession implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> list) {
        return new SleepAnalysisResult("Минимальная сессия(мин.)", list.stream()
                .map((ss) -> Duration.between(ss.getStartOfSession(), ss.getEndOfSession()).toMinutes())
                .max(Long::compare).get().intValue());
    }
}