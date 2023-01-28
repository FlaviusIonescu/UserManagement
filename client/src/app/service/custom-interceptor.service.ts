import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, map, Observable, tap } from 'rxjs';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})

export class CustomInterceptor implements HttpInterceptor {

  private userservice: UserService;
  constructor(
    private router: Router,
    userserice: UserService) {
    this.userservice = userserice;
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authReq = req.clone({ withCredentials: true });
    authReq.headers.set('Content-Type', 'application/json')
    return next.handle(authReq).pipe(tap(() => { },
      (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status !== 401) {
            alert(err.error)
            sessionStorage.clear();
            return;
          }
          sessionStorage.clear();
          alert("Unauthorized")
          this.router.navigate(['/login']);
        }
      }));

  }

}