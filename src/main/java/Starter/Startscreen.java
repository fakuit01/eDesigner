package Starter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.SplashScreen;



public class Startscreen extends JFrame{
    BorderPane borderPane=new BorderPane();
    Stage window;
    JFrame jframe=new JFrame("test");

    /*
    public static void execute(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Loading");
        window.setMinHeight(600);
        window.setMinWidth(400);
        Scene scene = new Scene(borderPane, 1000, 600);
        window.setScene(scene);
        window.show();


    }*/
public void showscreen(){
    jframe.setSize(1000,600);
    jframe.setLocationRelativeTo(null);
    jframe.setBackground(Color.BLUE);
    jframe.setVisible(true);


}
public void closescreen(){
    TimerTask action = new TimerTask()
    {
        public void run()
        {
            jframe.dispose();
        }
    };
    Timer caretaker = new Timer();
    caretaker.schedule(action, 2*1000);


}

    public void Splashscreen() {

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
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.err.println("Thread unterbrochen");
                }
                splash.close();
                Startscreen.this.setVisible(true);
            }
        };
        t.start();
    }
}
