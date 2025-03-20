import { DatePipe, NgFor } from '@angular/common';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { MatOptionModule } from '@angular/material/core';
import { MatSelect, MatSelectModule } from '@angular/material/select';
import { PilotaConCosto } from '../../../models/piloti.model';
import { MatButtonModule } from '@angular/material/button';
import { Fantaf1BffService } from '../../../service/fantaf1-bff.service';
import { Gara } from '../../../models/gare.model';

@Component({
  selector: 'ff1-selezione-piloti',
  standalone: true,
  imports: [NgFor, MatOptionModule, MatSelectModule, MatButtonModule, DatePipe],
  templateUrl: './selezione-piloti.component.html',
  styleUrl: './selezione-piloti.component.scss',
})
export class SelezionePilotiComponent implements OnInit, OnDestroy {
  @Input() piloti: PilotaConCosto[] = [];

  gara!: Gara;
  scadenzaSelezioni!: string; // valorizzata dalla chiamata
  countdownString: string = '';
  private countdownInterval: any;

  constructor(private readonly _bffService: Fantaf1BffService) {}

  pilotiSelezionati: any[] = [];
  totaleCosto = 0;
  budgetRimanente = 100;

  ngOnInit(): void {
    this._bffService.getNextRaceInfo().subscribe({
      next: (response) => {
        this.gara = response;

        this.scadenzaSelezioni = response?.scadenzaScelte || '';
        this.startCountdown();
      },
    });
  }

  ngOnDestroy(): void {
    if (this.countdownInterval) {
      clearInterval(this.countdownInterval);
    }
  }

  startCountdown() {
    const scadenza = new Date(this.scadenzaSelezioni).getTime();

    this.countdownInterval = setInterval(() => {
      const now = new Date().getTime();
      const distance = scadenza - now;

      if (distance < 0) {
        this.countdownString = 'Tempo scaduto';
        clearInterval(this.countdownInterval);
        return;
      }

      const days = Math.floor(distance / (1000 * 60 * 60 * 24));
      const hours = Math.floor(
        (distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
      );
      const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
      const seconds = Math.floor((distance % (1000 * 60)) / 1000);

      this.countdownString = `${days}gg ${hours - 3}h ${minutes}m ${seconds}s`;
    }, 1000);
  }

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

  isButtonDisabled() {
    return !this.gara?.scelteAperte || this.budgetRimanente < 0;
  }
}
