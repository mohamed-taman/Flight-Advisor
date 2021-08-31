import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent, UploadComponent, TravelComponent} from "@app/components";
import {AuthGuard} from "@app/helpers";

const accountModule = () => import('@app/components').then(x => x.AccountModule);
const cityModule = () => import('@app/components').then(x => x.CityModule);

const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'travel', component: TravelComponent, canActivate: [AuthGuard]},
  { path: 'upload', component: UploadComponent, canActivate: [AuthGuard]},
  { path: 'cities', loadChildren: cityModule, canActivate: [AuthGuard] },
  { path: 'account', loadChildren: accountModule},
  // otherwise redirect to home
  { path: '**', redirectTo: '' }
 ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
