import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

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
import { ReactiveFormsModule } from '@angular/forms';
import { AngularFileUploaderModule } from "angular-file-uploader";
import { MatSnackBarModule } from '@angular/material/snack-bar';


@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    UploadPageComponent,
    EmployeeDataPageComponent,
    NotFoundPageComponent,
    UploadFieldComponent
  ],
  imports: [
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
    ReactiveFormsModule,
    AngularFileUploaderModule,
    MatSnackBarModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
