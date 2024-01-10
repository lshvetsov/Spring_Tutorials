package ge.lshvetsov.simplepayroll.config;

import ge.lshvetsov.simplepayroll.controller.EmployeeController;
import ge.lshvetsov.simplepayroll.model.Employee;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeLinkAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    public EntityModel<Employee> toModel (@NonNull Employee employee) {
        EntityModel<Employee> employeeModel = EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all(Optional.of(false))).withRel("employees without dismissed"),
                linkTo(methodOn(EmployeeController.class).all(Optional.of(true))).withRel("employees with dismissed")
        );
        addOperationLinks(employeeModel);
        return employeeModel;
    }

    public CollectionModel<EntityModel<Employee>> toCollectionModel (@NonNull List<Employee> employees) {
        List<EntityModel<Employee>> tempEmpList = employees.stream()
                .map(employee -> EntityModel.of(employee,
                                linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel()))
                .map(this::addOperationLinks)
                .toList();
        return CollectionModel.of(tempEmpList,
                linkTo(methodOn(EmployeeController.class).all(Optional.of(true))).withRel("employees without dismissed"),
                linkTo(methodOn(EmployeeController.class).all(Optional.of(false))).withRel("employees with dismissed")
        );
    }

    private EntityModel<Employee> addOperationLinks (EntityModel<Employee> employeeModel) {
        if (employeeModel.getContent() == null) return employeeModel;
        Employee.Status status = employeeModel.getContent().getStatus();
        if (Employee.Status.HIRING.equals(status)) {
            employeeModel.add(linkTo(methodOn(EmployeeController.class).employ(employeeModel.getContent().getId())).withRel("employ"));
        }
        if (Employee.Status.EMPLOYED.equals(status)) {
            employeeModel.add(linkTo(methodOn(EmployeeController.class).delete(employeeModel.getContent().getId())).withRel("dismiss"));
        }
        return employeeModel;
    }

}
