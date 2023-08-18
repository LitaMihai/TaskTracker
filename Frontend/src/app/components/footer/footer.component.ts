import { Component } from '@angular/core';
import { SwPush } from '@angular/service-worker';
import { ReminderService } from '../../services/reminder.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent {
  readonly VAPID_PUBLIC_KEY = "BEb0hUIa52hs-GqrSGbEonbk_PWOruKc9C_I9uTg0eEue4uirbbZBsYdZcP5kcjnlfRQfH9Hkc8-bfMOQksjRMw"

  constructor(
    private swPush: SwPush,
    private reminderService: ReminderService
  ) {}

  subscribeToNotifications() {
    this.swPush.requestSubscription({
      serverPublicKey: this.VAPID_PUBLIC_KEY
    })
    .then(sub => this.reminderService.addPushSubscriber(sub).subscribe())
    .catch(err => console.error("Could not subscribe to notifications", err));
  }
}
