import {Component, OnInit} from '@angular/core';

import { AccountService } from '@app/services';
import { User } from '@app/models';
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Flight Advisor Service';
  user?: User | null;
  travelForm?: FormGroup;

  constructor(private accountService: AccountService,
              private formBuilder: FormBuilder) {
    this.accountService.user.subscribe(x => this.user = x);
  }

    ngOnInit(): void {
      this.travelForm = this.formBuilder.group({
          travelingFrom: [""], goingTo: [""]
      });
    }

  logout() {
    this.accountService.logout();
  }
}
