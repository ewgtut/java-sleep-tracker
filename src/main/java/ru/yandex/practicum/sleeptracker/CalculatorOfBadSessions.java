package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class CalculatorOfBadSessions implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> list) {
        return new SleepAnalysisResult("Плохие сессии сна", (int) list.stream().filter((ss) -> ss.getSleepQuality().equals(SleepQuality.BAD)).count());
    }

}
