import {NgModule} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

import {AccountRoutingModule} from './account-routing.module';
import {LayoutComponent} from './layout.component';
import {LoginComponent} from './login.component';
import {RegisterComponent} from './register.component';
import {IconsModule} from "@app/helpers";

@NgModule({
    imports: [
        CommonModule,
        IconsModule,
        ReactiveFormsModule,
        AccountRoutingModule,
        IconsModule
    ],
    declarations: [
        LayoutComponent,
        LoginComponent,
        RegisterComponent
    ]
})
export class AccountModule {
}
