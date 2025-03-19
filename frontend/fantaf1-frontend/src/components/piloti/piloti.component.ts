import { Component, OnInit } from '@angular/core';
import { TabellaPilotiComponent } from './tabella-piloti/tabella-piloti.component';
import { SelezionePilotiComponent } from './selezione-piloti/selezione-piloti.component';
import { Fantaf1BffService } from '../../service/fantaf1-bff.service';
import { PilotaConCosto } from '../../models/piloti.model';

@Component({
  selector: 'ff1-piloti',
  standalone: true,
  imports: [TabellaPilotiComponent, SelezionePilotiComponent],
  templateUrl: './piloti.component.html',
  styleUrl: './piloti.component.scss',
})
export class PilotiComponent implements OnInit {
  anno = new Date().getFullYear();
  piloti: PilotaConCosto[] = [];

  constructor(private readonly _bffService: Fantaf1BffService) {}

  ngOnInit(): void {
    this.loadPiloti();
  }

  loadPiloti() {
    this._bffService.getDriversByYearWithCosto(this.anno).subscribe({
      next: (response) => {
        this.piloti = response;
      },
    });
  }
}
