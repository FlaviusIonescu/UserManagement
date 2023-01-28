import { EventEmitter, Injectable, Output } from '@angular/core';
import { LoginComponent } from '../login/login.component';
import { UserFormComponent } from '../user-form/user-form.component';


@Injectable({
  providedIn: 'root'
})
export class EventsService {

  private observersMap = new Map<string, number>();
  constructor() { }

  @Output() credentialsEvent = new EventEmitter<string>();

  public subscribeLoginComponent(obsId: string, login: LoginComponent) {
    if (!this.observersMap.has(obsId)) {
      this.observersMap.set(obsId, 1);
      this.credentialsEvent.subscribe((msg: string) => {
          let arr: string[] = msg.split(",");
          if (arr[0] == "Login") {
            login.doLogin(arr[1], arr[2]);
          }
        });
    }
  }

  public subscribeAddUserComponent(obsId: string, addUserForm: UserFormComponent) {
    if (!this.observersMap.has(obsId)) {
      this.observersMap.set(obsId, 1);
      this.credentialsEvent.subscribe((msg: string) => {
          let arr: string[] = msg.split(",");
          if (arr[0] == "Create User") {
            addUserForm.doAddUser(arr[1], arr[2]);
          }
        });
    }
  }

  public credentialsEventEmit(msg: string) {
    this.credentialsEvent.emit(msg);
  }
}
