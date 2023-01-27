import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from './model/user';
import { UserService } from './service/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'User Management';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {
    this.userService = userService;
  }

  ngOnInit() {
    let token = sessionStorage.getItem('token');
    if (token) {
      this.router.navigate(['/users']);
    } else {
      this.router.navigate(['/login']);
    }
  }

  doLogout() {
    this.userService.logout().subscribe(data => {
        sessionStorage.clear();
        this.router.navigate(['/login']);
    });
  }

  isLoggedIn() {
    let token = sessionStorage.getItem('token');
    if (token) {
      return true;
    }
    return false;
  }
}
