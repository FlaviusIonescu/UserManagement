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
  sidebarActive = false;

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
      this.router.navigate(['/home']);
    } else {
      this.router.navigate(['/login']);
    }
  }

  get isActive() {
    return this.sidebarActive;
  }

  public toggleSideBar() {
    this.sidebarActive = !this.sidebarActive;
  }

  doLogout() {
    this.userService.logout().subscribe(data => {
      sessionStorage.clear();
      this.router.navigate(['/login']);
    });
  }

  isLoggedIn() {
    return this.userService.isLoggedIn()
  }

  getUserName() {
    let token = sessionStorage.getItem('token');
    if (token) {
      return token;
    }
    return '';
  }
}
