package ge.lshvetsov.simplepayroll.controller;

import ge.lshvetsov.simplepayroll.config.EmployeeLinkAssembler;
import ge.lshvetsov.simplepayroll.model.Employee;
import ge.lshvetsov.simplepayroll.repository.EmployeeRepository;
import ge.lshvetsov.simplepayroll.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeService service;
  private final EmployeeRepository repository;
  private final EmployeeLinkAssembler linkAssembler;

  @GetMapping()
  public ResponseEntity<CollectionModel<EntityModel<Employee>>> all(@RequestParam Optional<Boolean> withoutDismissed) {
    List<Employee> employees = service.getEmployees(withoutDismissed.orElse(false));
    return ResponseEntity.ok(linkAssembler.toCollectionModel(employees));
  }

  @GetMapping("/{id}")
  public ResponseEntity<EntityModel<Employee>> one(@PathVariable Long id) {
    return ResponseEntity.ok(linkAssembler.toModel(service.getEmployee(id)));
  }

  @PostMapping()
  public ResponseEntity<EntityModel<Employee>> newEmployee(@RequestBody Employee newEmployee) {
    EntityModel<Employee> employeeModel = linkAssembler.toModel(service.createEmployee(newEmployee));
    return ResponseEntity
      .created(employeeModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
      .body(employeeModel);
  }

  @PutMapping("/{id}")
  public ResponseEntity<EntityModel<Employee>> employ(@PathVariable Long id) {
    return ResponseEntity.ok(linkAssembler.toModel(service.employ(id)));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<EntityModel<Employee>> replaceEmployee (@RequestBody @NonNull Employee newEmployee, @PathVariable Long id) {
    EntityModel<Employee> employeeModel = linkAssembler.toModel(service.replace(newEmployee, id));
    return ResponseEntity
      .created(employeeModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
      .body(employeeModel);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {

    try {
      Employee dismissed = service.dismiss(id);
      return ResponseEntity.ok(linkAssembler.toModel(dismissed));
    } catch (IllegalStateException ex) {
      return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(Problem.create() //
          .withTitle("Method not allowed")
          .withDetail(ex.getMessage()));
    }
  }
}
