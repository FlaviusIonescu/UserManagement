import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[] = [];

  constructor(private userService: UserService,
    private router: Router,
    private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.refreshList();
  }

  toggle_user(username: string) {
    this.userService.toggle(username).subscribe(result => this.refreshList());
  }

  delete_user(username: string) {
    this.userService.delete(username).subscribe(result => this.refreshList());
  }

  refreshList() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
    });
  }
}
