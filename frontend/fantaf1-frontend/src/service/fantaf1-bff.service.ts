import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pilota, PilotaConCosto } from '../models/piloti.model';
import { Gara } from '../models/gare.model';

@Injectable({
  providedIn: 'root',
})
export class Fantaf1BffService {
  baseUrl: string = './fantaf1-bff';

  constructor(private readonly _httpClient: HttpClient) {}

  getDriversByYear(year: number): Observable<Pilota[]> {
    return this._httpClient.get<Pilota[]>(`${this.baseUrl}/piloti`, {
      params: { anno: year },
    });
  }

  getDriversByYearWithCosto(year: number): Observable<PilotaConCosto[]> {
    return this._httpClient.get<PilotaConCosto[]>(
      `${this.baseUrl}/piloti-con-costo`,
      {
        params: { anno: year },
      }
    );
  }

  getNextRaceInfo(): Observable<Gara> {
    return this._httpClient.get<Gara>(`${this.baseUrl}/limite-scelta`);
  }

  login(username: string, password: string): Observable<any> {
    return this._httpClient.post(`${this.baseUrl}/login`, {
      username,
      password,
    });
  }

  aggiornaPilotaCosto(id: number, costo: number): Observable<any> {
    return this._httpClient.post(
      `${this.baseUrl}/piloti/${id}/aggiorna-costo`,
      {
        nuovoValore: costo,
      }
    );
  }
}
