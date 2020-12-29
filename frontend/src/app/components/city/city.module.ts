import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule, FormsModule} from "@angular/forms";

import {CityRoutingModule} from './city-routing.module';
import {LayoutComponent} from './layout.component';
import {AddComponent} from './add.component';
import {SearchComponent} from './search.component';

@NgModule({
    imports: [
        CommonModule,
        CityRoutingModule,
        ReactiveFormsModule,
        FormsModule
    ],
    declarations: [
        LayoutComponent,
        AddComponent,
        SearchComponent
    ]
})
export class CityModule {
}
