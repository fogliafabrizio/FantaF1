import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { Fantaf1BffService } from '../../service/fantaf1-bff.service';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'ff1-login',
  standalone: true,
  imports: [
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatButtonModule,
    NgIf,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  username = '';
  password = '';

  errorMessage = '';

  constructor(
    private readonly _fantaf1BffService: Fantaf1BffService,
    private readonly router: Router,
    private snackBar: MatSnackBar
  ) {}

  onSubmit() {
    this._fantaf1BffService.login(this.username, this.password).subscribe(
      (data: any) => {
        console.log('Login effettuato', data);
        localStorage.setItem('jwt', data.token); // 👉 salva il token

        this.snackBar.open('Login effettuato con successo!', '', {
          duration: 3000,
        });
        this.router.navigate(['/home']);
      },
      (error) => {
        console.error('Errore durante il login', error);
        this.errorMessage = 'Username o password errati';
      }
    );
  }
}
