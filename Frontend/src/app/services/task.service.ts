import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Task } from '../Task';
import { TASKS } from '../mock-tasks';
import { Observable, of } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json"
  }),
};

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiURL = 'http://localhost:8080/tasks';

  constructor(private httpClient: HttpClient) { }

  getTasks(): Observable<Task[]> {
    return this.httpClient.get<Task[]>(this.apiURL+"/all");
  }

  deleteTask(task: Task): Observable<Task> {
    const url = this.apiURL + '/delete/' + task.id;
    return this.httpClient.delete<Task>(url);
  }

  updateTaskReminder(task: Task): Observable<Task> {
    const url = this.apiURL + '/update/' + task.id;
    return this.httpClient.put<Task>(url, task, httpOptions);
  }

  addTask(task: Task): Observable<Task> {
    return this.httpClient.post<Task>(this.apiURL + "/add", task, httpOptions);
  }
}
