package ru.yandex.practicum.sleeptracker;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerAppTest {
    private static List<SleepSession> sleepList_1 = new ArrayList<>();
    private static List<SleepSession> sleepList_2 = new ArrayList<>();
    private static List<SleepSession> sleepList_3 = new ArrayList<>();
    private static List<SleepSession> sleepList_4 = new ArrayList<>();
    private static List<SleepSession> sleepList_5 = new ArrayList<>();


    @BeforeAll
    static void initAll() {
        sleepList_2.add(new SleepSession("01.10.25 23:15","02.10.25 07:15","BAD"));

        sleepList_3.add(new SleepSession("02.10.25 23:00","03.10.25 05:15","BAD"));
        sleepList_3.add(new SleepSession("03.10.25 23:55","04.10.25 01:00","BAD"));

        sleepList_4.add(new SleepSession("03.10.25 07:55","03.10.25 09:00","BAD"));

        sleepList_5.add(new SleepSession("03.10.25 21:55","04.10.25 06:05","GOOD"));

    }

    //CounterOfSessions
    @Test
    void testGetSessionCountZeroSessions() {
        Function<List<SleepSession>,SleepAnalysisResult> counter = new CounterOfSessions();
        assertEquals(0,counter.apply(sleepList_1).result);
    }

    @Test
    void testGetSessionCountOneSession() {
        Function<List<SleepSession>,SleepAnalysisResult> counter = new CounterOfSessions();
        assertEquals(1,counter.apply(sleepList_2).result);
    }
    //CalculatorOfMinSession
    @Test
    void testGetSessionMinDurationOneSession() {
        Function<List<SleepSession>,SleepAnalysisResult> calculator = new CalculatorOfMinSession();
        assertEquals(480,calculator.apply(sleepList_2).result);
    }

    @Test
    void testGetSessionMinDurationTwoSessions() {
        Function<List<SleepSession>,SleepAnalysisResult> calculator = new CalculatorOfMinSession();
        assertEquals(65,calculator.apply(sleepList_3).result);
    }
    //CalculatorOfMaxSession
    @Test
    void testGetSessionMaxDurationOneSession() {
        Function<List<SleepSession>,SleepAnalysisResult> calculator = new CalculatorOfMaxSession();
        assertEquals(480,calculator.apply(sleepList_2).result);
    }

    @Test
    void testGetSessionMaxDurationTwoSessions() {
        Function<List<SleepSession>,SleepAnalysisResult> calculator = new CalculatorOfMaxSession();
        assertEquals(375,calculator.apply(sleepList_3).result);
    }

    //CalculatorOfAvgOfSessions
    @Test
    void testGetSessionAvgDurationOneSession() {
        Function<List<SleepSession>,SleepAnalysisResult> calculator = new CalculatorOfAvgOfSessions();
        assertEquals(480,calculator.apply(sleepList_2).result);
    }
    @Test
    void testGetSessionAvgDurationTwoSessions() {
        Function<List<SleepSession>,SleepAnalysisResult> calculator = new CalculatorOfAvgOfSessions();
        assertEquals(220,calculator.apply(sleepList_3).result);
    }
    //CalculatorOfBadSessions
    @Test
    void testGetSessionCountOfBadOneSession() {
        Function<List<SleepSession>,SleepAnalysisResult> calculator = new CalculatorOfBadSessions();
        assertEquals(1,calculator.apply(sleepList_2).result);
    }
    @Test
    void testGetSessionCountOfBadTwoSessions() {
        Function<List<SleepSession>,SleepAnalysisResult> calculator = new CalculatorOfBadSessions();
        assertEquals(2,calculator.apply(sleepList_3).result);
    }
    //CalculateSleeplessNights
    @Test
    void testGetSessionCountOfSleeplessZeroSessions() {
        Function<List<SleepSession>,SleepAnalysisResult> calculator = new CalculateSleeplessNights();
        assertEquals(0,calculator.apply(sleepList_2).result);
    }

    @Test
    void testGetSessionCountOfSleeplessOneSession() {
        Function<List<SleepSession>,SleepAnalysisResult> calculator = new CalculateSleeplessNights();
        assertEquals(1,calculator.apply(sleepList_4).result);
    }
    //CalculatorOfCategoryOfUser
    void testGetUserCategorySecondList() {
        Function<List<SleepSession>,SleepAnalysisResult> calculator = new CalculatorOfCategoryOfUser();
        assertTrue(calculator.apply(sleepList_2).description.contains("голубь"));
    }

    @Test
    void testGetUserCategoryFifthList() {
        Function<List<SleepSession>,SleepAnalysisResult> calculator = new CalculatorOfCategoryOfUser();
        assertTrue(calculator.apply(sleepList_5).description.contains("жаворонок"));
    }
}