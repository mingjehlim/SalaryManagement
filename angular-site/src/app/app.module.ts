import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from "@angular/forms"; 

import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSliderModule } from '@angular/material/slider';
import { NavigationComponent } from './navigation/navigation.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { UploadPageComponent } from './upload-page/upload-page.component';
import { EmployeeDataPageComponent } from './employee-data-page/employee-data-page.component';
import { NotFoundPageComponent } from './not-found-page/not-found-page.component';
import { UploadFieldComponent } from './upload-field/upload-field.component';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { ReactiveFormsModule } from '@angular/forms';
import { AngularFileUploaderModule } from "angular-file-uploader";
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { HttpClientModule } from '@angular/common/http';
import { EmployeeDialogComponent } from './employee-dialog/employee-dialog.component';
import { TextDialogComponent } from './text-dialog/text-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    UploadPageComponent,
    EmployeeDataPageComponent,
    NotFoundPageComponent,
    UploadFieldComponent,
    EmployeeDialogComponent,
    TextDialogComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot([
      {path: 'upload', component: UploadPageComponent},
      {path: 'employee-data', component: EmployeeDataPageComponent},
      {path: '', redirectTo: '/upload', pathMatch: 'full'},
      {path: '**', component: NotFoundPageComponent}
    ]),
    BrowserAnimationsModule,
    MatSliderModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatInputModule,
    MatSelectModule,
    MatRadioModule,
    MatCardModule,
    MatGridListModule,
    ReactiveFormsModule,
    AngularFileUploaderModule,
    MatSnackBarModule,
    HttpClientModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
    MatDialogModule
  ],
  providers: [
    {
      provide: MatDialogRef,
      useValue: {}
    },
    EmployeeDataPageComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
