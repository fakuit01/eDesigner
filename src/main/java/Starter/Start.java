package Starter;

public class Start {
    public static void main(String[] args) {
        //Startscreen.execute(args);
        /*
        Startscreen startscreen=new Startscreen();
        startscreen.showscreen();
        startscreen.closescreen();
*/
        new Splashscreen();
        App.execute(args);

    }
}
