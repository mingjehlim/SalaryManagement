import { Component, Inject, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { Employee } from 'src/shared/model/EmployeeModel';
import { EmployeeService } from 'src/shared/service/EmployeeService';

@Component({
  selector: 'app-employee-dialog',
  templateUrl: './employee-dialog.component.html',
  styleUrls: ['./employee-dialog.component.css']
})
export class EmployeeDialogComponent {
  model!: Employee;

  constructor(@Inject(MAT_DIALOG_DATA) public data: Employee,
  public dialogRef: MatDialogRef<EmployeeDialogComponent>,
    public employeeService: EmployeeService,
  ) {
  }
  
  ngOnInit(): void {
    this.employeeService.find(this.data.id).subscribe((data: any)=>{
      
      this.model = data["result"];
    });
  }

  save(){
    this.employeeService.update(this.model.id, this.model).subscribe((data: any)=>{
      this.dialogRef.close();
    });
  }

}
