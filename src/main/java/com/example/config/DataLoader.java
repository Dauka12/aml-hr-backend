package com.example.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.models.Employee;
import com.example.models.User;
import com.example.repositories.EmployeeRepository;
import com.example.repositories.UserRepository;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner init(PasswordEncoder passwordEncoder, UserRepository userRepository, EmployeeRepository employeeRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                // Создаём сотрудника
                Employee employee = new Employee("John", "Doe", "john.doe@example.com", "IT", 50000.0);
                employeeRepository.save(employee);

                // Создаём пользователя, связанного с сотрудником
                User user = new User("admin", passwordEncoder.encode("admin123"), "ROLE_ADMIN", employee);
                userRepository.save(user);
            }
        };
    }
}
