package ru.yandex.practicum.gym;

import java.util.*;

//Класс для хранения и быстрого поиска расписания тренировок по дню недели и времени.
public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        timetable.computeIfAbsent(day, k -> new TreeMap<>())
                .computeIfAbsent(time, k -> new ArrayList<TrainingSession>())
                .add(trainingSession);


    }

    //Получение тренировок в определенный день
    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> map = timetable.get(dayOfWeek);
        if (map == null) {
            return new ArrayList<>();
        }
        List<TrainingSession> result = new ArrayList<>();
        for (List<TrainingSession> trainingSessions : map.values()) {
            result.addAll(trainingSessions);
        }
        return result;


    }

    //Получение тренировок в определенный день и время
    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);
        if (daySchedule == null) {
            return new ArrayList<>();
        }
        return daySchedule.getOrDefault(timeOfDay, new ArrayList<>());
    }

    //Подсчет тренировок по тренеру
    public List<CounterOfTrainings> getCountByCoaches() {
        if(timetable.isEmpty()){
            return new ArrayList<>();
        }
        HashMap<Coach, Integer> coachCounts = new HashMap<>();
        List<CounterOfTrainings> listCounterOfTrainings = new ArrayList<>();

        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> entry : timetable.entrySet()) {
            for (Map.Entry<TimeOfDay, List<TrainingSession>> entry2 : entry.getValue().entrySet()) {
                for (TrainingSession trainingSession : entry2.getValue()) {
                    Coach coach = trainingSession.getCoach();
                    //String coachKey = coach.getSurname() + " " + coach.getName() + " " + coach.getMiddleName();
                    coachCounts.put(coach, coachCounts.getOrDefault(coach, 0) + 1);
                }
            }
        }

        for (Map.Entry<Coach, Integer> entry : coachCounts.entrySet()) {
            Coach coach = entry.getKey();
            listCounterOfTrainings.add(new CounterOfTrainings(coach.getSurname(), coach.getName(), coach.getMiddleName(), entry.getValue()));
        }

        listCounterOfTrainings.sort(Collections.reverseOrder());
        return listCounterOfTrainings;

    }
}
