import { Component, Inject, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { FormsModule } from "@angular/forms";
import { Employee } from 'src/shared/model/EmployeeModel';
import { EmployeeService } from 'src/shared/service/EmployeeService';
import { ReadKeyExpr } from '@angular/compiler';

export interface DialogData {
  employee: Employee;
  dialogType: String;
}

@Component({
  selector: 'app-employee-dialog',
  templateUrl: './employee-dialog.component.html',
  styleUrls: ['./employee-dialog.component.css']
})


export class EmployeeDialogComponent {
  model!: Employee;
  label!: String;

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogData,
    public dialogRef: MatDialogRef<EmployeeDialogComponent>,
    public employeeService: EmployeeService,
  ) {
  }
  
  ngOnInit(): void {
    switch(this.data.dialogType) {

      case "create":
        // Create empty model
        this.model = {} as Employee;
        this.label = "Enter new employee"
        break;
      case "update":
        // Get emp data and map to model
        this.employeeService.find(this.data.employee.id).subscribe((data: any)=>{
          this.model = data["result"];
          this.label = "Update employee data"
        });
        break;  
      default:
        break;
    }
  }

  save(){
    this.employeeService.update(this.model.id, this.model).subscribe((data: any)=>{
      this.dialogRef.close();
    });
  }

  create(){
    this.employeeService.create(this.model).subscribe((data: any)=>{
      this.dialogRef.close();
    });
  }
}
