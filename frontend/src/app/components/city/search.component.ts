import {Component, OnInit} from '@angular/core';
import {AlertService, CityService} from "@app/services";
import {FormBuilder, FormGroup} from "@angular/forms";
import {first} from "rxjs/operators";
import {City} from "@app/models";

@Component({
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
    form?: FormGroup;
    loading: boolean = false;
    submitted: boolean = false;
    cities: City[] = [];

    constructor(private formBuilder: FormBuilder,
                private cityService: CityService,
                private alertService: AlertService) {
    }

    // convenience getter for easy access to form fields
    get f() {
        return this.form!.controls;
    }

    ngOnInit(): void {
        this.form = this.formBuilder
            .group({byName: [''], commentsLimit: ['']});
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

        this.cityService.search(this.form!.value)
            .pipe(first())
            .subscribe({
                next: (cities) => {
                    this.cities = cities;
                    this.alertService.success('Search was Successfully',
                        {autoClose: true});
                },
                error: error => {
                    this.alertService.error(error, {autoClose: true});
                }
            });

        this.loading = false;
    }

    chickCommentsLimit(value: number): boolean {
        if (value > 30) {
            this.f.commentsLimit.setValue(30);
            return false;
        }
        return true;
    }
}
