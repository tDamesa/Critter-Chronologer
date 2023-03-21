package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s JOIN s.pets p WHERE p.id = :petId")
    List<Schedule> findAllByPetId(long petId);

    @Query("SELECT s FROM Schedule s JOIN s.employees e WHERE e.id = :employeeId")
    List<Schedule> findAllByEmployeeId(long employeeId);

    @Query("SELECT s FROM Schedule s JOIN s.pets p WHERE p.owner.id = :ownerId")
    List<Schedule> findSchedulesByOwnerId(long ownerId);

    List<Schedule> findByEmployeesContaining(Employee employee);
}
