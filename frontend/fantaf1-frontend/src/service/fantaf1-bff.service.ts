import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pilota, PilotaConCosto } from '../models/piloti.model';

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
}
