package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class CalculatorOfMinSession implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> list) {
        return new SleepAnalysisResult("Минимальная сессия(мин.)", list.stream()
                .filter((ss) -> (ss.getStartOfSession() != null && ss.getEndOfSession() != null))
                .map((ss) -> Duration.between(ss.getStartOfSession(), ss.getEndOfSession()).toMinutes())
                .min(Long::compare).get().intValue());
    }
}
