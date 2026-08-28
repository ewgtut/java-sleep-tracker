package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

public class CalculatorOfCategoryOfUser implements Function<List<SleepSession>, SleepAnalysisResult> {
    private final int NIGHT_HOUR_DELIMITER = 12;
    private final int NIGHT_HOUR_START = 0;
    private final int NIGHT_HOUR_END = 6;
    private final int NIGHT_MIN_START = 0;
    private final int NIGHT_MIN_END = 0;

    //«Сова» — если время засыпания было после 23:00, а время пробуждения — после 9:00.
    private final int OWL_NIGHT_HOUR_START = 23;
    private final int OWL_NIGHT_HOUR_END = 9;
    //«Жаворонок» — если время засыпания было до 22:00, а время пробуждения до — 7:00.
    private final int LARK_NIGHT_HOUR_START = 22;
    private final int LARK_NIGHT_HOUR_END = 7;


    @Override
    public SleepAnalysisResult apply(List<SleepSession> list) {
        //список сессий с ночным сном
        List<SleepSession> nightList = list.stream().filter((ss) -> {

            LocalDate nightLocalDate;
            LocalDateTime nightLocalDateTimeStart, nightLocalDateTimeEnd;

            if (ss.getStartOfSession().getHour() > NIGHT_HOUR_DELIMITER) {
                nightLocalDate = ss.getStartOfSession().toLocalDate().plusDays(1);
            } else {
                nightLocalDate = ss.getStartOfSession().toLocalDate();
            }
            nightLocalDateTimeStart = LocalDateTime.of(nightLocalDate, LocalTime.of(NIGHT_HOUR_START, NIGHT_MIN_START));
            nightLocalDateTimeEnd = LocalDateTime.of(nightLocalDate, LocalTime.of(NIGHT_HOUR_END, NIGHT_MIN_END));
            return ((nightLocalDateTimeStart.isAfter(ss.getStartOfSession()) && nightLocalDateTimeStart.isBefore(ss.getEndOfSession())) ||
                    (nightLocalDateTimeEnd.isAfter(ss.getStartOfSession()) && nightLocalDateTimeEnd.isBefore(ss.getEndOfSession())));
        } ).collect(Collectors.toList());

        // подсчет сессий совы
        //«Сова» — если время засыпания было после 23:00, а время пробуждения — после 9:00.
        int OwlCount = (int) nightList.stream().filter((ss) -> {
            LocalDate nightLocalDate;
            LocalDateTime nightLocalDateTimeStart, nightLocalDateTimeEnd;

            if (ss.getStartOfSession().getHour() > NIGHT_HOUR_DELIMITER) {
                nightLocalDate = ss.getStartOfSession().toLocalDate().plusDays(1);
            } else {
                nightLocalDate = ss.getStartOfSession().toLocalDate();
            }
            // отметки времени для сравнения
            LocalDateTime periodStart = LocalDateTime.of(nightLocalDate.minusDays(1),LocalTime.of(OWL_NIGHT_HOUR_START,0));
            LocalDateTime periodEnd = LocalDateTime.of(nightLocalDate,LocalTime.of(OWL_NIGHT_HOUR_END,0));
            return ss.getStartOfSession().isAfter(periodStart) && ss.getEndOfSession().isAfter(periodEnd);
        }).count();

        // подсчет сессий жаворонка
        //«Жаворонок» — если время засыпания было до 22:00, а время пробуждения до — 7:00.
        int LarkCount = (int) nightList.stream().filter((ss) -> {
            LocalDate nightLocalDate;
            LocalDateTime nightLocalDateTimeStart, nightLocalDateTimeEnd;

            if (ss.getStartOfSession().getHour() > NIGHT_HOUR_DELIMITER) {
                nightLocalDate = ss.getStartOfSession().toLocalDate().plusDays(1);
            } else {
                nightLocalDate = ss.getStartOfSession().toLocalDate();
            }
            // отметки времени для сравнения
            LocalDateTime periodStart = LocalDateTime.of(nightLocalDate.minusDays(1),LocalTime.of(LARK_NIGHT_HOUR_START,0));
            LocalDateTime periodEnd = LocalDateTime.of(nightLocalDate,LocalTime.of(LARK_NIGHT_HOUR_END,0));
            return ss.getStartOfSession().isBefore(periodStart) && ss.getEndOfSession().isBefore(periodEnd);
        }).count();

        //«Голубь» — во всех остальных случаях.
        int PigeonCount = nightList.size() - LarkCount - OwlCount;

        int count;
        String description;

        if (OwlCount > LarkCount && OwlCount > PigeonCount) {
            count = OwlCount;
            description = "сова";
        } else if (LarkCount > OwlCount && LarkCount > PigeonCount) {
            count = LarkCount;
            description = "жаворонок";
        } else {
            count = PigeonCount;
            description = "голубь";
        }
        return new SleepAnalysisResult(String.format("Пользователь относится к типу \"%s\", число сессий",description),count);
    }
}
