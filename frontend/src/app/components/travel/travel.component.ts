import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AlertService, CityService} from "@app/services";
import {Airport} from "@app/models/Airport";
import {Utils} from "@app/helpers";
import {Trip} from "@app/models";

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
    trips: Trip[] = [];
    searchForm?: FormGroup;
    airports: Airport[] = [];
    isSearchingFromAirports: boolean = false;
    isSearchingToAirports: boolean = false;

    constructor(private formBuilder: FormBuilder,
                private cityService: CityService,
                private alertService: AlertService) {
    }

    ngOnInit(): void {
        this.searchForm = this.formBuilder.group({
            flyingFrom: [''], flyingTo: [''],
            fromDate: [(new Date()).toISOString().substring(0, 10)], toDate: ['']
        })
    }

    // convenience getter for easy access to form fields
    get f() {
        return this.searchForm!.controls;
    }

    onKeypress(event: any): void {
        if (!event.key) return;

        if ((event.key.length == 1 ||
            event.key == 'Backspace' ||
            event.key == 'Delete') &&
            event.target.value.length >= 3) {
            this.searchAirports(event.target.name, event.target.value);
        }
    }

    onSelect(id: number): void {
        if (this.f.flyingFrom.value == this.f.flyingTo.value)
            switch (id) {
                case 1: {
                    this.f.flyingFrom.setValue(this.f.flyingTo.value);
                    this.f.flyingTo.setValue("")
                    break;
                }
                case 2: {
                    this.f.flyingTo.setValue("");
                }
            }
    }

    onSubmit(): void {

        this.isSubmitted = true;

        // reset alerts on submit
        this.alertService.clear();

        // stop here if form is invalid
        if (this.searchForm!.invalid) return;

        this.searchTrips(this.f.flyingFrom.value, this.f.flyingTo.value);

    }

    private searchTrips(flyingFrom: string, flyingTo: string): void {

        this.isSearching = true;

        this.cityService.travel(flyingFrom, flyingTo)
            .subscribe({
                next: (trips: Trip[]) => {
                    this.trips = trips;
                    this.isSearching = false;
                },
                error: err => {
                    this.alertService.error(err);
                    this.isSearching = false;
                }
            });
    }

    private searchAirports(target: string, value: string): void {
        Utils.clear(this.airports);

        if (target == "flyingFrom")
            this.isSearchingFromAirports = true;
        else
            this.isSearchingToAirports = true;

        this.cityService.searchAirports(value)
            .subscribe({
                next: (airports: Airport[]) => {
                    this.airports = airports;
                    this.isSearchingFromAirports = false;
                    this.isSearchingToAirports = false;
                },
                error: err => {
                    this.alertService.error(err);
                    this.isSearchingFromAirports = false;
                    this.isSearchingToAirports = false;
                }
            });
    }
}
