import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'User Management';
  
  
  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) { 

  }

  ngOnInit() {
   // let token = sessionStorage.getItem('token');
 //   if (token == '') {
      this.router.navigate(['/login']);
   // } else {
  //    this.router.navigate(['/users']);
 ///   }
  }
}
