package ge.lshvetsov.simplepayroll.service;

import ge.lshvetsov.simplepayroll.model.Employee;

import java.util.List;

public interface EmployeeService {

  List<Employee> getEmployees (boolean withoutDismissed);
  Employee getEmployee(Long id);
  Employee createEmployee(Employee newEmployee);
  Employee employ(Long id);
  Employee replace(Employee newEmployee, Long id);
  Employee dismiss(Long id);


}
