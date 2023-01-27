import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../model/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  user: User = new User();

  constructor(
      private route: ActivatedRoute,
      private router: Router,
      private userService: UserService
  ) { }

  ngOnInit() {
      sessionStorage.setItem('token', '');
  }

  doLogin() {
    this.userService.login(this.user).subscribe(data => {
      if (data) {
        sessionStorage.setItem(
          'token',
          this.user.username
        );
        this.router.navigate(['/users']);
      } else {
        alert('Authentication failed.')
      } 
    });
  }
      
}