package com.biestro.chioscoSmoothie;

import com.biestro.chioscoSmoothie.Exceptions.FrullatoreRottoException;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final ArrayList<Frutto> magazzino = new ArrayList<>();
    private static final ArrayList<Smoothie> menuSmoothie = new ArrayList<>();
    private static final GestioneMagazzino gestioneMagazzino = new GestioneMagazzino();
    private static final PreparazioneSmoothie preparazioneSmoothie = new PreparazioneSmoothie();

    public static void main(String[] args) {
        System.out.println("=== Benvenuto al Chiosco Smoothie del Resort Archimede ===");

        boolean continua = true;
        while (continua) {
            mostraMenu();
            try {
                int scelta = Integer.parseInt(sc.nextLine());
                continua = gestisciSceltaMenu(scelta);
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido!");
            }
        }

        System.out.println("Grazie per aver utilizzato il nostro sistema. Arrivederci!");
        sc.close();
    }

    private static void mostraMenu() {
        System.out.println("\n=== MENU CHIOSCO SMOOTHIE ===");
        System.out.println("1. Gestione Magazzino");
        System.out.println("2. Gestione Smoothie");
        System.out.println("3. Visualizza Statistiche");
        System.out.println("4. Esci");
        System.out.println("\nSeleziona un'opzione (1-4):");
    }

    private static boolean gestisciSceltaMenu(int scelta) {
        switch (scelta) {
            case 1:
                menuMagazzino();
                return true;
            case 2:
                menuSmoothie();
                return true;
            case 3:
                mostraStatistiche();
                return true;
            case 4:
                return false;
            default:
                System.out.println("Opzione non valida!");
                return true;
        }
    }

    private static void menuMagazzino() {
        System.out.println("\n=== GESTIONE MAGAZZINO ===");
        System.out.println("1. Aggiungi nuovo frutto");
        System.out.println("2. Visualizza magazzino");
        System.out.println("3. Torna al menu principale");

        try {
            int scelta = Integer.parseInt(sc.nextLine());
            switch (scelta) {
                case 1:
                    aggiungiFrutto();
                    break;
                case 2:
                    visualizzaMagazzino();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opzione non valida!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Inserisci un numero valido!");
        }
    }

    private static void menuSmoothie() {
        System.out.println("\n=== GESTIONE SMOOTHIE ===");
        System.out.println("1. Crea nuovo smoothie");
        System.out.println("2. Prepara smoothie");
        System.out.println("3. Visualizza menu smoothie");
        System.out.println("4. Torna al menu principale");

        try {
            int scelta = Integer.parseInt(sc.nextLine());
            switch (scelta) {
                case 1:
                    creaNuovoSmoothie();
                    break;
                case 2:
                    preparaSmoothie();
                    break;
                case 3:
                    visualizzaMenuSmoothie();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opzione non valida!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Inserisci un numero valido!");
        }
    }

    private static void aggiungiFrutto() {
        System.out.println("\n=== AGGIUNTA NUOVO FRUTTO ===");

        System.out.println("Inserisci il nome del frutto:");
        String nome = sc.nextLine();

        int quantita = 0;
        boolean quantitaValida = false;
        while (!quantitaValida) {
            try {
                System.out.println("Inserisci la quantità in grammi:");
                quantita = Integer.parseInt(sc.nextLine());
                if (quantita > 0) {
                    quantitaValida = true;
                } else {
                    System.out.println("La quantità deve essere maggiore di 0!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido!");
            }
        }

        int maturazione = 0;
        boolean maturazioneValida = false;
        while (!maturazioneValida) {
            try {
                System.out.println("Inserisci l'indice di maturazione (1-10):");
                maturazione = Integer.parseInt(sc.nextLine());
                if (maturazione >= 1 && maturazione <= 10) {
                    maturazioneValida = true;
                } else {
                    System.out.println("La maturazione deve essere tra 1 e 10!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido!");
            }
        }

        double prezzo = gestioneMagazzino.getPrezzoFrutto(nome);
        magazzino.add(new Frutto(nome, quantita, maturazione, prezzo));
        System.out.println("Frutto aggiunto con successo!");
        gestioneMagazzino.verificaScorte(magazzino);
    }

    private static void creaNuovoSmoothie() {
        if (magazzino.isEmpty()) {
            System.out.println("Non ci sono frutti in magazzino! Aggiungi prima dei frutti.");
            return;
        }

        System.out.println("\n=== CREAZIONE NUOVO SMOOTHIE ===");
        System.out.println("Inserisci il nome dello smoothie:");
        String nome = sc.nextLine();

        ArrayList<Frutto> fruttiNecessari = new ArrayList<>();
        ArrayList<Integer> quantitaFruttoNecessaria = new ArrayList<>();

        boolean continuaAggiunta = true;
        while (continuaAggiunta) {
            visualizzaMagazzino();
            System.out.println("\nInserisci il nome del frutto da aggiungere (o 'fine' per terminare):");
            String nomeFrutto = sc.nextLine();

            if (nomeFrutto.equalsIgnoreCase("fine")) {
                if (fruttiNecessari.isEmpty()) {
                    System.out.println("Devi aggiungere almeno un frutto!");
                    continue;
                }
                continuaAggiunta = false;
            } else {
                Frutto fruttoScelto = null;
                for (Frutto f : magazzino) {
                    if (f.getNome().equalsIgnoreCase(nomeFrutto)) {
                        fruttoScelto = f;
                        break;
                    }
                }

                if (fruttoScelto != null) {
                    try {
                        System.out.println("Inserisci la quantità necessaria in grammi:");
                        int quantita = Integer.parseInt(sc.nextLine());
                        if (quantita > 0 && quantita <= fruttoScelto.getQuantitaDisponibile()) {
                            fruttiNecessari.add(fruttoScelto);
                            quantitaFruttoNecessaria.add(quantita);
                        } else {
                            System.out.println("Quantità non valida o non disponibile!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Inserisci un numero valido!");
                    }
                } else {
                    System.out.println("Frutto non trovato!");
                }
            }
        }

        double temperaturaIdeale = 0;
        boolean temperaturaValida = false;
        while (!temperaturaValida) {
            try {
                System.out.println("Inserisci la temperatura ideale in °C:");
                temperaturaIdeale = Double.parseDouble(sc.nextLine());
                temperaturaValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido!");
            }
        }

        Smoothie nuovoSmoothie = new Smoothie(nome, fruttiNecessari, temperaturaIdeale, quantitaFruttoNecessaria);
        menuSmoothie.add(nuovoSmoothie);
        System.out.println("Smoothie creato con successo!");
    }

    private static void preparaSmoothie() {
        if (menuSmoothie.isEmpty()) {
            System.out.println("Non ci sono smoothie nel menu! Crea prima uno smoothie.");
            return;
        }

        visualizzaMenuSmoothie();
        System.out.println("\nInserisci il nome dello smoothie da preparare:");
        String nome = sc.nextLine();

        for (int i = 0; i < menuSmoothie.size(); i++) {
            if (menuSmoothie.get(i).getNome().equalsIgnoreCase(nome)) {
                try {
                    preparazioneSmoothie.preparazioneSmoothie(
                            menuSmoothie,
                            magazzino,
                            i,
                            menuSmoothie.get(i).getTemperaturaIdeale(),
                            menuSmoothie.get(i).getTemperaturaAmbiente(),
                            menuSmoothie.get(i).getFruttiNecessari(),
                            menuSmoothie.get(i).getQuantitaFruttoNecessaria()
                    );
                    System.out.println("\nSmoothie " + nome + " preparato con successo!");
                    System.out.printf("Prezzo totale: %.2f€%n", menuSmoothie.get(i).getPrezzoTotale());
                    return;
                } catch (FrullatoreRottoException e) {
                    String errore = "Errore durante la preparazione: " + e.getMessage();
                    System.out.println(errore);
                    Logger.logError(errore);
                    return;
                }
            }
        }
        System.out.println("Smoothie non trovato!");
    }

    private static void visualizzaMagazzino() {
        if (magazzino.isEmpty()) {
            System.out.println("\nIl magazzino è vuoto!");
            return;
        }

        System.out.println("\n=== MAGAZZINO ATTUALE ===");
        for (Frutto f : magazzino) {
            System.out.println("\n" + f.toString());
        }
    }

    private static void visualizzaMenuSmoothie() {
        if (menuSmoothie.isEmpty()) {
            System.out.println("\nIl menu degli smoothie è vuoto!");
            return;
        }

        System.out.println("\n=== MENU SMOOTHIE ===");
        for (Smoothie s : menuSmoothie) {
            System.out.println("\n" + s.toString());
        }
    }

    private static void mostraStatistiche() {
        System.out.println("\n=== STATISTICHE ===");
        System.out.println("Frutti in magazzino: " + magazzino.size());
        System.out.println("Smoothie nel menu: " + menuSmoothie.size());

        if (!magazzino.isEmpty()) {
            System.out.println("\nFrutti quasi esauriti (sotto la soglia minima):");
            boolean trovati = false;
            for (Frutto f : magazzino) {
                if (f.getQuantitaDisponibile() < 500) {
                    System.out.println("- " + f.getNome() + ": " + f.getQuantitaDisponibile() + "g");
                    trovati = true;
                }
            }
            if (!trovati) {
                System.out.println("Nessun frutto sotto la soglia minima.");
            }
        }
    }
}