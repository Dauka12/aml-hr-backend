package com.example.service;

import com.example.dto.TaskRequest;
import com.example.models.Task;
import com.example.repositories.EmployeeRepository;
import com.example.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public Task createTask(TaskRequest taskRequest) {

        employeeRepository.findById(taskRequest.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден!"));

        Task task = new Task(taskRequest.getTitle(), taskRequest.getDescription(), taskRequest.getStatus(), taskRequest.getEmployeeId());

        return taskRepository.save(task);
    }
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }
    public Task findTaskById(Long id) {
        return taskRepository.findById(id).get();
    }
    public Task updateTaskById(Long id, TaskRequest taskRequest, @RequestBody Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());

        return taskRepository.save(task);
    }
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

}
