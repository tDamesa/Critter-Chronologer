package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule saveSchedule(Schedule schedule) {
       return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesByPetId(long petId) {
        return scheduleRepository.findAllByPetId(petId);
    }

    public List<Schedule> getScheduleByEmployeeId(long employeeId) {
        return scheduleRepository.findAllByEmployeeId(employeeId);
    }

    public List<Schedule> getSchedulesByOwnerId(long ownerId) {
        return scheduleRepository.findSchedulesByOwnerId(ownerId);
    }
}
