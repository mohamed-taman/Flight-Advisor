import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";

import {LayoutComponent} from './layout.component';
import {AddComponent} from './add.component';
import {SearchComponent} from './search.component';
import {ShowCommentsComponent} from "./show-comments.component";

const routes: Routes = [
    {
        path: '', component: LayoutComponent,
        children: [
            {path: 'add', component: AddComponent},
            {path: 'search', component: SearchComponent},
            {path: 'comments', component: ShowCommentsComponent}

        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CityRoutingModule { }
