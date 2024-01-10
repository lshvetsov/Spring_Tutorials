package ge.lshvetsov.simplepayroll.exceptions;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Employee can't be found: id " + id);
    }

    public EmployeeNotFoundException() {
        super("Employees can't be found");
    }

}
