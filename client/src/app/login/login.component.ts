import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  user: User = new User();

  loginForm:FormGroup =  new FormGroup({
    email: new FormControl('',[
      Validators.required,
      Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    password: new FormControl('')
    });


  constructor(
      private route: ActivatedRoute,
      private router: Router,
      private userService: UserService
  ) {
    
   }

get getEmail(){
  return this.loginForm.get('email');
 }

  ngOnInit() {
      sessionStorage.setItem('token', '');
  }

  doLogin() {
    this.user.username = this.loginForm.value.email || '';
    this.user.password = this.loginForm.value.password || '';
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