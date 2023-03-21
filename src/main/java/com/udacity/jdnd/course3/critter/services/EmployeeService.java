package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;


    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(long employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = getEmployee(employeeId);
        employee.setDaysAvailable(daysAvailable);
        saveEmployee(employee);
    }

    public List<Employee> checkAvailability(EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeRepository.findDistinctBySkillsInAndDaysAvailable(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());
        return employees.stream().filter(employee ->employee.getSkills().containsAll(employeeDTO.getSkills())).collect(Collectors.toList());
    }

    public List<Employee> findAllById(List<Long> employeeIds) {
       return employeeRepository.findAllById(employeeIds);
    }
}
