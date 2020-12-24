import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent, CityComponent, UploadComponent, TravelComponent} from "@app/components";
import {AuthGuard} from "@app/helpers";

const accountModule = () => import('@app/components').then(x => x.AccountModule);
//const usersModule = () => import('@/users/users.module').then(x => x.UsersModule);

const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'cities', component: CityComponent, canActivate: [AuthGuard]},
  { path: 'travel', component: TravelComponent, canActivate: [AuthGuard]},
  { path: 'upload', component: UploadComponent, canActivate: [AuthGuard]},
  //{ path: 'cities', loadChildren: usersModule, canActivate: [AuthGuard] },
  { path: 'account', loadChildren: accountModule},
  // otherwise redirect to home
  { path: '**', redirectTo: '' }
 ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
