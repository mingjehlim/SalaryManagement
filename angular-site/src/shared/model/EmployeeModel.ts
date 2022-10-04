export interface EmployeeData {
    result: Employee[];
    total_count: number;
  }
  
export interface Employee {
    [x: string]: any;
    id: string;
    name: string;
    login: string;
    salary: string;
    action: string;
}