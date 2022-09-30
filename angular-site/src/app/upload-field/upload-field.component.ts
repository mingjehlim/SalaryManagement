import { Component, ElementRef, ViewChild } from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-upload-field',
  templateUrl: './upload-field.component.html',
  styleUrls: ['./upload-field.component.css']
})

export class UploadFieldComponent {
  constructor(private _snackBar: MatSnackBar) {}
  
  afuConfig = {
    multiple: false,
    formatsAllowed: ".csv",
    uploadAPI:  {
      url: "http://localhost:8080/users/upload",
      headers: {
      },
      params: {
      }
    },
    theme: "",
    hideProgressBar: true,
    hideResetBtn: false,
    hideSelectBtn: false,
    fileNameIndex: false,
    autoUpload: false,
    replaceTexts: {
      selectFileBtn: 'Select Files',
      resetBtn: 'Reset',
      uploadBtn: 'Upload',
      dragNDropBox: 'Drag N Drop',
      attachPinBtn: 'Attach Files...',
      afterUploadMsg_success: 'Successfully Uploaded !',
      afterUploadMsg_error: 'Upload Failed !',
      sizeLimit: 'Size Limit'
    }
  };

  docUpload($event: any){
    let message = "";
    if ($event.status == 200){
      message = $event.body.message;
    }
    else {
      message = $event.error.message;
    }

    this.openSnackBar(message);
  };

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
        duration: 5000
      });
  }
}
