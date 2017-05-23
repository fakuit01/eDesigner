package Starter.Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Leitung extends Bauelemente {
    public double xend;
    public double yend;
    double xs,ys,xe,ye;
    Color colorGrew=Color.rgb(238,238,238);
    Color colorBlue=Color.rgb(50, 200, 0);
    Line line =new Line();
    BorderPane border=new BorderPane();
    boolean deleted=false;
    double linelength;

    public Leitung(int ID,double xstart, double ystart, int Orientation, double xende, double yende)
    {
        super(ID,xstart,ystart,Orientation);
        this.xend=xende;
        this.yend=yende;
        //System.out.println("Class Leitung: "+xstart+","+ystart+","+xend+","+yend);
        line.setStartX(xstart);
        line.setStartY(ystart);
        line.setEndX(xend);
        line.setEndY(yend);
        line.setStroke(colorGrew);
        line.setStrokeWidth(5);

        //zeichnet während des drag bzw zieht die Linie, falls man am Start oder Ende der Linie zieht
        line.setOnMouseDragged(new EventHandler<MouseEvent>(){


            public void handle(MouseEvent event)
            {
                int startMausAbstand = (int) Math.sqrt((Math.pow(Math.abs(line.getStartX()-event.getSceneX()),2))
                        +(Math.pow(Math.abs(line.getStartY()-event.getSceneY()),2)));

                int endMausAbstand = (int) Math.sqrt((Math.pow(Math.abs(line.getEndX()-event.getSceneX()),2))
                        +(Math.pow(Math.abs(line.getEndY()-event.getSceneY()),2)));


                //Prüft ob man am Startpunkt zieht
                if(startMausAbstand <= 20){
                    System.out.println("Maus befindet sich im Startbereich.");
                    line.setStroke(colorBlue);
                    posX=event.getSceneX()+xs;
                    posY=event.getSceneY()+ys;

                    line.setStartX(posX);
                    line.setStartY(posY);
                }
                //Prüft ob man am Endpunkt zieht
                else if(endMausAbstand <= 20){
                    System.out.println("Maus befindet sich im Endbereich.");
                    line.setStroke(colorBlue);
                    xend=event.getSceneX()+xe;
                    yend=event.getSceneY()+ye;

                    line.setEndX(xe+event.getSceneX());
                    line.setEndY(ye+event.getSceneY());
                }
                //sonst draggen
                else{
                    System.out.println("Nun wird gedragged.");
                    line.setStroke(colorBlue);
                    posX=event.getSceneX()+xs;
                    posY=event.getSceneY()+ys;
                    xend=event.getSceneX()+xe;
                    yend=event.getSceneY()+ye;

                    line.setStartX(posX);
                    line.setStartY(posY);
                    line.setEndX(xe+event.getSceneX());
                    line.setEndY(ye+event.getSceneY());
                }

            }});

        //um den Abstand von X und Y Koordinaten zu der Maus zu bekommen
        line.setOnMouseEntered(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {
                xs=posX-event.getSceneX();
                ys=posY-event.getSceneY();
                xe=xend-event.getSceneX();
                ye=yend-event.getSceneY();
                line.setStroke(colorBlue);
            }});
        line.setOnMouseExited(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {
                line.setStroke(colorGrew);

            }});
        //zeichnet wenn dropped
        line.setOnMouseReleased(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event)
            {
                line.setStroke(colorGrew);
                //Mülleimer Funktion löscht alle Händler und das Bild Klasse bleibt allerdings erhalten
                if(event.getSceneX()<=125&&event.getSceneY()>=450&&event.getSceneY()<=500) {
                    deleted=true;
                    border.getChildren().remove(line);
                    line.removeEventHandler(MouseEvent.ANY, this);
                }
                else {
                    posX = rundenLeitungen(event.getSceneX() + xs);
                    posY = rundenLeitungen(event.getSceneY() + ys);
                    xend = rundenLeitungen(event.getSceneX() + xe);
                    yend = rundenLeitungen(event.getSceneY() + ye);
                    line.setStartX(posX);
                    line.setStartY(posY);
                    line.setEndX(xend);
                    line.setEndY(yend);
                    //posX=rundenLeitungen(event.getSceneX());
                    //posY=rundenLeitungen(event.getSceneY());
                }
            }});
    }
    //Wird zum String xml hinzugefügt
    public String toxml(String xml){
        if(deleted==false) {
            xml += "\t\t<Leitung>\n"
                    + "\t\t\t<ID>" + ID + "</ID>\n"
                    + "\t\t\t<PositionXstart>" + (int) posX + "</PositionXstart>\n"
                    + "\t\t\t<PositionYstart>" + (int) posY + "</PositionYstart>\n"
                    + "\t\t\t<PositionXend>" + (int) xend + "</PositionXend>\n"
                    + "\t\t\t<PositionYend>" + (int) yend + "</PositionYend>\n"
                    +"\t\t</Leitung>\n\n";
            return xml;
        }
        else return xml;
    }
    public void draw1(BorderPane borderPane)
    {
        //Todo ka ob das gut ist
        this.border=borderPane;
        border.getChildren().add(line);
        //borderPane.getChildren().add(line);
    }
    public double rundenLeitungen(double runden)
    {
        double a=0,b=0;
        if (runden % 25 < 12.5) {
            a= runden - (runden % 25);
            return a;

        } else if (runden % 25 >= 12.5) {
            b= runden +  (25-runden % 25);
            return b;
        } else return 0;
    }
    public double getOrientation() {return Orientation;}
    public double getPosX() {return posX;}
    public double getPosY() {return posY;}
    public double getXend() {return xend;}
}
