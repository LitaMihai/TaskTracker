import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent {
  @Input() text!: string;
  @Input() color: string = 'green';
  @Output() btnClickEvent = new EventEmitter();
  
  constructor() { }

  ngOnInit(): void {}

  onClickEvent() {
    this.btnClickEvent.emit();
  }
}
