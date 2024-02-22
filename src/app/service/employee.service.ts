import {Injectable} from "@angular/core";
import {LoggingService} from "./logging.service";
import {Employee, EmployeeStatus} from "../model/employee";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, map, Observable, tap, throwError} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class EmployeeService {

  employees:Employee[] = [];
  private url:string = environment.apiUrl
  private password:string = environment.password

  constructor(private loggingService: LoggingService, private http: HttpClient) {}

  getEmployee(id: number): Employee {
    return this.employees.find(employee => employee.id === id) as Employee;
  }

  updateEmployees(): Observable<Employee[]> {
    this.loggingService.logEvent('Fetch employees')
    return this.http.get<{ _embedded: { employeeList: Employee[]}}>(
      this.url,
      {
        headers: new HttpHeaders({'Authorization': this.getAuthHeader(this.password)}),
        withCredentials: true}
    )
      .pipe(
        tap(responseData => this.loggingService.logEvent(responseData)),
        map(responseData => this.employees = responseData._embedded.employeeList.map(
            employee => {
              employee.status = mapStatusToEnum(employee.status)
              return employee
            }
        )),
        catchError(error => throwError(() => error))
    )
  }

  getEmployees(withoutDismissed: boolean): Employee[] {
    if(this.employees) {
      if (withoutDismissed) {
        return this.employees.filter(employee => {
          return employee.status !== EmployeeStatus.DISMISSED
        })
      }
      else return this.employees
    }
    else return[]
  }

  createEmployee(employee: Employee) {
    this.loggingService.logEvent('Creating a candidate')
    this.http.post<Employee>(this.url, employee, {
      observe: 'response',
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': this.getAuthHeader(this.password),
        'X-XSRF-TOKEN': this.getToken()
      }),
      withCredentials: true
    }).pipe(
      tap(responseData => this.loggingService.logEvent(responseData)),
      map(response => {
        let employee = response.body;
        if (employee) {
          this.employees.push(employee)
        }
      }),
      catchError(error => throwError(() => error))
    ).subscribe()
  }

  hireEmployee(newEmployee: Employee) {
    this.loggingService.logEvent(`Hiring the candidate with ${newEmployee.id}`)
    const url = `${this.url}/${newEmployee.id}`
    this.http.put<Employee>(url, newEmployee, {
      observe: 'response'
    }).pipe(
      tap(responseData => this.loggingService.logEvent(responseData)),
      map(response => {
        let newEmployee = response.body;
        if (newEmployee) {
          // @ts-ignore
          let number = this.employees.findIndex(employee => employee.id === newEmployee.id);
          this.employees[number] = newEmployee
        }
      }),
      catchError(error => throwError(() => error))
    ).subscribe()
  }

  updateEmployee(employee: Employee) {
    this.loggingService.logEvent('Updating an employee')
    const url = `${this.url}/${employee.id}`
    this.http.patch<Employee>(url, employee, {
      observe: 'response',
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': this.url,
        'Authorization': this.getAuthHeader(this.password),
        'X-XSRF-TOKEN': this.getToken()
      })
    }).pipe(
      tap(responseData => this.loggingService.logEvent(responseData)),
      map(response => {
        let employee = response.body;
        if (employee) {
          // @ts-ignore
          this.employees = this.employees.map(
            // @ts-ignore
            currentEmployee => currentEmployee.id === employee.id ? employee : currentEmployee
          )
        }
      }),
      catchError(error => throwError(() => error))
    ).subscribe()
  }

  dismissEmployee(id: number) {
    this.loggingService.logEvent(`Dismissing the employee with ${id}`)
    const url = `${this.url}/${id}`
    this.http.delete<Employee>(url, {
      observe: 'response',
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': this.url,
        'Authorization': this.getAuthHeader(this.password),
        'X-XSRF-TOKEN': this.getToken()
      })
    }).pipe(
      tap(responseData => this.loggingService.logEvent(responseData)),
      map(() => this.employees.filter(employee => employee.id === id).map(
        // @ts-ignore
        employee => employee.status = EmployeeStatus.DISMISSED
      )),
      catchError(error => throwError(() => error))
    ).subscribe()
  }
  private getToken(): string {
      const cookies = document.cookie.split(';');
      for (const cookie of cookies) {
        const [name, value] = cookie.split('=');
        if (name.trim() === 'XSRF-TOKEN') {
          return value.trim();
        }
      }
      return ''
  }

  private getAuthHeader(password: string): string {
    return 'Basic ' + btoa(`${environment.username}:${password}`)
  }
}

function mapStatusToEnum(status: string): EmployeeStatus {
  // Add logic to map the string status to the corresponding EmployeeStatus
  // For example:
  if (status === 'Hiring') {
    return EmployeeStatus.HIRING;
  } else if (status === 'Employed') {
    return EmployeeStatus.EMPLOYED;
  } else if (status === 'Dismissed') {
    return EmployeeStatus.DISMISSED;
  } else {
    // Handle unknown status values
    return EmployeeStatus.HIRING; // Default value or appropriate handling
  }
}


