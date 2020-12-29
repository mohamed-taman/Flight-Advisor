import {Component, OnInit} from '@angular/core';

import {AlertService, CityService, CountryService} from '@app/services';
import {City, Country} from "@app/models";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {first} from "rxjs/operators";

@Component({
    templateUrl: './add.component.html',
    styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
    form?: FormGroup;
    loading: boolean = false;
    submitted: boolean = false;
    byCountryId: boolean = true;

    constructor(private formBuilder: FormBuilder,
                private cityService: CityService,
                private countryService: CountryService,
                private alertService: AlertService) {
    }

    // convenience getter for easy access to form fields
    get f() {
        return this.form!.controls;
    }

    ngOnInit() {
        this.form = this.formBuilder
            .group({
                name: [''], countryChoice: ['1'],
                countryName: [''], countryId: ['',Validators.required],
                description: ['']
            });
    }

    onCountryChoiceChange(){
            if (this.f.countryChoice.value === '1') {
                this.byCountryId = true;
                this.f.countryName.clearValidators();
                this.f.countryName.reset("");
                this.f.countryId.setValidators(Validators.required);
                this.f.countryId.updateValueAndValidity();
            } else {
                this.byCountryId = false;
                this.f.countryId.clearValidators();
                this.f.countryId.reset("");
                this.f.countryName.setValidators(Validators.required);
                this.f.countryName.updateValueAndValidity();
            }
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

        let city: City = new City(
            this.f.name.value,
            this.f.description.value,
            this.byCountryId? this.f.countryId.value : 0,
            !this.byCountryId? this.f.countryName.value : "");

        console.log(city);

        this.cityService.create(city)
            .pipe(first())
            .subscribe({
                next: city => {
                    console.log(city);
                    this.alertService.success(`City (${city}) added Successfully.`,
                        {keepAfterRouteChange: true});
                },
                error: err => {
                    this.alertService.error(err);
                }
            });

        this.loading = false;
    }

    get countries(): Country[] {
        return this.countryService.getCountries();
    }
}
