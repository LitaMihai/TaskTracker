import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Task } from 'src/app/Task';
import { UiService } from 'src/app/services/ui.service';
import { Subscription } from 'rxjs';

import {
  trigger,
  state,
  style,
  animate,
  transition
} from '@angular/animations';

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css'],
  animations: [
    trigger("inOutAnimation", [
      transition(":enter", [
        style({ height: 0, opacity: 0 }),
        animate("200ms ease-out", style({ height: 200, opacity: 1 })),
      ]),
      transition(":leave", [
        style({ height: 200, opacity: 1 }),
        animate("200ms ease-in", style({ height: 0, opacity: 0 })),
      ]),
    ]),
  ],
})
export class AddTaskComponent implements OnInit {
  @Output() onAddTask: EventEmitter<Task> = new EventEmitter();

  text!: string;
  date!: string;
  reminder: boolean = false;
  showAddTask: boolean = false;
  subscription!: Subscription;

// Date formats
  months = [
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "May",
    "Jun",
    "Jul",
    "Aug",
    "Sep",
    "Oct",
    "Nov",
    "Dec",
  ];
  days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

  constructor(private uiService: UiService) {
    this.subscription = this.uiService
    .onToggle()
    .subscribe((value) => (this.showAddTask = value));
  }

  ngOnInit(): void {}

  onSubmit() {
    if (this.text && this.date) {
      // Date formats (optional)
      let dateTmp = new Date(this.date);
      let dayName = this.days[dateTmp.getDay()];
      let dayNumber = dateTmp.getDate();
      let monthName = this.months[dateTmp.getMonth()];
      let year = dateTmp.getFullYear();
      let hour = `${String(dateTmp.getHours()).padStart(2, "0")}:${String(
        dateTmp.getMinutes()
      ).padStart(2, "0")}`;
      let formattedTime = `${dayName}, ${dayNumber} ${monthName} ${year} at ${hour}`;

      // Create new Task object
      const newTaskObj = {
        text: this.text,
        day: formattedTime,
        reminder: this.reminder,
      };

      // Emit event
      this.onAddTask.emit(newTaskObj);

      // Clear form after submit
      this.text = "";
      this.date = "";
      this.reminder = false;
    }
  }
}
