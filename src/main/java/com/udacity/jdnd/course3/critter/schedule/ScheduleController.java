package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return convertEntityToScheduleDTO( scheduleService.saveSchedule(convertScheduleDTOTOToEntity(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
       List<Schedule> schedules = scheduleService.getAllSchedules();
        return schedules.stream().map(schedule -> convertEntityToScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getSchedulesByPetId(petId);
        return schedules.stream().map(schedule -> convertEntityToScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleByEmployeeId(employeeId);
        return schedules.stream().map(schedule -> convertEntityToScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getSchedulesByOwnerId(customerId);
        return schedules.stream().map(schedule -> convertEntityToScheduleDTO(schedule)).collect(Collectors.toList());
    }

    private ScheduleDTO convertEntityToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        List<Pet> pets = schedule.getPets();
        List<Employee> employees = schedule.getEmployees();
        if(pets!= null && !pets.isEmpty()){
            List<Long> petIds = pets.stream().map(Pet::getId).collect(Collectors.toList());
            scheduleDTO.setPetIds(petIds);
        }
        if(employees!= null && !employees.isEmpty()){
            List<Long> employeeIds = employees.stream().map(Employee::getId).collect(Collectors.toList());
            scheduleDTO.setEmployeeIds(employeeIds);
        }
        BeanUtils.copyProperties(schedule, scheduleDTO);
        return scheduleDTO;
    }

    private Schedule convertScheduleDTOTOToEntity(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        List<Pet> pets = petService.findAllById(scheduleDTO.getPetIds());
        List<Employee> employees = employeeService.findAllById(scheduleDTO.getEmployeeIds());
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }
}
