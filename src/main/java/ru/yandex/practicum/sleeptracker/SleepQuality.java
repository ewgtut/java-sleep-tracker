package ru.yandex.practicum.sleeptracker;

public enum SleepQuality {
    GOOD,
    NORMAL,
    BAD;
    public static SleepQuality getQuality(String qualityString) {
        switch (qualityString){
            case "GOOD" :
                return SleepQuality.GOOD;
            case "NORMAL" :
                return SleepQuality.NORMAL;
            case "BAD":
                return SleepQuality.BAD;
        }
        throw new IllegalArgumentException("Неизвестное качество сна");
    }
}
