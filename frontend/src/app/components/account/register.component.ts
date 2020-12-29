import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {first} from 'rxjs/operators';

import {AccountService, AlertService} from '@app/services';

@Component({
    templateUrl: 'register.component.html',
    styleUrls: ['register.component.css']
})
export class RegisterComponent implements OnInit {
    form?: FormGroup;
    loading: boolean = false;
    submitted: boolean = false;
    passwordHidden: boolean = false;


    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private accountService: AccountService,
        private alertService: AlertService
    ) {
    }

    // convenience getter for easy access to form fields
    get f() {
        return this.form!.controls;
    }

    ngOnInit() {
        this.form = this.formBuilder
            .group({
                firstName: [''], lastName: [''],
                username: [''], password: ['']
            });
    }

    onSubmit() {
        this.submitted = true;

        // reset alerts on submit
        this.alertService.clear();

        // stop here if form is invalid
        if (this.form!.invalid) {
            return;
        }

        this.loading = true;

        this.accountService.register(this.form!.value)
            .pipe(first())
            .subscribe({
                next: () => {
                    this.alertService.success('Registration successful',
                        {keepAfterRouteChange: true});
                    this.router
                        .navigate(['../login'], {relativeTo: this.route});
                },
                error: error => {
                    this.alertService.error(error);
                    this.loading = false;
                }
            });
    }

    /**
     * This method is used to show password and hide it again
     */
    togglePasswordView() {
        this.passwordHidden = !this.passwordHidden;
    }
}
