import { Component, Input, OnInit } from '@angular/core';
import { Pilota, PilotaConCosto } from '../../../models/piloti.model';
import { Fantaf1BffService } from '../../../service/fantaf1-bff.service';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import {
  MatDialog,
  MatDialogModule,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { NgIf } from '@angular/common';
import { EditPriceDialog } from './edit-price-dialog/edit-price-dialog.component';

@Component({
  selector: 'ff1-tabella-piloti',
  standalone: true,
  imports: [MatTableModule, MatButtonModule, MatIconModule, NgIf],
  templateUrl: './tabella-piloti.component.html',
  styleUrl: './tabella-piloti.component.scss',
})
export class TabellaPilotiComponent {
  anno = new Date().getFullYear();
  @Input() piloti: PilotaConCosto[] = [];

  displayedColumns = ['numero', 'sigla', 'nome', 'cognome', 'costo', 'azioni'];

  constructor(
    private readonly _bffService: Fantaf1BffService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {}

  viewHistory(pilota: Pilota) {
    alert(`Azione su ${pilota.id} ${pilota.nome} ${pilota.cognome}`);
  }

  editPrice(pilota: Pilota) {
    this.dialog
      .open(EditPriceDialog, {
        width: '300px',
        data: { pilota },
      })
      .afterClosed()
      .subscribe(() => {
        window.location.reload();
      });
  }

  viewStats(pilota: Pilota) {
    alert(`Azione su ${pilota.nome} ${pilota.cognome}`);
  }
}
