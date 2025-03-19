export interface Pilota {
  id: number;
  driver_id?: string;
  sigla?: string;
  nome?: string;
  cognome?: string;
  numero?: number;
  nazionalita?: string;
}

export interface PilotaConCosto {
  id: number;
  driver_id?: string;
  sigla?: string;
  nome?: string;
  cognome?: string;
  numero?: number;
  nazionalita?: string;
  costo: number;
}
