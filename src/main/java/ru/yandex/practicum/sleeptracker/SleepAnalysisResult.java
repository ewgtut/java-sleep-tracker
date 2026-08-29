package ru.yandex.practicum.sleeptracker;

import java.util.Objects;

public class SleepAnalysisResult {
    public String description;
    public int result;

    public SleepAnalysisResult(String description, int result) {
        this.description = description;
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SleepAnalysisResult that)) return false;
        return result == that.result && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, result);
    }
}
