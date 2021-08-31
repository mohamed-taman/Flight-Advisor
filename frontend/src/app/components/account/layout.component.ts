import {Component} from '@angular/core';
import {Router} from '@angular/router';

import {AccountService} from '@app/services';

@Component({
    templateUrl: 'layout.component.html',
    styleUrls: ['layout.component.css']
})
export class LayoutComponent {
    constructor(
        private router: Router,
        private accountService: AccountService
    ) {
        // redirect to home if already logged in
        if (this.accountService.userValue) {
            this.router
                .navigate(['/'])
                .then(r => console.log(r));
        }
    }
}
