import { NgModule } from '@angular/core';

import { NgxBootstrapIconsModule, allIcons } from 'ngx-bootstrap-icons';

@NgModule({
  declarations: [],
  imports: [
    NgxBootstrapIconsModule.pick(allIcons)
  ],
  exports: [
    NgxBootstrapIconsModule
  ]
})
export class IconsModule { }
