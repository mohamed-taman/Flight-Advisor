import {Component, OnDestroy, OnInit} from '@angular/core';
import {AlertService, CityService, SessionService} from "@app/services";
import {FormBuilder, FormGroup} from "@angular/forms";
import {first} from "rxjs/operators";
import {City} from "@app/models";

@Component({
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit, OnDestroy {
    form?: FormGroup;
    addForm?: FormGroup;
    loading: boolean = false;
    submitted: boolean = false;
    cities: City[] = [];

    constructor(private formBuilder: FormBuilder,
                private cityService: CityService,
                private alertService: AlertService,
                private session: SessionService) {
    }

    // convenience getter for easy access to form fields
    get f() {
        return this.form!.controls;
    }

    get af() {
        return this.addForm!.controls;
    }

    ngOnInit(): void {
        this.form = this.formBuilder
            .group({byName: [''], commentsLimit: ['']});

        this.addForm = this.formBuilder
            .group({description: ['']});
    }

    ngOnDestroy(): void {
        this.session.remove("cityId");

    }

    private search(): void {

        this.loading = true;

        this.cityService.search(this.form!.value)
            .pipe(first())
            .subscribe({
                next: (cities) => {
                    this.cities = cities;
                },
                error: error => {
                    this.alertService.error(error, {autoClose: true});
                }
            });

        this.loading = false;
    }

    onSearchSubmit() {

        this.submitted = true;

        // reset alerts on submit
        this.alertService.clear();

        // stop here if form is invalid
        if (this.form!.invalid) {
            return;
        }

        this.search();
        this.submitted = false;
    }

    onAdd(cityId: number): void {
        this.session.put("cityId", cityId);
    }

    addComment(): void {

        this.submitted = true;

        // reset alerts on submit
        this.alertService.clear();

        // stop here if form is invalid
        if (this.addForm!.invalid) {
            return;
        }

        this.cityService.addComment(this.session.get("cityId"), this.af.description.value)
            .subscribe({
                next: () => {
                    this.alertService.info("Comment added successfully",
                        {autoClose: true});

                    this.search();
                },
                error: error => {
                    this.alertService.error(error, {autoClose: true});
                }
            });

        this.submitted = false;
    }

    onShow(city: City): void {
        this.session.put("city", city);
    }

    checkCommentsLimitInput(value: number): boolean {
        if (value > 30) {
            this.f.commentsLimit.setValue(30);
            return false;
        }
        return true;
    }
}
