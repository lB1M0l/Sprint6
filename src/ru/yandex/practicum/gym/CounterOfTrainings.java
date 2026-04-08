package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    //фамилия
    private String surname;
    //имя
    private String name;
    //отчество
    private String middleName;

    private int trainingCount;

    public CounterOfTrainings(String surname, String name, String middleName, int trainingCount) {
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.trainingCount = trainingCount;
    }

    public int getTrainingCount() {
        return trainingCount;
    }


    @Override
    public int compareTo(CounterOfTrainings o) {
        return Integer.compare(trainingCount, o.trainingCount);
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) o;
        return trainingCount == that.trainingCount && Objects.equals(surname, that.surname) && Objects.equals(name, that.name) && Objects.equals(middleName, that.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, middleName, trainingCount);
    }

    @Override
    public String toString() {
        return "CounterOfTrainings{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", trainingCount=" + trainingCount +
                '}';
    }
}
