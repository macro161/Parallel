package com.parallel;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
	    VertybinisPopierius vp = new VertybinisPopierius(150.0f);
        System.out.println("\nAkcininkai perkantys neapsaugant bendro resurso \n");
	    try {
            Thread akcininkasPetras = new BlogasAkcininkas(vp,  "Petras"); akcininkasPetras.start();
            Thread akcininkasJonas = new BlogasAkcininkas(vp,  "Jonas"); akcininkasJonas.start();
            akcininkasJonas.join(); akcininkasPetras.join();
        } catch (Exception ex) {
	        ex.printStackTrace();
        }

        System.out.println("\nAkcininkai perkantys apsaugant bendro resurso\n");
        try {
            Thread akcininkasPetras = new GerasAkcininkas(vp,  "Petras"); akcininkasPetras.start();
            Thread akcininkasJonas = new GerasAkcininkas(vp,  "Jonas"); akcininkasJonas.start();
            akcininkasJonas.join(); akcininkasPetras.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class GerasAkcininkas extends Thread {
    private VertybinisPopierius vertybinisPopierius;
    String vardas;
    float turimosAkcijos = 0;

    public GerasAkcininkas(VertybinisPopierius vertybinisPopierius, String vardas) {
        this.vertybinisPopierius = vertybinisPopierius;
        this.vardas = vardas;
    }

    public void run() {
        for(int i = 0 ; i < 100; i++) {
            synchronized(vertybinisPopierius) {
                float akcijosKaina = vertybinisPopierius.kaina; //KRITINE SEKCIJA
                try { Thread.sleep(new Random().nextInt(50)); } catch (InterruptedException e) { }
                turimosAkcijos += vertybinisPopierius.pirkti(akcijosKaina);
            }
        }
        System.out.println(vardas + " nusipirko " + turimosAkcijos + " akciju");
    }

}

class BlogasAkcininkas extends Thread {
    private VertybinisPopierius vertybinisPopierius;
    String vardas;
    float turimosAkcijos = 0;

    public BlogasAkcininkas(VertybinisPopierius vertybinisPopierius, String vardas) {
        this.vertybinisPopierius = vertybinisPopierius;
        this.vardas = vardas;
    }

    public void run() {
        for(int i = 0 ; i < 100; i++) {
            float akcijosKaina = vertybinisPopierius.kaina; //KRITINE SEKCIJA
            try { Thread.sleep(new Random().nextInt(50)); } catch (InterruptedException e) { }
            turimosAkcijos += vertybinisPopierius.pirkti(akcijosKaina);
        }
        System.out.println(vardas + " nusipirko " + turimosAkcijos + " akciju");
    }
}

class VertybinisPopierius {
    public float kaina;

    public VertybinisPopierius(float kaina) {
        this.kaina = kaina;
    }

    public float pirkti(float pinigai) {
        float akcijos = pinigai / kaina;
        kaina = kaina + new Random(10).nextFloat();
        return akcijos;
    }
}