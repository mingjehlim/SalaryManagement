import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Employee } from 'src/shared/model/EmployeeModel';
import { EmployeeService } from 'src/shared/service/EmployeeService';

@Component({
  selector: 'app-text-dialog',
  templateUrl: './text-dialog.component.html',
  styleUrls: ['./text-dialog.component.css']
})
export class TextDialogComponent implements OnInit {
  id!: string ;
  
  constructor(@Inject(MAT_DIALOG_DATA) public data: string,
    public dialogRef: MatDialogRef<TextDialogComponent>,
    public employeeService: EmployeeService,
    private _snackBar: MatSnackBar) { 
      
    }

  ngOnInit(): void {
    this.id = this.data;
  }

  delete(id: string) {
      this.employeeService.delete(this.id).subscribe(res => { 
        
      this.openSnackBar(`Employee with id "${id}" was deleted successfully`);
      this.dialogRef.close({ success : true });
    })
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
        duration: 5000
      });
  }
}
