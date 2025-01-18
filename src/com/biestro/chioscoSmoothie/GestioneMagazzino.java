package com.biestro.chioscoSmoothie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GestioneMagazzino {
    private static final int SOGLIA_MINIMA = 500; // grammi
    private static final int QUANTITA_RIORDINO = 2000; // grammi
    private Map<String, Double> listinoPrezzi;

    public GestioneMagazzino() {
        this.listinoPrezzi = new HashMap<>();
        // Prezzi predefiniti al grammo
        listinoPrezzi.put("banana", 0.005);
        listinoPrezzi.put("mango", 0.008);
        listinoPrezzi.put("ananas", 0.006);
        listinoPrezzi.put("fragola", 0.007);
    }

    public void verificaScorte(ArrayList<Frutto> frutti) {
        for (Frutto frutto : frutti) {
            if (frutto.getQuantitaDisponibile() < SOGLIA_MINIMA) {
                rifornisciFrutto(frutto);
                Logger.logError("Rifornimento automatico effettuato per " + frutto.getNome());
            }
        }
    }

    private void rifornisciFrutto(Frutto frutto) {
        frutto.setQuantitaDisponibile(frutto.getQuantitaDisponibile() + QUANTITA_RIORDINO);
        System.out.println("Rifornimento automatico effettuato per " + frutto.getNome() +
                ". Nuova quantità: " + frutto.getQuantitaDisponibile() + " grammi");
    }

    public double getPrezzoFrutto(String nomeFrutto) {
        return listinoPrezzi.getOrDefault(nomeFrutto.toLowerCase(), 0.006);
    }
}