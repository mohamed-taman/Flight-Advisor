import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule, FormsModule} from "@angular/forms";

import {CityRoutingModule} from './city-routing.module';
import {LayoutComponent} from './layout.component';
import {AddComponent} from './add.component';
import {SearchComponent} from './search.component';
import {ShowCommentsComponent} from './show-comments.component';
import {IconsModule} from "@app/helpers";

@NgModule({
    imports: [
        CommonModule,
        CityRoutingModule,
        ReactiveFormsModule,
        FormsModule,
        IconsModule
    ],
    declarations: [
        LayoutComponent,
        AddComponent,
        SearchComponent,
        ShowCommentsComponent
    ]
})
export class CityModule {
}
