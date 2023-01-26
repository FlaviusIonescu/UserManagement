import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import { UpdatePassword } from '../model/update-password';

@Injectable()
export class UserService {

  private usersUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:9000/users';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public save(user: User) {
    return this.http.post<User>(this.usersUrl, user);
  }

  public toggle(username: string) {
    return this.http.post<User>(this.usersUrl  + '/toggle', username);
  }

  public delete(username: string) {
    let params = new HttpParams();
    params = params.append('username', username);
    return this.http.delete<User>(this.usersUrl + '/delete', { params: params });
  }

  public changePassword(pass: UpdatePassword) {
    return this.http.post<User>(this.usersUrl  + '/change', pass);
  }
}
