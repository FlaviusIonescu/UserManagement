import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { UpdatePassword } from '../model/update-password';

@Component({
  selector: 'app-user-password',
  templateUrl: './user-password.component.html',
  styleUrls: ['./user-password.component.css']
})
export class UserPasswordComponent {

  updatePass: UpdatePassword;

  constructor(private route: ActivatedRoute,
      private router: Router,
      private userService: UserService) {
    this.updatePass = new UpdatePassword();
    let username = localStorage.getItem('username');
    if (username != null) {
      this.updatePass.username = username;
    } else {
      // TODO - show error
    }
  }

  onSubmit() {
    this.userService.changePassword(this.updatePass).subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }
}
