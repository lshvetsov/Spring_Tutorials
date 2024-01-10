package ge.lshvetsov.simplepayroll.repository;

import ge.lshvetsov.simplepayroll.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByStatusIsNot (Employee.Status status);

}
