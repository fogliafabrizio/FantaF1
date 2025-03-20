import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { isTokenExpired } from '../util/jwt-utils';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem('jwt');

    if (token && !isTokenExpired(token)) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
