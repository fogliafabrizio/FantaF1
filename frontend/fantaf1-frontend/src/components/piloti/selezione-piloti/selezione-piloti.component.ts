import { NgFor } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatOptionModule } from '@angular/material/core';
import { MatSelect, MatSelectModule } from '@angular/material/select';
import { PilotaConCosto } from '../../../models/piloti.model';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'ff1-selezione-piloti',
  standalone: true,
  imports: [NgFor, MatOptionModule, MatSelectModule, MatButtonModule],
  templateUrl: './selezione-piloti.component.html',
  styleUrl: './selezione-piloti.component.scss',
})
export class SelezionePilotiComponent {
  @Input() piloti: PilotaConCosto[] = [];

  pilotiSelezionati: any[] = [];
  totaleCosto = 0;
  budgetRimanente = 100;

  onSelectPilota(pilota: any, index: number) {
    // Evita selezioni duplicate
    if (this.pilotiSelezionati.includes(pilota)) {
      return;
    }

    this.pilotiSelezionati[index] = pilota;
    this.aggiornaTotale();
  }

  // Metodo per filtrare i piloti disponibili in base a quelli già selezionati
  getPilotiDisponibili(index: number) {
    return this.piloti.filter(
      (p) =>
        !this.pilotiSelezionati.includes(p) ||
        this.pilotiSelezionati[index] === p
    );
  }

  aggiornaTotale() {
    this.totaleCosto = this.pilotiSelezionati.reduce(
      (sum, p) => sum + (p?.costo || 0),
      0
    );
    this.budgetRimanente = 100 - this.totaleCosto;
  }
}
