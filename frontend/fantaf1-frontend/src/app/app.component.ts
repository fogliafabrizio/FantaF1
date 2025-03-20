import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { isTokenExpired } from '../util/jwt-utils';
import jwtDecode from 'jwt-decode';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    MatToolbarModule,
    MatButtonModule,
    RouterModule,
    NgIf,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
  username = undefined;
  token!: string;

  constructor(
    private router: Router,
    private readonly _cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.refreshUserInfo();

    setInterval(() => {
      const token = localStorage.getItem('jwt');
      if (token && isTokenExpired(token)) {
        localStorage.removeItem('jwt');
        this.router.navigate(['/login']);
      } else {
        this.refreshUserInfo();
      }
    }, 60000);
  }

  refreshUserInfo() {
    const token = localStorage.getItem('jwt');
    if (token) {
      const decoded: any = jwtDecode(token);
      this.username = decoded.sub;
      this._cdr.detectChanges();
    }
  }

  logout() {
    localStorage.removeItem('jwt');
    this.router.navigate(['/login']).then(() => {
      window.location.reload();
    });
  }
}
