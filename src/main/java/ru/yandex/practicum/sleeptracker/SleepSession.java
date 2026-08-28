package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SleepSession {
    private LocalDateTime startOfSession;
    private LocalDateTime endOfSession;
    private SleepQuality sleepQuality;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public SleepSession(String startOfSession, String endOfSession, String sleepQuality) {
        this.startOfSession = LocalDateTime.parse(startOfSession, formatter);
        this.endOfSession = LocalDateTime.parse(endOfSession, formatter);
        this.sleepQuality = SleepQuality.getQuality(sleepQuality);
    }

    public LocalDateTime getStartOfSession() {
        return startOfSession;
    }

    public LocalDateTime getEndOfSession() {
        return endOfSession;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }
}
