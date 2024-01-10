package ge.lshvetsov.simplepayroll.service;

import ge.lshvetsov.simplepayroll.exceptions.EmployeeNotFoundException;
import ge.lshvetsov.simplepayroll.model.Employee;
import ge.lshvetsov.simplepayroll.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository repository;

  @Override
  public List<Employee> getEmployees(boolean withoutDismissed) {
    List<Employee> employees;

    if (withoutDismissed) employees = repository.findByStatusIsNot(Employee.Status.DISMISSED);
    else employees = repository.findAll();

    if (employees.isEmpty()) throw new EmployeeNotFoundException();
    return employees;
  }

  @Override
  public Employee getEmployee(Long id) {
    return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
  }

  @Override
  public Employee createEmployee(Employee newEmployee) {
    newEmployee.setStatus(Employee.Status.HIRING);
    return repository.save(newEmployee);
  }

  @Override
  public Employee employ(Long id) {
    Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    employee.setStatus(Employee.Status.EMPLOYED);
    return repository.save(employee);
  }

  @Override
  public Employee replace(Employee newEmployee, Long id) {
    Employee employee = repository.findById(id).map(emp -> {
      emp.setName(newEmployee.getName());
      emp.setRole(newEmployee.getRole());
      emp.setStatus(emp.getStatus() == null ? Employee.Status.HIRING : emp.getStatus());
      return emp;
    }).orElseGet(() -> {
      newEmployee.setId(id);
      newEmployee.setStatus(Employee.Status.HIRING);
      return newEmployee;
    });
    return repository.save(employee);

  }

  @Override
  public Employee dismiss(Long id) {
    Employee employee = repository.findById(id)
      .orElseThrow(() -> new EmployeeNotFoundException(id));

    if (Employee.Status.EMPLOYED.equals(employee.getStatus())) {
      employee.setStatus(Employee.Status.DISMISSED);
      return repository.save(employee);
    }

    if (Employee.Status.HIRING.equals(employee.getStatus())) {
      repository.deleteById(id);
      employee.setStatus(Employee.Status.DISMISSED);
      return employee;
    }

    throw new IllegalStateException("Impossible to dismiss the employee that has been dismissed");
  }
}
