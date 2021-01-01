import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

// Import Http modules, and should be loaded after browser module
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";

// Import Reactive modules for all modules
import {ReactiveFormsModule} from "@angular/forms";

// Import application routing logic
import {AppRoutingModule} from '@app/app-routing.module';

// Import our Root application
import {AppComponent} from '@app/app.component';

// load all the icons, and interceptors to be available for the application
import {ErrorInterceptor, IconsModule, JwtInterceptor} from '@app/helpers';

// Application Modules
import {
    AccountModule, AlertComponent, CityModule,
    HomeComponent, TravelComponent, UploadComponent} from '@app/components';


@NgModule({
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        IconsModule,
        AccountModule,
        CityModule,
        ReactiveFormsModule,
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        UploadComponent,
        AlertComponent,
        TravelComponent,
    ],
    providers: [
        {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
        {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},

        // provider used to create fake backend
        //fakeBackendProvider
    ],
    bootstrap: [AppComponent]   // Entry point for our application
})
export class AppModule {
}
