import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {City} from "@app/models";
import {environment} from "@environments/environment";
import {map} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class CityService {

    constructor(private http: HttpClient) {
    }

    public create(city: City): Observable<City> {
        return this.http
            .post<City>(`${environment.apiUrl}/v1/cities`, city)
            .pipe(map(cityView => {
                return City.of(cityView.id, cityView.name, cityView.description,
                    cityView.country, cityView.comments);
            }));
    }

    public search(searchTerms: { byName?: string, commentsLimit: number }): Observable<City[]> {

        let commentsLimit: string = searchTerms.
            commentsLimit ? `?cLimit=${searchTerms.commentsLimit}` : '';

        return this.http
            .post<City[]>(`${environment.apiUrl}/v1/cities/search${commentsLimit}`,
                {"byName": searchTerms.byName});
    }


}
