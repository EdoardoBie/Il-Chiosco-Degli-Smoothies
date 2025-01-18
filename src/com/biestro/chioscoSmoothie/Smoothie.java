package com.biestro.chioscoSmoothie;
import java.util.Random;
import java.util.ArrayList;

public class Smoothie {

    private String nome;
    private ArrayList <Frutto> fruttiNecessari;
    private double temperaturaIdeale;
    private double temperaturaAmbiente;
    private ArrayList<Integer> quantitaFruttoNecessaria;
    private double prezzoTotale;

    public Smoothie (String nome, ArrayList <Frutto> fruttiNecessari, double temperaturaIdeale, ArrayList<Integer> quantitaFruttoNecessaria) {
        this.nome = nome;
        this.fruttiNecessari = fruttiNecessari;
        this.temperaturaIdeale = temperaturaIdeale;
        Random random = new Random();
        double min = 19, max = 26;
        this.temperaturaAmbiente = min + (max - min) * random.nextDouble();
        this.quantitaFruttoNecessaria = quantitaFruttoNecessaria;
    }

    public double getTemperaturaIdeale() {
        return temperaturaIdeale;
    }

    public double getTemperaturaAmbiente() {
        return temperaturaAmbiente;
    }

    public ArrayList<Integer> getQuantitaFruttoNecessaria() {
        return quantitaFruttoNecessaria;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Frutto> getFruttiNecessari() {
        return fruttiNecessari;
    }

    public void calcolaPrezzoTotale() {
        prezzoTotale = 0;
        for (int i = 0; i < fruttiNecessari.size(); i++) {
            prezzoTotale += fruttiNecessari.get(i).getPrezzoAlGrammo() * quantitaFruttoNecessaria.get(i);
        }
        // 30% per costi di mano d'opera
        prezzoTotale *= 1.30;
    }

    public double getPrezzoTotale() {
        return prezzoTotale;
    }

    @Override
    public String toString() {
        calcolaPrezzoTotale();
        return "Nome='" + nome + '\'' +
                ", \nFrutti necessari=" + fruttiNecessari.toString() +
                ", \nQuantita frutti necessaria=" + quantitaFruttoNecessaria +
                ", \nTemperatura ideale=" + temperaturaIdeale +
                ", \nPrezzo totale=" + String.format("%.2f€", prezzoTotale);
    }
}