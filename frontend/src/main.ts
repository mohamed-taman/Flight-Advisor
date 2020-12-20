import { enableProdMode } from '@angular/core';

// We import browser dynamic because our application is a browser based application
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

// Import our root application
import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

// Run our application main class
platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
