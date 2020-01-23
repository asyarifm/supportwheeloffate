package com.asyarifm.supportwheeloffate.model;

import java.time.LocalDate;

public class DaySchedule {

    private LocalDate date;
    private Engineer dayShiftEngineer;
    private Engineer nightShiftEngineer;

    public DaySchedule(LocalDate date, Engineer dayShiftEngineer, Engineer nightShiftEngineer) {
        this.date = date;
        this.dayShiftEngineer = dayShiftEngineer;
        this.nightShiftEngineer = nightShiftEngineer;
    }

    public Engineer getDayShiftEngineer() {
        return dayShiftEngineer;
    }

    public Engineer getNightShiftEngineer() {
        return nightShiftEngineer;
    }

    public LocalDate getDate() {
        return date;
    }
}
