import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { EventsService } from '../service/events.service';
import { CREDENTIAL_ACTIONS } from '../credentials/credentials.component';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css'],
  providers: [
    {
      provide: CREDENTIAL_ACTIONS,
      useValue: "Create User"
    }]
})
export class UserFormComponent implements OnInit {

  constructor(
      private router: Router,
      private eventService: EventsService,
      private userService: UserService) {
  }


  ngOnInit() {
    this.eventService.subscribeAddUserComponent("Create user", this);
  }


  public doAddUser(username: string, password: string) {
    var user: User = new User();
    user.username = username;
    user.password = password;
    this.userService.save(user).subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }
}
