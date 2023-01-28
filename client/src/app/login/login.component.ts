import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { EventsService } from '../service/events.service';
import { CREDENTIAL_ACTIONS } from '../credentials/credentials.component';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [
    {
      provide: CREDENTIAL_ACTIONS,
      useValue: "Login"
    }]
})


export class LoginComponent implements OnInit {

  constructor(
    private router: Router,
    private userService: UserService,
    private eventService: EventsService
  ) {

  }


  ngOnInit() {
    sessionStorage.setItem('token', '');
    this.eventService.subscribeLoginComponent("login", this);

  }

  doLogin(username: string, password: string) {
    let user: User = new User();
    user.username = username;
    user.password = password;
    this.userService.login(user).subscribe(data => {
      if (data) {
        sessionStorage.setItem(
          'token',
          user.username
        );
        this.router.navigate(['/users']);
      } else {
        alert('Authentication failed.')
      }
    });
  }

}