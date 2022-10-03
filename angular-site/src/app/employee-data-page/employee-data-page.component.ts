//import { Component, OnInit } from '@angular/core';
import {Component, ViewChild, AfterViewInit, Input, ElementRef} from '@angular/core';
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import {MatSort, SortDirection} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { HttpClient } from '@angular/common/http';
import { catchError, map, merge, Observable, of as observableOf, startWith, switchMap } from 'rxjs';

@Component({
  selector: 'app-employee-data-page',
  templateUrl: './employee-data-page.component.html',
  styleUrls: ['./employee-data-page.component.css']
})

export class EmployeeDataPageComponent implements AfterViewInit {
  displayedColumns: string[] = ['id', 'name', 'login', 'salary', 'action'];
  database!: HttpDatabase | null;
  data: Employee[] = [];

  resultsLength = 0;
  isLoadingResults = true;
  prevPageIndex = 0;
  @ViewChild('minSalary') minSalary: ElementRef;
  @ViewChild('maxSalary') maxSalary: ElementRef;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _httpClient: HttpClient,
    minSalary: ElementRef,
    maxSalary: ElementRef) {
      this.minSalary = minSalary;
      this.maxSalary = maxSalary;
    }

  onPaginateChange($event: any){
    this.prevPageIndex = $event.previousPageIndex;
  }

  ngAfterViewInit() {
    this.database = new HttpDatabase(this._httpClient);

    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.database!.getEmployees(
            this.sort.active,
            this.sort.direction,
            this.paginator.pageIndex,
            this.paginator.pageSize,
            this.minSalary.nativeElement.value,
            this.maxSalary.nativeElement.value
          ).pipe(catchError(() => observableOf(null)));
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;

          if (data === null) {
            this.resultsLength = 0;
            return [];
          }

          if (this.prevPageIndex > this.paginator.pageIndex ){
            // Go to prev page
          }
          else {
            // Go to next page
              if (this.resultsLength == 0){
                this.resultsLength =  this.paginator.pageSize + data.result.length;
              }
              else {
                this.resultsLength = (this.paginator.pageIndex + 2) * data.result.length;
              }
          }

          return data.result;
        }),
      )
      .subscribe(data => (this.data = data));
  }

  applyFilter(event: Event) {
    this.ngAfterViewInit();
  }
}

export interface EmployeeData {
  result: Employee[];
  total_count: number;
}

export interface Employee {
  id: string;
  name: string;
  login: string;
  salary: string;
  action: string;
}

/** An example database that the data source uses to retrieve data for the table. */
export class HttpDatabase {
  constructor(private _httpClient: HttpClient) {}

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
    const requestUrl = `${href}?minSalary=${minSalary}&maxSalary=${maxSalary}&offset=${page+1}&limit=${pageSize}&sort=${sortSymbol}${sort}`;

    return this._httpClient.get<EmployeeData>(requestUrl);
  }
}

