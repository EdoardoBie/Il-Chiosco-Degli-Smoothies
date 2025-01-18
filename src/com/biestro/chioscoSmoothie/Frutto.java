package com.biestro.chioscoSmoothie;

public class Frutto {
    private String nome;
    private int quantitaDisponibile;
    private int maturazione;
    private double prezzoAlGrammo;

    public Frutto(String nome, int quantitaDisponibile, int maturazione, double prezzoAlGrammo) {
        this.nome = nome;
        this.quantitaDisponibile = quantitaDisponibile;
        this.maturazione = maturazione;
        this.prezzoAlGrammo = prezzoAlGrammo;
    }

    public double getPrezzoAlGrammo() {
        return prezzoAlGrammo;
    }

    public int getMaturazione() {
        return maturazione;
    }

    public int getQuantitaDisponibile() {
        return quantitaDisponibile;
    }

    public void setQuantitaDisponibile(int quantitaDisponibile) {
        this.quantitaDisponibile = quantitaDisponibile;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Nome='" + nome + '\'' +
                ", \nQuantitaDisponibile=" + quantitaDisponibile + " grammi" +
                ", \nMaturazione=" + maturazione +
                ", \nPrezzo al grammo=" + String.format("%.2f€", prezzoAlGrammo);
    }
}
