import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient, HttpParams} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';

import {environment} from '@environments/environment';
import {User} from '@app/models';

@Injectable({
    providedIn: 'root'
})
export class AccountService {

    private userSubject: BehaviorSubject<User | null>;
    public user: Observable<User | null>;

    constructor(private router: Router, private http: HttpClient) {

        this.userSubject = new BehaviorSubject<User | null>(JSON
            .parse(<string>localStorage.getItem('user')));

        this.user = this.userSubject.asObservable();
    }

    public get userValue(): User | null {
        return this.userSubject.value;
    }

    login(username: string, password: string): Observable<User> {
        return this.http.post<User>(`${environment.apiUrl}/public/login`,
            {username, password},
            {observe: 'response'})
            // resp is of type `HttpResponse<User>`
            .pipe(map(response => {

                // 1. Get Authorization token
                const token = response.headers.get('Authorization');

                // 2. get user, access the body directly, which is typed as `User`.
                const user: User = {...response.body, token};

                /*
                    3. Store user details and jwt token in local storage,
                       to keep user logged in between page refreshes
                 */
                localStorage.setItem('user', JSON.stringify(user));
                this.userSubject.next(user);
                return user;
            }));
    }

    logout() {
        // remove user from local storage and set current user to null
        localStorage.removeItem('user');
        this.userSubject.next(null);
        this.router.navigate(['/account/login']);
    }

    register(user: User) {
        return this.http.post(`${environment.apiUrl}/public/register`, user);
    }

    //This for future implementation
    getAll() {
        return this.http.get<User[]>(`${environment.apiUrl}/users`);
    }

    getById(id: string) {
        return this.http.get<User>(`${environment.apiUrl}/users/${id}`);
    }

    update(id: string, params: HttpParams) {
        return this.http.put(`${environment.apiUrl}/users/${id}`, params)
            .pipe(map(x => {
                // update stored user if the logged in user updated their own record
                if (id == this.userValue!.id) {
                    // update local storage
                    const user = {...this.userValue, ...params} as User;
                    localStorage.setItem('user', JSON.stringify(user));

                    // publish updated user to subscribers
                    this.userSubject.next(user);
                }
                return x;
            }));
    }

    delete(id: string) {
        return this.http.delete(`${environment.apiUrl}/users/${id}`)
            .pipe(map(x => {
                // auto logout if the logged in user deleted their own record
                if (id == this.userValue!.id) {
                    this.logout();
                }
                return x;
            }));
    }
}
