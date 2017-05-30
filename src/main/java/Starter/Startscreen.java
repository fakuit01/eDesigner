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


public class Startscreen {
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

}
