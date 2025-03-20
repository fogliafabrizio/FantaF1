import { Component, Inject } from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { Pilota } from '../../../../models/piloti.model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { NgIf } from '@angular/common';
import { Fantaf1BffService } from '../../../../service/fantaf1-bff.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  templateUrl: './edit-price-dialog.component.html',
  selector: 'edit-price-dialog',
  standalone: true,
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    MatCardModule,
    NgIf,
  ],
})
export class EditPriceDialog {
  newPrice!: number;

  constructor(
    public dialogRef: MatDialogRef<EditPriceDialog>,
    @Inject(MAT_DIALOG_DATA) public data: { pilota: Pilota },
    private readonly _bffService: Fantaf1BffService,
    private snackBar: MatSnackBar
  ) {}

  confirm() {
    this._bffService
      .aggiornaPilotaCosto(this.data.pilota.id, this.newPrice)
      .subscribe({
        next: () => {
          this.snackBar.open('Prezzo aggiornato con successo!', '', {
            duration: 3000,
            panelClass: ['snackbar-success'],
          });
          this.dialogRef.close();
        },
        error: (error) => {
          this.snackBar.open("Errore durante l'aggiornamento del prezzo", '', {
            duration: 3000,
            panelClass: ['snackbar-error'],
          });
        },
      });
  }
}
