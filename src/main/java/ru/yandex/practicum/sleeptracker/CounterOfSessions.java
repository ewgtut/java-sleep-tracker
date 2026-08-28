package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class CounterOfSessions implements Function<List<SleepSession>,SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> list) {
        return new SleepAnalysisResult("Общее количество сессий",list.size());
    }
}
