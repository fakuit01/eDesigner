package Starter;
import java.awt.SplashScreen;

import javax.swing.JFrame;

public class Splashscreen extends JFrame{
    private static final int SHOW_FOR = 3000;

    public Splashscreen() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        this.setTitle("Splash-Demo");

        Thread t = new Thread() {
            public void run() {
                SplashScreen splash = SplashScreen.getSplashScreen();
                
                if (splash == null) {
                    System.out
                            .println("SplashScreen kann nicht erzeugt werden.");
                    return;
                }
                try {
                    Thread.sleep(SHOW_FOR);
                } catch (InterruptedException e) {
                    System.err.println("Thread unterbrochen");
                }
                splash.close();
                Splashscreen.this.setVisible(true);
            }
        };
        t.start();
    }
    }
