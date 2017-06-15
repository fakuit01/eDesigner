package Starter.Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;


import javafx.geometry.Orientation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public abstract class Bauelemente {

    int ID;
    double posX = 0, posY = 0;
    int Orientation=0;
    ImageView imageview = new ImageView();
    boolean deleted=false;
    BorderPane border=new BorderPane();
    ImageView helpimage = new ImageView();
    boolean isselected=false;
    boolean bauteilEntered = false;

    //Icon
    public Bauelemente(int ID, double posX1, double posY1, int Orientation1) {
        this.posX = posX1;
        this.posY = posY1;
        this.ID=ID;
        this.Orientation = Orientation1;
        /*
        //KeyEvent funktioniert nicht
        imageview.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent event) {
                if (event.getCode()==KeyCode.CONTROL)
                {
                    System.out.println("Control Spannungsquelle");
                }
            }
        });
        */
        //Wenn man über das Objekt drüber fährt
        imageview.setOnMouseEntered(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {

                //färbt beim drüberfahren das objekt in farbe
                orientationF();
                //Hilfebild
                helpimage.setY(border.getHeight() - 36);
                helpimage.setX(5);
                border.getChildren().add(helpimage);
                bauteilEntered = true;
                //Hilfebild ende
            }});
        //Wenn man das Objekt verlässt
        imageview.setOnMouseExited(new EventHandler<MouseEvent>(){

            //Muelleimer
            public void handle(MouseEvent event)
            {
                border.getChildren().remove(helpimage);
                if(event.getSceneX()<=125&&event.getSceneY()>=450&&event.getSceneY()<=500) {
                    deleted = true;
                    imageview.setImage(null);
                    imageview.removeEventHandler(MouseEvent.ANY, this);
                }
                //Transparent in schwarz je nach orientierung
                else if(!isselected)
                {
                    orientationS();
                }
                bauteilEntered = false;
            }});
        /*
        //Ändert das Bild in das mit schwarzen Hintergrund beim Losllassen der Maustaste Todo unnötig mit neuem Code
        imageview.setOnMouseReleased(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event)
            {
                if(event.getButton()== MouseButton.PRIMARY) {

                    //Mülleimer Funktion löscht alle Händler und das Bild Klasse bleibt allerdings erhalten
                    //wird auch nicht vom Borderpane entfernt bei border.getChildren().remove(imageview) kommen fehler
                    if (event.getSceneX() <= 125 && event.getSceneY() >= 450 && event.getSceneY() <= 500) {
                        deleted = true;
                        imageview.setImage(null);
                        imageview.removeEventHandler(MouseEvent.ANY, this);
                    }
                    //oben
                    else if(event.getSceneY() < 50) {
                        posX = round(event.getSceneX());
                        posY = 50;
                        imageview.setX(posX - 25);
                        imageview.setY(posY-25);
                        orientationS();
                    }
                    //rechts passt nicht immer
                    else if (event.getSceneX() > border.getWidth()-40) {
                        posX = round(border.getWidth()-40);
                        posY = round(event.getSceneY());
                        imageview.setX(posX-25);
                        imageview.setY(posY-25);
                        orientationS();
                    }
                    //links muss noch
                    else if (event.getSceneX() <= 125&&event.getSceneY()<=450){
                        posX = round(0+125+25);
                        posY = round(event.getSceneY());
                        imageview.setX(posX - 25);
                        imageview.setY(posY- 25);
                        orientationS();
                    }
                    //unten
                    else if (event.getSceneY() >= (border.getHeight()-25 - 40))
                    {
                        posX = round(event.getSceneX());
                        posY = round(border.getHeight()-25- 40);
                        imageview.setX(posX-25);
                        imageview.setY(posY-25);
                        orientationS();
                    }
                    //normaler bereich
                    else
                    {
                        posX = round(event.getSceneX());
                        posY = round(event.getSceneY());
                        imageview.setX(posX- 25);
                        imageview.setY(posY- 25);
                        orientationS();
                    }
                }else return;
            }});
        //zeichnet während des drag das Transparente Bild Todo unnötig mit neuem Code
        imageview.setOnMouseDragged(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event)
            {
                if(event.getButton()==MouseButton.PRIMARY) {
                    //beta anfang
                    //Welches Bild ist aktuell? Wegen drehen des Bildes
                    //oberhalb menüleiste
                    if (event.getSceneY() < 25)
                    {
                        imageview.setX(event.getSceneX() - 25);
                        orientationR();
                    }
                    //in linker vbox allerdings nicht beim Mülleimer
                    // Todo man kann vom mülleimer hochziehen bug
                    else if (event.getSceneX() < 125&&event.getSceneY()<450 )
                    {
                        imageview.setY(event.getSceneY() - 25);
                        orientationR();
                    }
                    //untere Hbox
                    else if (event.getSceneY() > (border.getHeight()-40)) {
                        imageview.setX(event.getSceneX() - 25);
                        orientationR();
                    }
                    //rechte vbox
                    else if (event.getSceneX() > (border.getWidth()-25))
                    {
                        imageview.setY(event.getSceneY() - 25);
                        orientationR();
                    }
                    else
                    {
                        //normaler Bereich
                        orientationFT();
                        imageview.setX(event.getSceneX() - 25);
                        imageview.setY(event.getSceneY() - 25);
                    }
                }
            }});
        */
        //Rechtsklick Drehung bzw ändern des Bildes
        imageview.setOnMouseClicked(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {

                if (event.getButton() == MouseButton.SECONDARY) {
                    //Welches Bild ist aktuell? Wegen drehen des Bildes
                    orientationFundOrienation();
                    //System.out.println("Ori rechtsklick "+Orientation);
                }
                //funktioniert noch nicht richtig bei rechtsdrehung dann linksdrehung
            /*else if(event.getButton()==MouseButton.PRIMARY)
                {
                    if(Orientation==0) {imageview.setImage(F135);Orientation=3;}
                    else if(Orientation==1) {imageview.setImage(F90);Orientation=0;}
                    else if(Orientation==2) {imageview.setImage(F45);Orientation=1;}
                    else if(Orientation==3) {imageview.setImage(F00);Orientation=2;}
                    System.out.println("Ori links "+Orientation);
                }*/
            }});
    }

    //public abstract void draw(GraphicsContext gc, double Orientation);
    //Zeichnen Methode
    //Wird zum String xml hinzugefügt
    public abstract String toxml(String xml);
    public abstract double getOrientation();
    public abstract double getPosX();
    public abstract double getPosY();
    public void orientationS(){}
    public void orientationR(){}
    public void orientationF(){}
    public void orientationFT(){}
    public void orientationFundOrienation(){}
    //Snap ans Raster der Bauteile
    public double round(double runden)
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
    //Zeichnen Methode
    public void draw(BorderPane borderPane)
    {
        this.border=borderPane;
        imageview.setX(posX-25);
        imageview.setY(posY-25);
        orientationS();
        border.getChildren().add(imageview);
    }


    //Neu draggen neuer code

    public boolean isMouseInsideImage()
    {
        return bauteilEntered;
    }
    public void preview(double xDistance, double yDistance)
    {
        if (posY + yDistance < 25)
        {
            imageview.setX(posX + xDistance - 25);
            orientationR();
        }
        //in linker vbox allerdings nicht beim Mülleimer
        // Todo man kann vom mülleimer hochziehen bug
        else if (posX + xDistance < 125&&posY + yDistance<450 )
        {
            imageview.setY(posY + yDistance - 25);
            orientationR();
        }
        //untere Hbox
        else if (posY + yDistance > (border.getHeight()-40)) {
            imageview.setX(posX + xDistance - 25);
            orientationR();
        }
        //rechte vbox
        else if (posX + xDistance > (border.getWidth()-25))
        {
            imageview.setY(posY + yDistance - 25);
            orientationR();
        }
        else
        {
            //beta ende
            //normaler Bereich
            orientationFT();
            imageview.setX(posX + xDistance - 25);
            imageview.setY(posY + yDistance - 25);
        }
    }

    public void move(double xDistance, double yDistance)
    {
        if (posX + xDistance <= 125 && posY + yDistance >= 450 && posY + yDistance <= 500) {
            deleted = true;
            imageview.setImage(null);
        }
        //oben
        else if(posY + yDistance < 50) {
            posX = round(posX + xDistance);
            posY = 50;
            imageview.setX(posX - 25);
            imageview.setY(posY-25);
        }
        //rechts passt nicht immer
        else if (posX + xDistance > border.getWidth()-40) {
            posX = round(border.getWidth()-40);
            posY = round(posY + yDistance);
            imageview.setX(posX-25);
            imageview.setY(posY-25);
        }
        //links muss noch
        else if (posX + xDistance <= 125&&posY + yDistance<=450){
            posX = round(0+125+25);
            posY = round(posY + yDistance);
            imageview.setX(posX - 25);
            imageview.setY(posY- 25);
        }
        //unten
        else if (posY + yDistance >= (border.getHeight()-25 - 40))
        {
            posX = round(posX + xDistance);
            posY = round(border.getHeight()-25- 40);
            imageview.setX(posX-25);
            imageview.setY(posY-25);
        }
        //normaler bereich
        else
        {
            posX = round(posX + xDistance);
            posY = round(posY + yDistance);
            imageview.setX(posX- 25);
            imageview.setY(posY- 25);
        }
    }
    public void select(){
        isselected=true;
        orientationF();
    }
    public void deselect(){
        isselected=false;
        if(!bauteilEntered) {
            orientationS();
        }
    }

}

