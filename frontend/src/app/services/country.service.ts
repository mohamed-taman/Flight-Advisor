import {Injectable} from '@angular/core';
import {Country} from "@app/models";

@Injectable({
    providedIn: 'root'
})
export class CountryService {

    private readonly countries: Array<Country>;

    constructor() {
        this.countries = [new Country(1, 'Egypt'),
            new Country(2, 'Serbia'),
            new Country(3, 'Bulgaria')];
    }

    public getCountries(): Country[] {
        return this.countries;
    }
}
