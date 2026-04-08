package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> sessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, sessions.size());
        Group actualGroup = sessions.get(0).getGroup();
        Assertions.assertEquals("Акробатика для детей", actualGroup.getTitle());
        Assertions.assertEquals(Age.CHILD, actualGroup.getAge());
        Assertions.assertEquals(60, actualGroup.getDuration());

        //Проверить, что за вторник не вернулось занятий
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TimeOfDay time1 = new TimeOfDay(13, 0);
        TimeOfDay time2 = new TimeOfDay(20, 0);

        List<TrainingSession> trainingSessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        Assertions.assertEquals(2, trainingSessions.size());

        TimeOfDay timeOfDay = trainingSessions.get(0).getTimeOfDay();

        Assertions.assertEquals(time1.getHours(), timeOfDay.getHours());
        Assertions.assertEquals(time1.getMinutes(), timeOfDay.getMinutes());

        timeOfDay = trainingSessions.get(1).getTimeOfDay();
        Assertions.assertEquals(time2.getHours(), timeOfDay.getHours());
        Assertions.assertEquals(time2.getMinutes(), timeOfDay.getMinutes());


        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());

    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> trainingSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TimeOfDay time1 = new TimeOfDay(13, 0);
        TimeOfDay timeMonday = trainingSessions.get(0).getTimeOfDay();
        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(time1.getHours(), timeMonday.getHours());
        Assertions.assertEquals(time1.getMinutes(), timeMonday.getMinutes());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        trainingSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertTrue(trainingSessions.isEmpty());
    }

    //Проверка на пустой список трениров
    @Test
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Иванов", "Павел", "Петрович");
        Coach coach3 = new Coach("Петров", "Алексей", "Андреевич");

        Assertions.assertEquals(0, timetable.getCountByCoaches().size());
    }


    // Тест с 1 тренером и 1 тренировкой
    @Test
    void testGetCountByCoachesSingleCoachSingleTraining() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession trainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(trainingSession);


        List<CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();
        CounterOfTrainings actualCoach = counterOfTrainings.get(0);

        Assertions.assertEquals(1, counterOfTrainings.size());
        Assertions.assertEquals("Васильев",actualCoach.getSurname());
        Assertions.assertEquals("Николай",actualCoach.getName());
        Assertions.assertEquals("Сергеевич",actualCoach.getMiddleName());
        Assertions.assertEquals(1,actualCoach.getTrainingCount());
    }

    // Тест с 1 тренером и 3 группами
    @Test
    void testGetCountByCoachesSingleCoachMultipleTrainings(){
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Group group3 = new Group("Силовая подготовка", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group,coach,DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group2,coach,DayOfWeek.MONDAY, new TimeOfDay(16, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group3,coach,DayOfWeek.TUESDAY, new TimeOfDay(9, 0)));

        List<CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();
        CounterOfTrainings result = counterOfTrainings.get(0);

        Assertions.assertEquals(1, counterOfTrainings.size());
        Assertions.assertEquals("Васильев",result.getSurname());
        Assertions.assertEquals("Николай",result.getName());
        Assertions.assertEquals("Сергеевич",result.getMiddleName());
        Assertions.assertEquals(3,result.getTrainingCount());
    }

    // Тест с несколькими тренерами и проверкой сортировки по убыванию количества тренировок
    @Test
    void testGetCountByCoachesMultipleCoachesSortedDescending() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Иванов", "Павел", "Петрович");
        Coach coach3 = new Coach("Петров", "Алексей", "Андреевич");

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Group group3 = new Group("Силовая подготовка", Age.ADULT, 60);

        // coach1 -> 3 тренировки
        timetable.addNewTrainingSession(new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group2, coach1, DayOfWeek.THURSDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group1, coach1, DayOfWeek.TUESDAY, new TimeOfDay(12, 0)));

        // coach3 -> 2 тренировки
        timetable.addNewTrainingSession(new TrainingSession(group3, coach3, DayOfWeek.WEDNESDAY, new TimeOfDay(19, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group3, coach3, DayOfWeek.SATURDAY, new TimeOfDay(11, 0)));

        // coach2 -> 1 тренировка
        timetable.addNewTrainingSession(new TrainingSession(group2, coach2, DayOfWeek.MONDAY, new TimeOfDay(20, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertEquals(3, result.size());

        Assertions.assertEquals("Васильев", result.get(0).getSurname());
        Assertions.assertEquals(3, result.get(0).getTrainingCount());

        Assertions.assertEquals("Петров", result.get(1).getSurname());
        Assertions.assertEquals(2, result.get(1).getTrainingCount());

        Assertions.assertEquals("Иванов", result.get(2).getSurname());
        Assertions.assertEquals(1, result.get(2).getTrainingCount());
    }

}
