import { Routes } from '@angular/router';
import { PilotiComponent } from '../components/piloti/piloti.component';
import { HomeComponent } from '../components/home/home.component';
import { AuthGuard } from '../guards/auth.guard';
import { LoginComponent } from '../components/login/login.component';

export const routes: Routes = [
  { path: 'piloti', component: PilotiComponent, canActivate: [AuthGuard] },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '/home', pathMatch: 'full' },
];
