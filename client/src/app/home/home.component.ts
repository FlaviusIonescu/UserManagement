import { Component } from '@angular/core';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  constructor(
    private userService: UserService) {
  }

  isLoggedIn() {
    return this.userService.isLoggedIn()
  }

}
