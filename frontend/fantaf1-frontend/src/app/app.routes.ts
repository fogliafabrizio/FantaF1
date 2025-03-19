import { Routes } from '@angular/router';
import { PilotiComponent } from '../components/piloti/piloti.component';
import { HomeComponent } from '../components/home/home.component';

export const routes: Routes = [
  { path: 'piloti', component: PilotiComponent },
  { path: 'home', component: HomeComponent },
  { path: '**', redirectTo: '/home', pathMatch: 'full' },
];
