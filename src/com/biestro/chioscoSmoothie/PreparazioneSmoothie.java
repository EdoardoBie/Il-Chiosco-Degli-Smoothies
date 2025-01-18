package com.biestro.chioscoSmoothie;

import com.biestro.chioscoSmoothie.Exceptions.*;

import java.util.ArrayList;
import java.util.Random;

public class PreparazioneSmoothie {

    public boolean verificaDisponibilitaIngredienti(ArrayList<Smoothie> smoothie, int smoothieIndex) throws QuantitaInsufficienteException {
        for (int i=0; i<smoothie.get(smoothieIndex).getFruttiNecessari().size(); i++) {
            if (smoothie.get(smoothieIndex).getFruttiNecessari().get(i).getQuantitaDisponibile() < smoothie.get(smoothieIndex).getQuantitaFruttoNecessaria().get(i)){
                throw new QuantitaInsufficienteException("La quantità disponibile di " + smoothie.get(smoothieIndex).getFruttiNecessari().get(i).getNome() + " non è sufficiente");
            }
        }
        return true;
    }

    public boolean controlloMaturazioneFrutta(ArrayList<Frutto> frutto, int fruttoIndex) throws FruttoTroppoMaturoException, FruttoAcerboException {
        if (frutto.get(fruttoIndex).getMaturazione() > 8) {
            throw new FruttoTroppoMaturoException ("Il frutto ha una maturazione di " + frutto.get(fruttoIndex).getMaturazione() + ", quindi il frutto è troppo maturo!");
        } else if (frutto.get(fruttoIndex).getMaturazione() < 3) {
            throw new FruttoAcerboException ("Il frutto ha una maturazione di " + frutto.get(fruttoIndex).getMaturazione() + ", quindi il frutto è troppo acerbo!");
        } else {
            return true;
        }
    }

    public boolean verificaTemperatura(ArrayList<Smoothie> smoothie, int smoothieIndex) throws TemperaturaErrataException {
        if ((smoothie.get(smoothieIndex).getTemperaturaAmbiente()-smoothie.get(smoothieIndex).getTemperaturaIdeale()) > 2 || (smoothie.get(smoothieIndex).getTemperaturaAmbiente()-smoothie.get(smoothieIndex).getTemperaturaIdeale()) < -2) {
            throw new TemperaturaErrataException("La temperatura si discosta di più di 2° da quella ideale!" + " La temperatura dell'ambiente è " + smoothie.get(smoothieIndex).getTemperaturaAmbiente());
        } else {
            return true;
        }
    }

    public void preparazioneSmoothie(ArrayList<Smoothie> smoothie, ArrayList<Frutto> frutto, int indexSmoothie, double temperaturaIdeale, double temperaturaAmbiente, ArrayList<Frutto> fruttiNecessari, ArrayList<Integer> quantitaFruttoNecessaria) throws FrullatoreRottoException {

        // Check della temperatura

        try {
            verificaTemperatura(smoothie, indexSmoothie);
        } catch (TemperaturaErrataException e) {
            System.out.println(e.getMessage());
            String errore = "Errore: " + e;
            Logger.logError(errore);
        }

        // Check della maturazione dela frutta necessaria

        for (int i=0; i<fruttiNecessari.size(); i++) {
            try {
                controlloMaturazioneFrutta(fruttiNecessari, i);
            } catch (FruttoTroppoMaturoException | FruttoAcerboException e) {
                System.out.println(e.getMessage());
                String errore = "Errore: " + e;
                Logger.logError(errore);
            }
        }

        // Check della quantità della frutta necessaria

        try {
            verificaDisponibilitaIngredienti(smoothie, indexSmoothie);
            for (int i=0; i<smoothie.get(indexSmoothie).getFruttiNecessari().size(); i++) {
                smoothie.get(indexSmoothie).getFruttiNecessari().get(i).setQuantitaDisponibile(smoothie.get(indexSmoothie).getFruttiNecessari().get(i).getQuantitaDisponibile() - smoothie.get(indexSmoothie).getQuantitaFruttoNecessaria().get(i));
                System.out.println("La quantità di " + smoothie.get(indexSmoothie).getFruttiNecessari().get(i).getNome() + " rimasta è " + smoothie.get(indexSmoothie).getFruttiNecessari().get(i).getQuantitaDisponibile());
            }
        } catch (QuantitaInsufficienteException e) {
            System.out.println(e.getMessage());
            String errore = "Errore: " + e;
            Logger.logError(errore);
        }

        // Controllo guasto del frullatore

        Random random = new Random();

        if (random.nextDouble() < 0.1) {
            throw new FrullatoreRottoException("Si è verificato un guasto al frullatore!");
        }

    }

}
