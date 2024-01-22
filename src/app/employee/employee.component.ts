import {Component, Input, OnInit} from '@angular/core';
import {Employee, EmployeeStatus} from "../model/employee";
import {EmployeeService} from "../service/employee.service";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {LoggingService} from "../service/logging.service";
import {NgIf} from "@angular/common";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-employee',
  standalone: true,
  templateUrl: './employee.component.html',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    EmployeeComponent
  ],
  styleUrl: './employee.component.css'
})
export class EmployeeComponent implements OnInit {

  // @ts-ignore
  employeeForm: FormGroup;
  @Input() employee?: Employee
  isNew: boolean = true;

  constructor(private employeeService: EmployeeService,
              private loggingService: LoggingService,
              private router: Router,
              private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
        let param = params['id'];
        this.isNew = param == null;
        this.employee = this.employeeService.getEmployee(+param);
        this.employeeForm = new FormGroup({
        'firstName': new FormControl(this.employee?.firstName, [Validators.required]),
        'lastName': new FormControl(this.employee?.lastName, [Validators.required]),
        'role': new FormControl(this.employee?.role, [Validators.required])
      })
    })
  }

  onSubmit() {
    this.loggingService.logEvent(this.employeeForm)
    this.employeeForm.get('first_name')
    const newEmployee: Employee = {
      firstName: this.employeeForm.get('firstName')?.value,
      lastName: this.employeeForm.get('lastName')?.value,
      role: this.employeeForm.get('role')?.value,
      status: EmployeeStatus.HIRING
    };
    if (this.isNew) {
      this.employeeService.createEmployee(newEmployee)
    } else {
      newEmployee.id = this.employee?.id
      // @ts-ignore
      newEmployee.status = this.employee?.status;
      // @ts-ignore
      this.employeeService.updateEmployee(newEmployee, newEmployee.id)
    }
    this.router.navigate(['/']);
  }
}
