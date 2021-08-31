import { Component } from '@angular/core';
import {AccountService} from "@app/services";
import {User} from "@app/models";

@Component({
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.css']
})
export class HomeComponent {
  user: User | null;

  constructor(private accountService: AccountService) {
    this.user = this.accountService.userValue;
  }
}
