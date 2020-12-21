import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

// Import application routing logic
import { AppRoutingModule } from './app-routing.module';

// Import our Root application
import { AppComponent } from './app.component';

// load all the icons to be available for the application
import {IconsModule} from './icons/icons.module';

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    IconsModule
  ],
  declarations: [
    AppComponent
  ],
  providers: [],
  bootstrap: [AppComponent]   // Entry point for our application
})
export class AppModule { }
