package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CalculateSleeplessNights implements Function<List<SleepSession>, SleepAnalysisResult> {
    //Также будем считать, что если первая сессия сна в файле началась после 12 дня, потенциальной ночью для сна считается следующая ночь, а если до 12 — то предыдущая.
    private final int NIGHT_HOUR_DELIMITER = 12;
    //Бессонной ночью считается ночь, когда не было ни одной сессии сна, пересекающей интервал от 0:00 до 6:00.
    private final int NIGHT_HOUR_START = 0;
    private final int NIGHT_HOUR_END = 6;
    private final int NIGHT_MIN_START = 0;
    private final int NIGHT_MIN_END = 0;

    @Override
    public SleepAnalysisResult apply(List<SleepSession> list) {

        // ночи можно определять по первому дню
        //определяем ночи
        Map<LocalDate, Boolean> nightMap = list.stream().map((ss) -> {

            if (ss.getStartOfSession().getHour() > NIGHT_HOUR_DELIMITER) {
                return ss.getStartOfSession().toLocalDate().plusDays(1);
            } else {
                return ss.getStartOfSession().toLocalDate();
            }
        }).collect(Collectors.toSet()).stream().collect(Collectors.toMap((n) -> n, (n) -> Boolean.FALSE));

        // для каждой сессии определяем, был ли это ночной сон, если - да, то отмечаем соотв. ночь (по умлочанию сна не было)
        list.stream().forEach((ss) -> {

            LocalDate nightLocalDate;
            LocalDateTime nightLocalDateTimeStart, nightLocalDateTimeEnd;

            if (ss.getStartOfSession().getHour() > NIGHT_HOUR_DELIMITER) {
                nightLocalDate = ss.getStartOfSession().toLocalDate().plusDays(1);
            } else {
                nightLocalDate = ss.getStartOfSession().toLocalDate();
            }
            nightLocalDateTimeStart = LocalDateTime.of(nightLocalDate, LocalTime.of(NIGHT_HOUR_START, NIGHT_MIN_START));
            nightLocalDateTimeEnd = LocalDateTime.of(nightLocalDate, LocalTime.of(NIGHT_HOUR_END, NIGHT_MIN_END));
            if ((nightLocalDateTimeStart.isAfter(ss.getStartOfSession()) && nightLocalDateTimeStart.isBefore(ss.getEndOfSession())) ||
                    (nightLocalDateTimeEnd.isAfter(ss.getStartOfSession()) && nightLocalDateTimeEnd.isBefore(ss.getEndOfSession())) ) {
                nightMap.put(nightLocalDate, Boolean.TRUE);
            }
        });
        return new SleepAnalysisResult("Количество бессонных ночей", (int) nightMap.entrySet().stream().filter((e) -> e.getValue() == false).count());
    }

}
