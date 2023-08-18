import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ReminderService {

  private apiURL = 'http://localhost:8080/reminder';

  constructor(private http: HttpClient) { }

  addPushSubscriber(sub: any) {
    return this.http.post(this.apiURL + '/notifications', sub);
  }
}
