import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppComponent } from './app.component';
import { UserListComponent } from './user-list/user-list.component';
import { UserFormComponent } from './user-form/user-form.component';
import { UserService } from './service/user.service';
import { UserPasswordComponent } from './user-password/user-password.component';
import { LoginComponent } from './login/login.component';
import { CustomInterceptor } from './service/custom-interceptor.service';
import { CredentialsComponent } from './credentials/credentials.component';


@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    UserFormComponent,
    UserPasswordComponent,
    LoginComponent,
    CredentialsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    CredentialsComponent,
    UserService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CustomInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
