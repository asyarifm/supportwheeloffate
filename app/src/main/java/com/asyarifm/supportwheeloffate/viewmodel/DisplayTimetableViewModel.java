package com.asyarifm.supportwheeloffate.viewmodel;


import com.asyarifm.supportwheeloffate.model.DaySchedule;
import com.asyarifm.supportwheeloffate.model.Engineer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class DisplayTimetableViewModel {

    private List<Engineer> genEligibleEngineers(List<Engineer> engineerList, List<DaySchedule> scheduleList, int workloadLimit) {
        List<Engineer>  eligibleEngineers = new ArrayList<>();

        //check engineer workload
        for (Engineer each : engineerList) {
            if (each.getWorkload() < workloadLimit) {
                eligibleEngineers.add(each);
            }
        }

        //check previous day schedule
        if (scheduleList.size() > 0) {
            DaySchedule previousDaySchedule = scheduleList.get(scheduleList.size() - 1);
            if (eligibleEngineers.contains(previousDaySchedule.getDayShiftEngineer())) {
                eligibleEngineers.remove(previousDaySchedule.getDayShiftEngineer());
            }

            if (eligibleEngineers.contains(previousDaySchedule.getNightShiftEngineer())) {
                eligibleEngineers.remove(previousDaySchedule.getNightShiftEngineer());
            }
        }

        return eligibleEngineers;
    }

    //please ensure limitworkload >= numOfWeek
    public ArrayList<DaySchedule> genWeeksTimetable(List<Engineer> engineerList, int numOfWeek, int limitWorkload) {
        ArrayList<DaySchedule> scheduleList = new ArrayList<DaySchedule>();
        Engineer dayShiftEngineer = null;
        Engineer nightShiftEngineer = null;

        LocalDate currentDate = LocalDate.now();
        LocalDate startSchedule = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        int numberOfDays = 7 * numOfWeek; // 2 weeks
        for (int i = 0; i < numberOfDays; i++) {
            LocalDate shiftDate = startSchedule.plusDays(i);
            if (shiftDate.getDayOfWeek() != DayOfWeek.SATURDAY && shiftDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                Random random = new Random();

                List<Engineer> eligibleEngineers = genEligibleEngineers(engineerList, scheduleList, limitWorkload);

                if (eligibleEngineers.size() > 0) {
                    //sort and separate engineer with minimum workload
                    eligibleEngineers.sort(Comparator.comparing(Engineer::getWorkload));
                    int minWorkload = eligibleEngineers.get(0).getWorkload();
                    List<Engineer> minWorkloadEngineer = new ArrayList<>();
                    for (Engineer each:eligibleEngineers) {
                        if (each.getWorkload() == minWorkload) {
                            minWorkloadEngineer.add(each);
                        }
                    }
                    eligibleEngineers.removeAll(minWorkloadEngineer);

                    //ensure engineer with min workload is choosen
                    if (minWorkloadEngineer.size() > 0) {
                        dayShiftEngineer = minWorkloadEngineer.get(random.nextInt(minWorkloadEngineer.size()));
                        minWorkloadEngineer.remove(dayShiftEngineer);
                    } else {
                        dayShiftEngineer = eligibleEngineers.get(random.nextInt(eligibleEngineers.size()));
                        eligibleEngineers.remove(dayShiftEngineer);
                    }

                    if(minWorkloadEngineer.size() > 0) {
                        nightShiftEngineer = minWorkloadEngineer.get(random.nextInt(minWorkloadEngineer.size()));
                        minWorkloadEngineer.remove(nightShiftEngineer);
                    } else {
                        nightShiftEngineer = eligibleEngineers.get(random.nextInt(eligibleEngineers.size()));
                        eligibleEngineers.remove(nightShiftEngineer);
                    }

                    //update workload
                    engineerList.get(engineerList.indexOf(dayShiftEngineer)).increaseWorkload();
                    engineerList.get(engineerList.indexOf(nightShiftEngineer)).increaseWorkload();

                    scheduleList.add(new DaySchedule(startSchedule.plusDays(i), dayShiftEngineer, nightShiftEngineer));
                }
            }
        }

        return scheduleList;
    }
}
