package com.parallel;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
	    System.out.println("Help me");
	    VertybinisPopierius vp = new VertybinisPopierius(150.0f);
	    try {
            Thread akcininkasJonas = new Akcininkas(vp, 250.0f, "Jonas");
            akcininkasJonas.start();

            Thread akcininkasPetras = new Akcininkas(vp, 400.0f, "Petras");
            akcininkasPetras.start();

            akcininkasJonas.join();
            akcininkasPetras.join();
        } catch (Exception ex) {
	        ex.printStackTrace();
        }
    }
}

class Akcininkas extends Thread {

    private VertybinisPopierius vertybinisPopierius;
    float santaupos;
    String vardas;

    public Akcininkas(VertybinisPopierius vertybinisPopierius, float santaupos, String vardas) {
        this.vertybinisPopierius = vertybinisPopierius;
        this.santaupos = santaupos;
        this.vardas = vardas;
    }

//    public void run() {
//        for(int i = 0 ; i < 10; i++) {
//            float akcijosKaina = vertybinisPopierius.kaina;
//            try {
//                Thread.sleep(new Random().nextInt(500));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//           // if (vertybinisPopierius.kaina < santaupos) {
//                System.out.println(vardas + " nusipirko " + vertybinisPopierius.pirkti(akcijosKaina) + "akciju");
//           // }
//        }
//    }

        public void run() {
        for(int i = 0 ; i < 10; i++) {
            synchronized(vertybinisPopierius) {
                float akcijosKaina = vertybinisPopierius.kaina;
                try {
                    Thread.sleep(new Random().nextInt(500));
                } catch (InterruptedException e) {
                }
                System.out.println(vardas + " nusipirko " + vertybinisPopierius.pirkti(akcijosKaina) + "akciju");
            }
        }
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
