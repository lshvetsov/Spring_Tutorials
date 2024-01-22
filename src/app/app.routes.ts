import { Routes } from '@angular/router';
import {AppComponent} from "./app.component";
import {TableComponent} from "./table/table.component";
import {EmployeeComponent} from "./employee/employee.component";

export const routes: Routes = [
  {path: '', component: TableComponent},
  {path: 'edit/:id', component: EmployeeComponent},
  {path: 'create', component: EmployeeComponent}
];
