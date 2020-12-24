import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

// Import Http modules, and should be loaded after browser module
import {HttpClientModule, HTTP_INTERCEPTORS} from "@angular/common/http";

// Import application routing logic
import { AppRoutingModule } from '@app/app-routing.module';

// Import our Root application
import { AppComponent } from '@app/app.component';

// load all the icons, and interceptors to be available for the application
import {ErrorInterceptor, fakeBackendProvider, IconsModule, JwtInterceptor} from '@app/helpers';

// Application Modules
import { HomeComponent, AccountModule, AlertComponent,
         CityComponent, CommentComponent, UploadComponent,
         TravelComponent } from '@app/components';

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    IconsModule,
    AccountModule
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    UploadComponent,
    CityComponent,
    CommentComponent,
    AlertComponent,
    TravelComponent
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },

    // provider used to create fake backend
    //fakeBackendProvider
  ],
  bootstrap: [AppComponent]   // Entry point for our application
})
export class AppModule { }
