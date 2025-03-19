import { Component, Input, OnInit } from '@angular/core';
import { Pilota, PilotaConCosto } from '../../../models/piloti.model';
import { Fantaf1BffService } from '../../../service/fantaf1-bff.service';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { NgIf } from '@angular/common';

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

  constructor(private readonly _bffService: Fantaf1BffService) {}

  ngOnInit(): void {}

  viewHistory(pilota: Pilota) {
    alert(`Azione su ${pilota.nome} ${pilota.cognome}`);
  }

  editPrice(pilota: Pilota) {
    alert(`Azione su ${pilota.nome} ${pilota.cognome}`);
  }

  viewStats(pilota: Pilota) {
    alert(`Azione su ${pilota.nome} ${pilota.cognome}`);
  }
}
