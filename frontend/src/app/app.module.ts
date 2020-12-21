import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

// Import application routing logic
import { AppRoutingModule } from '@app/app-routing.module';

// Import our Root application
import { AppComponent } from '@app/app.component';

// load all the icons to be available for the application
import {IconsModule} from '@app/helpers';

// Application Modules
import { HomeComponent, AccountComponent, AlertComponent,
         CityComponent, CommentComponent, UploadComponent } from '@app/components';

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    IconsModule
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    AccountComponent,
    UploadComponent,
    CityComponent,
    CommentComponent,
    AlertComponent
  ],
  providers: [],
  bootstrap: [AppComponent]   // Entry point for our application
})
export class AppModule { }
