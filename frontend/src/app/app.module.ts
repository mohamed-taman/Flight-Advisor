import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';

// Import our Root application
import { AppComponent } from './app.component';

// Load all the bootstrap UI components to be available for the application
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

// load all the icons to be available for the application
import {IconsModule} from './icons/icons.module';

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    IconsModule
  ],
  declarations: [
    AppComponent
  ],
  providers: [],
  bootstrap: [AppComponent]   // Entry point for our application
})
export class AppModule { }
