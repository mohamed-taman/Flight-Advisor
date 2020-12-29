import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {BehaviorSubject, Observable, throwError} from "rxjs";
import {City} from "@app/models";
import {environment} from "@environments/environment";
import {catchError} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class CityService {

    private cities: City[] = [
        City.of(1, "Cairo", "Egypt", "Nice city to visit, and see pyramids."),
        City.of(2, "Belgrade", "serbia", "Nice city to see the old town."),
        City.of(3, "Sofia", "Bulgaria", "Good city to live in.")];

    constructor(private http: HttpClient) {
    }

    public create(city: City): Observable<City> {
        return this.http.post<City>(`${environment.apiUrl}/v1/cities`, city);
    }

    public search(searchTerms: { byName?: string, climit: number }): Observable<City[]> {
        return new BehaviorSubject(this.cities).asObservable();
    }


}
