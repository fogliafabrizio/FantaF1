package com.fantaf1.fantaf1_dati.util;

public interface RaceApi {

    /**
     * Ritorna la lista di tutte le gare nella storia della Formula 1
     * @return
     */
    String getRaces();

    /**
     * Ritorna la lista di tutte le gare in un determinato anno
     * @param year
     * @return
     */
    String getRaces(Integer year);

    /**
     * Ritorna la lista di tutte le gare in un determinato anno e round
     * @param year
     * @param round
     * @return
     */
    String getRaces(Integer year, Integer round);
}
