import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BlogComponent } from './blog/blog.component';
import { HomeComponent } from './home/home.component';
import { RegistrationComponent } from './registration/registration.component';
import { FoodbankComponent } from './foodbank/foodbank.component';

const routes: Routes = [
  { path: 'Registar', component: RegistrationComponent},
  { path: 'Blog', component: BlogComponent},
  { path: 'Home', component: HomeComponent},
  { path: 'FoodBank', component: FoodbankComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
