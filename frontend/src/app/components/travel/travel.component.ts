import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AlertService, CityService} from "@app/services";

@Component({
    selector: 'app-travel',
    templateUrl: 'travel.component.html',
    styleUrls: ['travel.component.css']
})
export class TravelComponent implements OnInit {
    today: Date = new Date();
    isRoundTrip: boolean = true;
    isSubmitted: boolean = false;
    isSearching: boolean = false;
    searchForm?: FormGroup;

    constructor(private formBuilder: FormBuilder,
                private cityService: CityService,
                private alertService: AlertService) {
    }

    ngOnInit(): void {
        this.searchForm = this.formBuilder.group({
            flyingFrom: [''], flyingTo: [''],
            fromDate: [(new Date()).toISOString().substring(0,10)], toDate: ['']
        })
    }

    // convenience getter for easy access to form fields
    get f() {
        return this.searchForm!.controls;
    }

    onSubmit(): void {

        this.isSubmitted = true;

        // reset alerts on submit
        this.alertService.clear();

        // stop here if form is invalid
        if (this.searchForm!.invalid) return;

        console.log(this.searchForm!.value);

        this.searchTrips(this.f.flyingFrom.value, this.f.flyingTo.value);

    }

    private searchTrips(flyingFrom: string, flyingTo: string): void {

        console.log(flyingFrom, flyingTo);
        this.isSearching = true;

        setTimeout(() => {
                console.log("Searching for flights");
                this.isSearching = false;
            },
            3000);
    }

}
