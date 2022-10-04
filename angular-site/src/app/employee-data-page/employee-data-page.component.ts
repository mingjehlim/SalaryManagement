//import { Component, OnInit } from '@angular/core';
import {Component, ViewChild, AfterViewInit, Input, ElementRef} from '@angular/core';
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import {MatSort, SortDirection} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { HttpClient } from '@angular/common/http';
import { catchError, map, merge, Observable, of as observableOf, startWith, switchMap } from 'rxjs';
import { EmployeeService } from 'src/shared/service/EmployeeService';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Employee } from 'src/shared/model/EmployeeModel';
import { EmployeeDialogComponent } from '../employee-dialog/employee-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { TextDialogComponent } from '../text-dialog/text-dialog.component';

@Component({
  selector: 'app-employee-data-page',
  templateUrl: './employee-data-page.component.html',
  styleUrls: ['./employee-data-page.component.css']
})

export class EmployeeDataPageComponent implements AfterViewInit {
  displayedColumns: string[] = ['id', 'name', 'login', 'salary', 'action'];
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
    public employeeService: EmployeeService,
    private _snackBar: MatSnackBar,
    public dialog: MatDialog,
    minSalary: ElementRef,
    maxSalary: ElementRef) {
      this.minSalary = minSalary;
      this.maxSalary = maxSalary;
    }

  onPaginateChange($event: any){
    this.prevPageIndex = $event.previousPageIndex;
  }

  ngAfterViewInit() {
    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.employeeService.getEmployees(
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

  find(data : Employee) {
    this.dialog.open(EmployeeDialogComponent, {
        data
    });
  }

  delete(id : string){
    const dialogRef = this.dialog.open(TextDialogComponent, {
      data: id
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.success){
        this.data = this.data.filter(item => item.id !== id);
      }
    });
  }
}