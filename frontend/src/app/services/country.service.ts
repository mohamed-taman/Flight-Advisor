import {Injectable} from '@angular/core';
import {Country} from "@app/models";
import {environment} from "@environments/environment";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class CountryService {

    constructor(private http: HttpClient) {
    }

    public getCountries(): Observable<Country[]> {
        return this.http.get<Country[]>(`${environment.apiUrl}/v1/countries`);
    }
}
