export interface Selezione {
  gpWeekendId: number;
  driverIds: number[];
}

export interface SelezionePiloti {
  gpWeekendId: number;
  driverIds: number[];
  creditsRemaining?: number;
}
