package Starter.Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class Spannungsquelle extends Bauelemente {

    int WidthHeight=50;
    //Bilder von den Objekten beim drag and drop Schwarz und Transparent
    Image S00=new Image("file:Images/Bauelementeschwarz/spannungsquelle00S.png",WidthHeight,WidthHeight,false,false);
    Image S45=new Image("file:Images/Bauelementeschwarz/spannungsquelle45S.png",WidthHeight,WidthHeight,false,false);
    Image S90=new Image("file:Images/Bauelementeschwarz/spannungsquelle90S.png",WidthHeight,WidthHeight,false,false);
    Image S135=new Image("file:Images/Bauelementeschwarz/spannungsquelle135S.png",WidthHeight,WidthHeight,false,false);

    /*
    Image T00=new Image("file:Images/Bauelementetransparent/spannungsquelle00T.png",50,50,false,false);
    Image T45=new Image("file:Images/Bauelementetransparent/spannungsquelle45T.png",50,50,false,false);
    Image T90=new Image("file:Images/Bauelementetransparent/spannungsquelle90T.png",50,50,false,false);
    Image T135=new Image("file:Images/Bauelementetransparent/spannungsquelle135T.png",50,50,false,false);
*/
    Image F00=new Image("file:Images/Bauelementefarbe/spannungsquelle00F.png",WidthHeight,WidthHeight,false,false);
    Image F45=new Image("file:Images/Bauelementefarbe/spannungsquelle45F.png",WidthHeight,WidthHeight,false,false);
    Image F90=new Image("file:Images/Bauelementefarbe/spannungsquelle90F.png",WidthHeight,WidthHeight,false,false);
    Image F135=new Image("file:Images/Bauelementefarbe/spannungsquelle135F.png",WidthHeight,WidthHeight,false,false);

    Image FT00=new Image("file:Images/Bauelementefarbe/spannungsquelle00FT.png",WidthHeight,WidthHeight,false,false);
    Image FT45=new Image("file:Images/Bauelementefarbe/spannungsquelle45FT.png",WidthHeight,WidthHeight,false,false);
    Image FT90=new Image("file:Images/Bauelementefarbe/spannungsquelle90FT.png",WidthHeight,WidthHeight,false,false);
    Image FT135=new Image("file:Images/Bauelementefarbe/spannungsquelle135FT.png",WidthHeight,WidthHeight,false,false);

    Image R00=new Image("file:Images/Bauelementefarbe/spannungsquelle00RT.png",WidthHeight,WidthHeight,false,false);
    Image R45=new Image("file:Images/Bauelementefarbe/spannungsquelle45RT.png",WidthHeight,WidthHeight,false,false);
    Image R90=new Image("file:Images/Bauelementefarbe/spannungsquelle90RT.png",WidthHeight,WidthHeight,false,false);
    Image R135=new Image("file:Images/Bauelementefarbe/spannungsquelle135RT.png",WidthHeight,WidthHeight,false,false);

    ImageView imageview = new ImageView();
    boolean deleted=false;

    public Spannungsquelle(int ID,double x, double y, int Orientation1)
    {
        super(ID,x,y,Orientation1);
        imageview.setImage(S00);
        imageview.setOnMouseEntered(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {
                //färbt beim drüberfahren das objekt in farbe
                if(Orientation==0) {imageview.setImage(F00);}
                else if(Orientation==1){imageview.setImage(F45);}
                else if(Orientation==2){imageview.setImage(F90);}
                else if(Orientation==3){imageview.setImage(F135);}
            }});
        ////aus transbild schwarzes bild
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
                    if (Orientation == 0) {
                        imageview.setImage(S00);
                    } else if (Orientation == 1) {
                        imageview.setImage(S45);
                    } else if (Orientation == 2) {
                        imageview.setImage(S90);
                    } else if (Orientation == 3) {
                        imageview.setImage(S135);
                    }
                }
            }});
        //Rechtsklick Drehung bzw ändern des Bildes
        imageview.setOnMouseClicked(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    //Welches Bild ist aktuell? Wegen drehen des Bildes
                    /*
                    if(imageview.getImage()==S00)
                    {imageview.setImage(S45);Orientation=1;}
                    else if(imageview.getImage()==S45)
                    {imageview.setImage(S90);Orientation=2;}
                    else if(imageview.getImage()==S90)
                    {imageview.setImage(S135);Orientation=3;}
                    else if(imageview.getImage()==S135)
                    {imageview.setImage(S00);Orientation=0;}
                    */
                    if(Orientation==0) {imageview.setImage(F45);Orientation=1;}
                    else if(Orientation==1) {imageview.setImage(F90);Orientation=2;}
                    else if(Orientation==2) {imageview.setImage(F135);Orientation=3;}
                    else if(Orientation==3) {imageview.setImage(F00);Orientation=0;}
                    //System.out.println("Ori rechtsklick "+Orientation);
                }
                //Todo funktioniert noch nicht richtig bei rechtsdrehung dann linksdrehung
            /*else if(event.getButton()==MouseButton.PRIMARY)
                {
                    if(Orientation==0) {imageview.setImage(F135);Orientation=3;}
                    else if(Orientation==1) {imageview.setImage(F90);Orientation=0;}
                    else if(Orientation==2) {imageview.setImage(F45);Orientation=1;}
                    else if(Orientation==3) {imageview.setImage(F00);Orientation=2;}
                    System.out.println("Ori links "+Orientation);
                }*/
            }});
        //zeichnet während des drag das Transparente Bild
        imageview.setOnMouseDragged(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event)
            {
                if(event.getButton()==MouseButton.PRIMARY) {
                    //Welches Bild ist aktuell? Wegen drehen des Bildes
                //oberhalb menüleiste
                    if (event.getSceneY() < 50) {
                        if (Orientation == 0) {
                            imageview.setImage(R00);
                        } else if (Orientation == 1) {
                            imageview.setImage(R45);
                        } else if (Orientation == 2) {
                            imageview.setImage(R90);
                        } else if (Orientation == 3) {
                            imageview.setImage(R135);
                        }
                        //in rechter vbox allerdings nicht beim Mülleimer
                        //Todo da muss ein oder rein bei all den Funktionen mit dieser if in jeder Klasse oder nicht?
                    } else if (event.getSceneX() < 125&&event.getSceneY()<450&& event.getSceneY() < 500 ){
                        if (Orientation == 0) {
                            imageview.setImage(R00);
                        } else if (Orientation == 1) {
                            imageview.setImage(R45);
                        } else if (Orientation == 2) {
                            imageview.setImage(R90);
                        } else if (Orientation == 3) {
                            imageview.setImage(R135);
                        }
                    }
                    //Todo funktioniert nur wenn screensize nicht geändert wird muss man noch ändern
                    //über rechter vbox
                    else if (event.getSceneY() >= 575 - 40) {
                        if (Orientation == 0) {
                            imageview.setImage(R00);
                        } else if (Orientation == 1) {
                            imageview.setImage(R45);
                        } else if (Orientation == 2) {
                            imageview.setImage(R90);
                        } else if (Orientation == 3) {
                            imageview.setImage(R135);
                        }
                    }
                    //Todo Funktioniert nicht Screen ist größer als die 900
                    //untere hbox
                    else if (event.getSceneX() >= 925) {
                        if (Orientation == 0) {
                            imageview.setImage(R00);
                        } else if (Orientation == 1) {
                            imageview.setImage(R45);
                        } else if (Orientation == 2) {
                            imageview.setImage(R90);
                        } else if (Orientation == 3) {
                            imageview.setImage(R135);
                        }
                    } else {
                        //normaler Bereich
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
                    }
                }
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
                    }
                    //Todo Bild sollte nicht in die Vbox gezogen werden können funktioniert noch nicht richtig
                    //oben
                    else if(event.getSceneY() < 50) {
                        imageview.setX(rundenLeitungen(event.getSceneX()) - 25);
                        imageview.setY(50-25);
                        posX = rundenLeitungen(event.getSceneX());
                        posY = 50-25;
                        if (Orientation == 0) {
                            imageview.setImage(S00);
                        } else if (Orientation == 1) {
                            imageview.setImage(S45);
                        } else if (Orientation == 2) {
                            imageview.setImage(S90);
                        } else if (Orientation == 3) {
                            imageview.setImage(S135);
                        }
                    }
                        //rechts
                    else if (event.getSceneX() >= 925) {
                        imageview.setX(925-25);
                        imageview.setY(rundenLeitungen(event.getSceneY()) - 25);
                        posX = 925-25;
                        posY = rundenLeitungen(event.getSceneY());
                        if (Orientation == 0) {
                            imageview.setImage(S00);
                        } else if (Orientation == 1) {
                            imageview.setImage(S45);
                        } else if (Orientation == 2) {
                            imageview.setImage(S90);
                        } else if (Orientation == 3) {
                            imageview.setImage(S135);
                        }
                    }
                        //links
                    else if (event.getSceneX() <= 125&&event.getSceneY()<=450&& event.getSceneY() <= 500 ){
                        //Todo noch mer if? Funktioniert noch nicht richtig
                        imageview.setX(rundenLeitungen(event.getSceneX()) - 25);
                        imageview.setY(rundenLeitungen(event.getSceneY()) - 25);
                        posX = rundenLeitungen(event.getSceneX());
                        posY = rundenLeitungen(event.getSceneY());
                        if (Orientation == 0) {
                            imageview.setImage(S00);
                        } else if (Orientation == 1) {
                            imageview.setImage(S45);
                        } else if (Orientation == 2) {
                            imageview.setImage(S90);
                        } else if (Orientation == 3) {
                            imageview.setImage(S135);
                        }
                    }
                        //unten
                    else if (event.getSceneY() >= 575 - 40) {
                        imageview.setX(rundenLeitungen(event.getSceneX()) - 25);
                        imageview.setY(rundenLeitungen(575- 40 - 25));
                        posX = rundenLeitungen(event.getSceneX());
                        posY = rundenLeitungen(575- 40 - 25);
                        if (Orientation == 0) {
                            imageview.setImage(S00);
                        } else if (Orientation == 1) {
                            imageview.setImage(S45);
                        } else if (Orientation == 2) {
                            imageview.setImage(S90);
                        } else if (Orientation == 3) {
                            imageview.setImage(S135);
                        }
                    }
                        //normaler bereich
                    else {
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
                        imageview.setX(rundenLeitungen(event.getSceneX()) - 25);
                        imageview.setY(rundenLeitungen(event.getSceneY()) - 25);
                        posX = rundenLeitungen(event.getSceneX());
                        posY = rundenLeitungen(event.getSceneY());
                    }
                }else return;
            }});
        /*
        //Key drücken um Aktion durchzuführen geht nicht
        imageviewSpannungsquelle1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
               // if(event.getCode()==KeyCode.E) {
                    //if(event.getCode()== KeyCode.DELETE)
                    System.out.println("Test"+event.getText());
                }
            //}
        });
        */
    }
    //Zeichnen Methode
    public void draw1(BorderPane borderPane)
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
    //Wird zum String xml hinzugefügt
    public String toxml(String xml){
        if(deleted==false) {
            xml     +="\t\t<Spannungsquelle>\n"
                    + "\t\t\t<ID>" + ID + "</ID>\n"
                    + "\t\t\t<PositionX>" + (int) posX + "</PositionX>\n"
                    + "\t\t\t<PositionY>" + (int) posY + "</PositionY>\n"
                    + "\t\t\t<Richtung>" + Orientation + "</Richtung>\n"
                    + "\t\t</Spannungsquelle>\n\n";
            return xml;
        }
        else {
            return xml;
        }
    }
    //Snap ans Raster der Bauteile
    public double rundenBauteile(double runden) {
        if (runden % 50 < 25) {
            return runden - (runden % 50);
        } else if (runden % 50 >= 25) {
            return runden + (50 - (runden % 50));
        } else return 0;
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
    public  double getOrientation() {return Orientation;}
    public  double getPosX() {return posX;}
    public  double getPosY() {return posY;}

}
