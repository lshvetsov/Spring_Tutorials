export interface Employee {
  id?: number
  firstName: string
  lastName: string
  role: string
  status: EmployeeStatus
}

export enum EmployeeStatus {
  HIRING = 'HIRING',
  EMPLOYED = 'EMPLOYED',
  DISMISSED = 'DISSMISSED'
}
