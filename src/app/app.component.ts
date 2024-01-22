import {Component, EventEmitter, Output} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink, RouterOutlet} from '@angular/router';
import {EmployeeService} from "./service/employee.service";
import {FormsModule} from "@angular/forms";
import {TableComponent} from "./table/table.component";
import {EmployeeComponent} from "./employee/employee.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, FormsModule, RouterLink, TableComponent, EmployeeComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  withoutDismissed: boolean = false;
  @Output() withoutDismissedChange = new EventEmitter<boolean>();

  constructor(private service: EmployeeService) {
  }

  onWithDismissedChange() {
    this.withoutDismissedChange.emit(this.withoutDismissed);
  }
}
