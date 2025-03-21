import { DatePipe, NgFor } from '@angular/common';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { MatOptionModule } from '@angular/material/core';
import { MatSelect, MatSelectModule } from '@angular/material/select';
import { PilotaConCosto } from '../../../models/piloti.model';
import { MatButtonModule } from '@angular/material/button';
import { Fantaf1BffService } from '../../../service/fantaf1-bff.service';
import { Gara } from '../../../models/gare.model';
import { Selezione } from '../../../service/fantaf1-bff.model';

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

        // Perform the second call only after the first one is completed
        this._bffService.getSelezionePiloti(this.gara.id).subscribe({
          next: (response) => {
            this.budgetRimanente =
              this.budgetRimanente + (response.creditsRemaining ?? 0);
            this.pilotiSelezionati = response.driverIds.map((id) =>
              this.piloti.find((p) => p.id === id)
            );
            this.aggiornaTotale();
          },
        });
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

  onSelectPilota(pilota: PilotaConCosto, index: number) {
    // Update the selected pilot at the given index
    this.pilotiSelezionati[index] = pilota;

    // Recalculate the total cost and remaining budget
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
    // Calculate the total cost by summing up the `costo` of selected pilots, ignoring undefined/null values
    this.totaleCosto = this.pilotiSelezionati.reduce(
      (sum, p) => sum + (p?.costo || 0), // Add `p.costo` only if `p` is defined
      0
    );

    // Update the remaining budget
    this.budgetRimanente = this.budgetRimanente - this.totaleCosto;

    console.log('Totale Costo:', this.totaleCosto);
    console.log('Budget Rimanente:', this.budgetRimanente);
  }

  isButtonDisabled() {
    return !this.gara?.scelteAperte || this.budgetRimanente < 0;
  }

  isSelectDisabled() {
    return !this.gara?.scelteAperte;
  }

  confermaSelezione() {
    const selezione: Selezione = {
      gpWeekendId: this.gara.id,
      driverIds: this.pilotiSelezionati.map((p) => p.id),
    };

    console.log(selezione);
    console.log(this.pilotiSelezionati);

    this._bffService.confermaSelezionePiloti(selezione).subscribe({
      next: (response) => {
        alert('Selezione confermata con successo');
        console.log(response);
      },
      error: () => {
        alert('Errore durante la conferma della selezione');
      },
    });
  }
}
