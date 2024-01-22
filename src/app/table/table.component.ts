import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Employee, EmployeeStatus} from "../model/employee";
import {EmployeeService} from "../service/employee.service";
import {FormsModule} from "@angular/forms";
import {RouterLink} from "@angular/router";
import {NgForOf, NgIf} from "@angular/common";
import {catchError, map, tap, throwError} from "rxjs";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-table',
  standalone: true,
  templateUrl: './table.component.html',
  imports: [
    FormsModule,
    RouterLink,
    NgForOf,
    NgIf
  ],
  styleUrl: './table.component.css'
})
export class TableComponent implements OnInit, OnDestroy {
  employees: Employee[] = []

  constructor(private service: EmployeeService, private appComponent: AppComponent) {
  }

  ngOnInit() {
    this.service.updateEmployees().subscribe(
      responseData => this.employees = responseData
    )
    this.appComponent.withoutDismissedChange.subscribe(value => {
      this.employees = this.service.getEmployees(value)
      console.log(this.employees)
    })
  }

  hireEmployee(employee: Employee){
    this.service.hireEmployee(employee)
  }

  dismissEmployee(employee: Employee){
    // @ts-ignore
    this.service.dismissEmployee(employee.id);
  }

  ngOnDestroy() {
    this.appComponent.withoutDismissedChange.unsubscribe();
  }

  protected readonly EmployeeStatus = EmployeeStatus;
}
