package com.fantaf1.fantaf1_dati.util;

public interface DriversApi {

    /**
     * Ritorna la lista di tutti i piloti nella storia della Formula 1
     * @return
     */
    String getDrivers();

    /**
     * Ritorna la lista di tutti i piloti in un determinato anno
     * @param year
     * @return
     */
    String getDrivers(Integer year);

    /**
     * Ritorna la lista di tutti i piloti in un determinato anno e round
     * @param year
     * @param round
     * @return
     */
    String getDrivers(Integer year, Integer round);

}
