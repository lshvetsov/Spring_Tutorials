package ge.lshvetsov.simplepayroll.controller;

import ge.lshvetsov.simplepayroll.exceptions.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class EmployeeAdvice {

    @ExceptionHandler({EmployeeNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    String employeeNotFound(EmployeeNotFoundException exception) {
        return exception.getMessage();
    }

}
