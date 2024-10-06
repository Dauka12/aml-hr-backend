package com.example.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Employee;
import com.example.repositories.EmployeeRepository;
import com.example.repositories.TaskRepository;


@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public EmployeeController(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable Long id) {
        // Находим сотрудника
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        // Если сотрудник найден, добавляем его задачи
        employeeOptional.ifPresent(employee -> {
            List<Long> taskIds = taskRepository.findAll().stream()
                .filter(task -> task.getEmployeeId().equals(employee.getId())) // Фильтруем задачи по employeeId
                .map(task -> task.getId()) // Собираем только ID задач
                .collect(Collectors.toList());
            
            employee.setTaskIds(taskIds); // Добавляем список ID задач
        });

        return employeeOptional;
    }
 
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден"));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setSalary(employeeDetails.getSalary());

        return employeeRepository.save(employee);
    }
    
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }
    
}