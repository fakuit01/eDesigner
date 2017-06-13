package Starter.Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;


public class Spannungsquelle extends Bauelemente {

    int WidthHeight=50;
    //Bilder von den Objekten beim drag and drop Schwarz und Transparent
    Image S00=new Image("file:Images/Bauelementeschwarz/spannungsquelle00S.png",WidthHeight,WidthHeight,false,false);
    Image S45=new Image("file:Images/Bauelementeschwarz/spannungsquelle45S.png",WidthHeight,WidthHeight,false,false);
    Image S90=new Image("file:Images/Bauelementeschwarz/spannungsquelle90S.png",WidthHeight,WidthHeight,false,false);
    Image S135=new Image("file:Images/Bauelementeschwarz/spannungsquelle135S.png",WidthHeight,WidthHeight,false,false);

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

    Image help=new Image("file:Images/spannungsquellehilfe.png",900,30,false,false);





    public Spannungsquelle(int ID,double x, double y, int Orientation1)
    {
        super(ID,x,y,Orientation1);
        imageview.setImage(S00);
        helpimage.setImage(help);
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

    public  double getOrientation() {return Orientation;}
    public  double getPosX() {return posX;}
    public  double getPosY() {return posY;}
    public void orientationS()
    {
        if (Orientation == 0) {imageview.setImage(S00);}
        else if (Orientation == 1) {imageview.setImage(S45);}
        else if (Orientation == 2) {imageview.setImage(S90);}
        else if (Orientation == 3) {imageview.setImage(S135);}
    }
    public void orientationR()
    {
        if (Orientation == 0) {imageview.setImage(R00);}
        else if (Orientation == 1) {imageview.setImage(R45);}
        else if (Orientation == 2) {imageview.setImage(R90);}
        else if (Orientation == 3) {imageview.setImage(R135);}
    }
    public void orientationF()
    {
        if (Orientation == 0) {
            imageview.setImage(F00);
        } else if (Orientation == 1) {
            imageview.setImage(F45);
        } else if (Orientation == 2) {
            imageview.setImage(F90);
        } else if (Orientation == 3) {
            imageview.setImage(F135);
        }
    }
    public void orientationFT()
    {
        if (Orientation == 0) {imageview.setImage(FT00);}
        else if (Orientation == 1) {imageview.setImage(FT45);}
        else if (Orientation == 2) {imageview.setImage(FT90);}
        else if (Orientation == 3) {imageview.setImage(FT135);}
    }
    public void orientationFundOrienation()
    {
        if(Orientation==0) {imageview.setImage(F45);Orientation=1;}
        else if(Orientation==1) {imageview.setImage(F90);Orientation=2;}
        else if(Orientation==2) {imageview.setImage(F135);Orientation=3;}
        else if(Orientation==3) {imageview.setImage(F00);Orientation=0;}
    }

}
