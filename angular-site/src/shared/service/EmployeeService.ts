import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
    
import {  Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Employee, EmployeeData } from '../model/EmployeeModel';
import { SortDirection } from '@angular/material/sort';
    
@Injectable({
  providedIn: 'root'
})

export class EmployeeService {
    
  private apiURL = "http://localhost:8080";
    
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }
   
  constructor(private httpClient: HttpClient) { }

  getEmployees(sort: string, order: SortDirection, page: number, pageSize: number, minSalary: number, maxSalary: number): Observable<EmployeeData> {
    let sortSymbol: string;

    if (order == "" || order == "asc" ){
      sortSymbol = "+";
    }
    else {
      sortSymbol = "-";
    }

    minSalary = minSalary == 0 ? 0 : minSalary;
    maxSalary = maxSalary == 0 ? 100000 : maxSalary;

    const href = 'http://localhost:8080/users';
    const requestUrl = `${href}?minSalary=${minSalary}&maxSalary=${maxSalary}&offset=${page}&limit=${pageSize}&sort=${sortSymbol}${sort}`;

    return this.httpClient.get<EmployeeData>(requestUrl);
  }
    
  create(employee: Employee): Observable<Employee> {
    return this.httpClient.post<Employee>(this.apiURL + '/users/', JSON.stringify(employee), this.httpOptions)
    .pipe(
      catchError(this.errorHandler)
    )
  }  
    
  find(id: string): Observable<Employee> {
    return this.httpClient.get<Employee>(`${this.apiURL}/users/${id}`)
    .pipe(
      catchError(this.errorHandler)
    )
  }
    
  update(id: string, employee: Employee): Observable<Employee> {
    return this.httpClient.patch<Employee>(this.apiURL + '/users/' + id, JSON.stringify(employee), this.httpOptions)
    .pipe(
      catchError(this.errorHandler)
    )
  }
    
  delete(id: string){
    return this.httpClient.delete<Employee>(this.apiURL + '/users/' + id, this.httpOptions)
    .pipe(
      catchError(this.errorHandler)
    )
  }
     
   
  errorHandler(error: { error: { message: string; }; status: any; message: any; }) {
    let errorMessage = '';
    if(error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(errorMessage);
 }
}