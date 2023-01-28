import { Component, Inject, Injectable, InjectionToken } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { EventsService } from '../service/events.service';

export const CREDENTIAL_ACTIONS = new InjectionToken<string>('action');

@Component({
  selector: 'app-credentials',
  templateUrl: './credentials.component.html',
  styleUrls: ['./credentials.component.css']
})

@Injectable({
  providedIn: 'root'
})

export class CredentialsComponent {

  constructor(
    @Inject(CREDENTIAL_ACTIONS) public action: string,
    private eventsService: EventsService
  ) {

  }


  loginForm: FormGroup = new FormGroup({
    email: new FormControl('', [
      Validators.required,
      Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    password: new FormControl('')
  });


  sendCredentials() {
    this.eventsService.credentialsEventEmit(
      this.action
      + ","
      + this.loginForm.value.email
      + ","
      + this.loginForm.value.password
    );
  }

  get getEmail() {
    return this.loginForm.get('email');
  }

}