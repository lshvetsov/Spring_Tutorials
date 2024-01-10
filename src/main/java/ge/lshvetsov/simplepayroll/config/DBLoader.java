package ge.lshvetsov.simplepayroll.config;

import ge.lshvetsov.simplepayroll.model.Employee;
import ge.lshvetsov.simplepayroll.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DBLoader {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repo) {
        return args -> {
            log.info("Preloading " + repo.save(new Employee("Bilbo", "Baggins", "burglar", Employee.Status.EMPLOYED)));
            log.info("Preloading " + repo.save(new Employee("Frodo", "Baggins", "thief", Employee.Status.EMPLOYED)));
        };
    }

}
