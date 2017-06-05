package Starter.Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;


public class Spule extends Bauelemente {
    int WidthHeight=50;
    //Bilder von den Objekten beim drag and drop Schwarz und Transparent
    Image S00=new Image("file:Images/Bauelementeschwarz/spule00S.png",50,50,false,false);
    Image S45=new Image("file:Images/Bauelementeschwarz/spule45S.png",50,50,false,false);
    Image S90=new Image("file:Images/Bauelementeschwarz/spule90S.png",50,50,false,false);
    Image S135=new Image("file:Images/Bauelementeschwarz/spule135S.png",50,50,false,false);

    Image F00=new Image("file:Images/Bauelementefarbe/spule00F.png",WidthHeight,WidthHeight,false,false);
    Image F45=new Image("file:Images/Bauelementefarbe/spule45F.png",WidthHeight,WidthHeight,false,false);
    Image F90=new Image("file:Images/Bauelementefarbe/spule90F.png",WidthHeight,WidthHeight,false,false);
    Image F135=new Image("file:Images/Bauelementefarbe/spule135F.png",WidthHeight,WidthHeight,false,false);

    Image FT00=new Image("file:Images/Bauelementefarbe/spule00FT.png",WidthHeight,WidthHeight,false,false);
    Image FT45=new Image("file:Images/Bauelementefarbe/spule45FT.png",WidthHeight,WidthHeight,false,false);
    Image FT90=new Image("file:Images/Bauelementefarbe/spule90FT.png",WidthHeight,WidthHeight,false,false);
    Image FT135=new Image("file:Images/Bauelementefarbe/spule135FT.png",WidthHeight,WidthHeight,false,false);

    ImageView imageview = new ImageView();
    boolean deleted=false;

    public Spule(int ID,double x, double y, int Orientation1)
    {
        super(ID,x,y,Orientation1);
        imageview.setImage(S00);
        //Wenn man über das Objekt drüber fährt
        imageview.setOnMouseEntered(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event) {
                //färbt beim drüberfahren das objekt in farbe
                if(Orientation==0) {imageview.setImage(F00);}
                else if(Orientation==1){imageview.setImage(F45);}
                else if(Orientation==2){imageview.setImage(F90);}
                else if(Orientation==3){imageview.setImage(F135);}
            }});
        //Wenn man das Objekt verlässt
        imageview.setOnMouseExited(new EventHandler<MouseEvent>(){
            //Muelleimer
            public void handle(MouseEvent event) {
                if(event.getSceneX()<=125&&event.getSceneY()>=450&&event.getSceneY()<=500) {
                    deleted=true;
                    imageview.setImage(null);
                    imageview.removeEventHandler(MouseEvent.ANY, this);
                }
                //Transparent in schwarz je nach orientierung
                else {
                    if (Orientation == 0) {imageview.setImage(S00);}
                    else if (Orientation == 1) {imageview.setImage(S45);}
                    else if (Orientation == 2) {imageview.setImage(S90);}
                    else if (Orientation == 3) {imageview.setImage(S135);}
                }
            }});
        //Rechtsklick Drehung bzw ändern des Bildes
        imageview.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    //Welches Bild ist aktuell? Wegen drehen des Bildes
                    if(Orientation==0) {imageview.setImage(S45);Orientation=1;}
                    else if(Orientation==1) {imageview.setImage(S90);Orientation=2;}
                    else if(Orientation==2) {imageview.setImage(S135);Orientation=3;}
                    else if(Orientation==3) {imageview.setImage(S00);Orientation=0;}

                }
                //Todo funktioniert noch nicht richtig linksdrehung
               /* else if(event.getButton()==MouseButton.PRIMARY)
                {
                    if(Orientation==0) {imageview.setImage(S135);Orientation=3;}
                    else if(Orientation==1) {imageview.setImage(S90);Orientation=0;}
                    else if(Orientation==2) {imageview.setImage(S45);Orientation=1;}
                    else if(Orientation==3) {imageview.setImage(S00);Orientation=2;}
                }*/
            }});
        //zeichnet während des drag das Transparente Bild
        imageview.setOnMouseDragged(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event)
            {
                if(event.getButton()==MouseButton.PRIMARY) {

                    //Welches Bild ist aktuell? Wegen drehen des Bildes
                    if (Orientation == 0) {
                        imageview.setImage(FT00);
                    } else if (Orientation == 1) {
                        imageview.setImage(FT45);
                    } else if (Orientation == 2) {
                        imageview.setImage(FT90);
                    } else if (Orientation == 3) {
                        imageview.setImage(FT135);
                    }
                    imageview.setX(event.getSceneX() - 25);
                    imageview.setY(event.getSceneY() - 25);
                }else return;
            }});
        //Ändert das Bild in das mit schwarzen Hintergrund beim Losllassen der Maustaste
        imageview.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event)
            {
                if(event.getButton()==MouseButton.PRIMARY) {
                    //Mülleimer Funktion löscht alle Händler und das Bild Klasse bleibt allerdings erhalten
                    if (event.getSceneX() <= 125 && event.getSceneY() >= 450 && event.getSceneY() <= 500) {
                        deleted = true;
                        imageview.setImage(null);
                        imageview.removeEventHandler(MouseEvent.ANY, this);
                    } else {
                        //Welches Bild ist aktuell? Wegen drehen des Bildes
                        if (Orientation == 0) {
                            imageview.setImage(S00);
                        } else if (Orientation == 1) {
                            imageview.setImage(S45);
                        } else if (Orientation == 2) {
                            imageview.setImage(S90);
                        } else if (Orientation == 3) {
                            imageview.setImage(S135);
                        }
                        imageview.setX(round(event.getSceneX()) - 25);
                        imageview.setY(round(event.getSceneY()) - 25);
                        posX = round(event.getSceneX());
                        posY = round(event.getSceneY());
                    }
                }else return;
            }});

    }

    //Wird zum String xml hinzugefügt
    public String toxml(String xml){
        if(deleted==false) {
            xml +="\t\t<Spule>\n"
                    +"\t\t\t<ID>" + ID + "</ID>\n"
                    +"\t\t\t<PositionX>" + (int) posX + "</PositionX>\n"
                    +"\t\t\t<PositionY>" + (int) posY + "</PositionY>\n"
                    +"\t\t\t<Richtung>" + Orientation + "</Richtung>\n"
                    +"\t\t</Spule>\n\n";
            return xml;
        }
        else return xml;
    }
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
        imageview.setX(posX-25);
        imageview.setY(posY-25);
        if(Orientation==0) {imageview.setImage(S00);}
        else if(Orientation==1){imageview.setImage(S45);}
        else if(Orientation==2){imageview.setImage(S90);}
        else if(Orientation==3){imageview.setImage(S135);}
        else imageview.setImage(S00);
        borderPane.getChildren().add(imageview);
    }
    public  double getOrientation() {return Orientation;}
    public  double getPosX() {return posX;}
    public  double getPosY() {return posY;}

}
