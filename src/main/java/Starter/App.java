package Starter;

//import Bauelemente.*;
import Starter.Bauelemente.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import java.awt.*;
import java.awt.Label;
import java.awt.TextField;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.Event;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.print.DocFlavor;
import javax.swing.*;
//import javax.xml.soap.Text;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.Iterator;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
//import java.nio.charset.StandardCharsets;
import javafx.scene.text.Text;
import javafx.util.Duration;



public class App extends Application {
    //Variablen die auch in den Funktionen verwendet werden
    Stage window;
    String xmlstring="";
    File file;
    String all="";
    int clickCount=0;
    double xStartLeitung=0,yStartLeitung=0,xEndLeitung=0,yEndLeitung=0;
    Dimension dim =Toolkit.getDefaultToolkit().getScreenSize();
    Color color=Color.rgb(238,238,238);
    private EventHandler<DragEvent> mIconDragOverRoot = null;
    Canvas canvas = new Canvas(dim.getWidth(),dim.getHeight());
    VBox vbox = new VBox();
    VBox vboxEmpty = new VBox();
    HBox hboxTipps = new HBox();
    GraphicsContext gc = canvas.getGraphicsContext2D();
    MenuBar menuBar = new MenuBar();
    BorderPane borderPane=new BorderPane();
    int IDLeitung=0,IDKondensator=0,IDSpule=0,IDSpannungsquelle=0,IDWiderstand=0;
    ArrayList<Bauelemente> arraylist= new ArrayList<Bauelemente>();
    Text textToolTipps=new Text();
    Line drawline=new Line();
    final Image hilfe=new Image("file:Images/hilfe.png",1000,600,false,false);
    ImageView helpView = new ImageView(hilfe);
    int timernumber=0;
    Scene scene;
    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    //Rectangle2D zweiterscreen;
    boolean isControlPressed = false;
    ArrayList<Bauelemente> draglist = new ArrayList<Bauelemente>();
    double xStartPosition;
    double yStartPosition;


    //Startet das Programm
    public static void execute(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {
        //Allgemeine Fenstereinstellungen
        window = primaryStage;
        window.setTitle("eDesign");
        window.getIcons().add(new Image ("file:Images/eIcon.png"));
        window.setMinHeight(600);
        window.setMinWidth(400);
        scene = new Scene(borderPane, 1000, 600);
        final Image cursor=new Image("file:Images/cursor.png",15,22,false,false);

        /*
        //Todo aktueller Screen versuch ich geb auf
        ArrayList<Rectangle2D> arraylistrect= new ArrayList<Rectangle2D>();
        ArrayList<Screen> arraylistscreen= new ArrayList<Screen>();
        for (Screen screen : Screen.getScreens()) {
            Rectangle2D bounds = screen.getVisualBounds();
            arraylistrect.add(bounds);
            arraylistscreen.add(screen);
        }
        zweiterscreen=arraylistscreen.get(1).getVisualBounds();
        //System.out.println("2 "+zweiterscreen);
        //2ter versuch
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[]    gs = ge.getScreenDevices();
        StringBuilder sb = new StringBuilder();
        DisplayMode dm;
        for (int i = 0; i < gs.length; i++) {
            dm = gs[i].getDisplayMode();
            sb.append(i + ", width: " + dm.getWidth() + ", height: " + dm.getHeight() + "\n");
        }
        //System.out.println("2 "+sb);
        //Ende versuch
*/
        //Löschen funktion anfang mit tastendruck wurde missbraucht
        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.DELETE) {

                    System.out.println(" Screen: delete");
                }
                if (event.getCode() == KeyCode.CONTROL) {

                    isControlPressed = true;
                }
            }
        });
        ///*
        //Anfang mehrere Objekte bewegen
        scene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent event)
            {if (event.getCode() == KeyCode.CONTROL) {isControlPressed = false;}}
        });

        scene.setOnMouseClicked(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {

                if(event.getButton() != MouseButton.PRIMARY || !isControlPressed) {return;}
                xStartPosition = event.getSceneX();
                yStartPosition = event.getSceneY();
                for(Bauelemente b : arraylist)
                {
                    if (b.isMouseInsideImage())
                    {
                        if(!draglist.contains(b)) {
                            draglist.add(b);
                            b.select();
                            //System.out.println("dragliste:");
                        }
                        else
                        {
                            draglist.remove(b);
                            b.deselect();
                        }
                    }
                }
            }
        });
        //Drag eines Bauteils
        scene.setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event) {
                if(event.getButton() != MouseButton.PRIMARY || isControlPressed) {return;}

                xStartPosition = event.getSceneX();
                yStartPosition = event.getSceneY();
                for(Bauelemente b : arraylist)
                {
                    if (b.isMouseInsideImage() && !draglist.contains(b))
                    {
                        draglist.add(b);
                        b.select();
                        //System.out.println("drag Bauteil:");
                        //for (Bauelemente d: draglist) {System.out.println(d);}
                    }
                }
            }
        });

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(event.getButton( )!= MouseButton.PRIMARY || isControlPressed) {return;}
                double xDistance = event.getSceneX() - xStartPosition;
                double yDistance = event.getSceneY() - yStartPosition;
                for (Bauelemente d: draglist) {
                    //if( d instanceof Leitung)
                    d.preview(xDistance, yDistance);}
            }
        });

        scene.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event) {
                if(event.getButton()!= MouseButton.PRIMARY || isControlPressed) {return;}
                double xDistance = event.getSceneX() - xStartPosition;
                double yDistance = event.getSceneY() - yStartPosition;
                for (Bauelemente d: draglist) {d.move(xDistance, yDistance);
                    d.deselect();}
                xStartPosition = 0;
                yStartPosition = 0;
                draglist.clear();
            }
        });
        //Ende mehrere Objekte bewegen
        //*/

        //Menüpunkt "Datei" erstellen
        Menu fileMenu = new Menu("_Menü");
        //Submenü/Unterpunkte zu fileMenu

        //Menüpunkt "Hilfe" erstellen und hinzufügen
        //Menu helpMenu = new Menu("_Hilfe");
        MenuItem help=new MenuItem("Hilfe");
        help.setOnAction(e->{showhelp();});
        //helpMenu.getItems().add(help);
        help.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        fileMenu.getItems().add(help);
        fileMenu.getItems().add(new SeparatorMenuItem());


        //Unterpunkt: Neu
        MenuItem newMenuItem = new MenuItem("Neu");
        newMenuItem.setOnAction(e -> {
            //Alles löschen und neu aufbauen Programm neustarten
            newEmpty();
        });
        newMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        fileMenu.getItems().add(newMenuItem);

        //Unterpunkt: Öffnen
        MenuItem openMenuItem = new MenuItem("Öffnen");
        openMenuItem.setOnAction(e -> {
            //File öffnen
            open();});
        openMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        fileMenu.getItems().add(openMenuItem);
        fileMenu.getItems().add(new SeparatorMenuItem());

        MenuItem autosave=new MenuItem("Speichern");
        autosave.setOnAction(e->{
            //Speichert in das zuvor gespeicherte File
            autosave();
        });
        autosave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        fileMenu.getItems().add(autosave);

        MenuItem save=new MenuItem("Speichern unter");
        save.setOnAction(e->{
            //Speichern unter
            saveas();
        });
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+U"));

        //Hinzufügen zum Menü
        fileMenu.getItems().add(save);
        fileMenu.getItems().add(new SeparatorMenuItem());

        MenuItem close= new MenuItem("Schließen");
        close.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        close.setOnAction(e->{
            ButtonType no = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yes = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION," ",yes,no);
            alert.setX(primScreenBounds.getWidth() /2 -212);
            alert.setY(primScreenBounds.getHeight() /2 -65);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("file:src/main/java/Starter/Css.css");
            alert.setTitle("Programm schliessen?");
            alert.setHeaderText("");
            alert.setContentText("Alle nicht gespeicherten Änderungen werden gelöscht.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yes) {
                System.exit(0);
            } else return;
            });
        fileMenu.getItems().add(close);
        /*
        //Menüpunkt "Bearbeiten" erstellen
        Menu editMenu = new Menu("_Bearbeiten");
        //Submenü/Unterpunkte zu editMenu
        editMenu.getItems().add(new MenuItem("Rückgängig"));
        editMenu.getItems().add(new SeparatorMenuItem());
        editMenu.getItems().add(new MenuItem("Ausschneiden"));
        editMenu.getItems().add(new MenuItem("Kopieren"));
        editMenu.getItems().add(new MenuItem("Einfügen"));
        editMenu.getItems().add(new MenuItem("Löschen"));
        editMenu.getItems().add(new SeparatorMenuItem());
        editMenu.getItems().add(new MenuItem("Screenshot"));
        //Menüpunkt "Ansicht" erstellen
        Menu viewMenu = new Menu("_Ansicht");
        //Submenü/Unterpunkte zu editMenu
        viewMenu.getItems().add(new MenuItem("Theme ändern"));
        viewMenu.getItems().add(new MenuItem("Schriftgröße ändern"));
        */





        //Menüleiste zusammenführen
        menuBar.getMenus().addAll(fileMenu);//editMenu, viewMenu, helpMenu);
        //Vbox Größe
        vbox.setPrefSize(100,100);
        //VBox Style
        vbox.setStyle("-fx-background-color: black;"
                + "-fx-border-style: solid;"
                + "-fx-border-color: darkgrey;"
                + "-fx-border-width: 0 3 0 0;"
                + "-fx-padding: 10.5px;");

        //Zeichnet das Gitter und fügt es zur borderpane
        drawGitter(gc);
        borderPane.getChildren().add(canvas);

        //++++++++++VBOX Bauteile mit Handler++++++++++
        //
        //Icon für den Widerstand auf der Vbox
        final ImageView imageviewWiderstand = new ImageView();
        final Image widerstand=new Image("file:Images/widerstand.png",100,100,false,false);
        imageviewWiderstand.setImage(widerstand);
        vbox.getChildren().addAll(imageviewWiderstand);
        final Image widerstandSchrift= new Image("file:Images/widerstandSchrift.png",100,100,false,false);

        //Mouse Over für das Einblenden der IconBezeichnung
        imageviewWiderstand.setOnMouseEntered(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {
                imageviewWiderstand.setImage(widerstandSchrift);
            }});
        //Icons wieder ohne Schrift anzeigen wenn man Objekt verlässt
        imageviewWiderstand.setOnMouseExited(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {
                imageviewWiderstand.setImage(widerstand);
            }});

        //Icon für den Kondensator auf der Vbox
        final ImageView imageviewKondensator = new ImageView();
        final Image kondensator=new Image("file:Images/kondensator.png",100,100,false,false);
        imageviewKondensator.setImage(kondensator);
        vbox.getChildren().addAll(imageviewKondensator);
        final Image kondensatorSchrift= new Image("file:Images/kondensatorSchrift.png",100,100,false,false);

        //Mouse Over für das Einbleinden der IconBezeichnung
        imageviewKondensator.setOnMouseEntered(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {
                imageviewKondensator.setImage(kondensatorSchrift);
            }});
        //Icons wieder ohne Schrift anzeigen wenn man Objekt verlässt

        imageviewKondensator.setOnMouseExited(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {
                imageviewKondensator.setImage(kondensator);
            }});

        //Icon für die Spule auf der Vbox
        final ImageView imageviewSpule = new ImageView();
        final Image spule=new Image("file:Images/spule.png",100,100,false,false);
        imageviewSpule.setImage(spule);
        vbox.getChildren().addAll(imageviewSpule);
        final Image spuleSchrift= new Image("file:Images/spuleSchrift.png",100,100,false,false);
        //Mouse Over für das Einblenden der IconBezeichnung
        imageviewSpule.setOnMouseEntered(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {
                imageviewSpule.setImage(spuleSchrift);
            }});
        imageviewSpule.setOnMouseExited(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {
                imageviewSpule.setImage(spule);
            }});

        //Icon für die spannungsquelle auf der Vbox
        final ImageView imageviewSpannungsquelle = new ImageView();
        final Image spannungsquelle=new Image("file:Images/spannungsquelle.png",100,100,false,false);
        imageviewSpannungsquelle.setImage(spannungsquelle);
        vbox.getChildren().addAll(imageviewSpannungsquelle);
        final Image spannungsquelleSchrift= new Image("file:Images/spannungsquelleSchrift.png",100,100,false,false);
        //Mouse Over für das Einbleinden der IconBezeichnung
        imageviewSpannungsquelle.setOnMouseEntered(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {imageviewSpannungsquelle.setImage(spannungsquelleSchrift);
            }});
        imageviewSpannungsquelle.setOnMouseExited(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {imageviewSpannungsquelle.setImage(spannungsquelle);
            }});


        //++++Ende der VBOX Elemente++++++



        //+++++++++DRAG AND DROP von Vbox auf  Gitter

        //Images beim drag von der Vbox auf das Borderlane
        final ImageView imageviewSpannungsquelle1 = new ImageView();
        final Image spannungsquelle00T =new Image("file:Images/Bauelementetransparent/spannungsquelle00T.png",50,50,false,false);
        imageviewSpannungsquelle1.setImage(spannungsquelle00T);

        final  ImageView imageviewSpule1 = new ImageView();
        final Image spule00T =new Image("file:Images/Bauelementetransparent/spule00T.png",50,50,false,false);
        imageviewSpule1.setImage(spule00T);

        final ImageView imageviewKondensator1 = new ImageView();
        final Image kondensator00T =new Image("file:Images/Bauelementetransparent/kondensator00T.png",50,50,false,false);
        imageviewKondensator1.setImage(kondensator00T);

        final ImageView imageviewWiderstand1 = new ImageView();
        final Image widerstand00T =new Image("file:Images/Bauelementetransparent/widerstand00T.png",50,50,false,false);
        imageviewWiderstand1.setImage(widerstand00T);

        // Drag and Drop von VBox aufs Borderpane
        imageviewSpannungsquelle.setOnMouseDragged(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event)
            {
                imageviewSpannungsquelle1.setX(event.getSceneX()-25);
                imageviewSpannungsquelle1.setY(event.getSceneY()-25);
                borderPane.getChildren().remove(imageviewSpannungsquelle1);
                borderPane.getChildren().add(imageviewSpannungsquelle1);
            }
        });
        imageviewSpannungsquelle.setOnMouseReleased(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event)
            {
                borderPane.getChildren().remove(imageviewSpannungsquelle1);
                double x=0,y=0;
                x=round(event.getSceneX());
                y=round(event.getSceneY());
                if(x<150) return;
                else {
                    IDSpannungsquelle++;
                    Spannungsquelle spannungsquelle = new Spannungsquelle(IDSpannungsquelle, x, y, 0);
                    arraylist.add(spannungsquelle);
                    //spannungsquelle.draw(gc,0);
                    spannungsquelle.draw(borderPane);
                    //xmlstring=spannungsquelle.toxml(xmlstring);

                }
            }
        });
        imageviewSpule.setOnMouseDragged(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event)
            {
                imageviewSpule1.setX(event.getSceneX()-25);
                imageviewSpule1.setY(event.getSceneY()-25);
                borderPane.getChildren().remove(imageviewSpule1);
                borderPane.getChildren().add(imageviewSpule1);
            }
        });
        imageviewSpule.setOnMouseReleased(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event)
            {
                borderPane.getChildren().remove(imageviewSpule1);
                double x=0,y=0;
                //System.out.println("losgelassen an: X: "+event.getSceneX()+" Y: "+event.getSceneY());
                x = round(event.getSceneX());
                y = round(event.getSceneY());
                if(x<150) return;
                else {
                    IDSpule++;
                    Spule spule = new Spule(IDSpule, x, y, 0);
                    arraylist.add(spule);
                    //spule.draw(gc,0);
                    spule.draw(borderPane);
                    //xmlstring=spule.toxml(xmlstring);
                }
            }
        });
        imageviewKondensator.setOnMouseDragged(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event)
            {
                imageviewKondensator1.setX(event.getSceneX()-25);
                imageviewKondensator1.setY(event.getSceneY()-25);
                borderPane.getChildren().remove(imageviewKondensator1);
                borderPane.getChildren().add(imageviewKondensator1);
            }
        });
        imageviewKondensator.setOnMouseReleased(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event)
            {
                borderPane.getChildren().remove(imageviewKondensator1);
                double x=0,y=0;
                //System.out.println("losgelassen an: X: "+event.getSceneX()+" Y: "+event.getSceneY());
                x = round(event.getSceneX());
                y = round(event.getSceneY());
                if(x<150) return;
                else {
                    IDKondensator++;
                    Kondensator kondensator = new Kondensator(IDKondensator, x, y, 0);
                    arraylist.add(kondensator);
                    //kondensator.draw(gc,0);
                    kondensator.draw(borderPane);
                    //xmlstring=kondensator.toxml(xmlstring);
                }
            }
        });
        imageviewWiderstand.setOnMouseDragged(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event)
            {
                imageviewWiderstand1.setX(event.getSceneX()-25);
                imageviewWiderstand1.setY(event.getSceneY()-25);
                borderPane.getChildren().remove(imageviewWiderstand1);
                borderPane.getChildren().add(imageviewWiderstand1);
            }
        });
        imageviewWiderstand.setOnMouseReleased(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event)
            {
                borderPane.getChildren().remove(imageviewWiderstand1);
                double x=0,y=0;

                //System.out.println("losgelassen an: X: "+event.getSceneX()+" Y: "+event.getSceneY());
                x = round(event.getSceneX());
                y = round(event.getSceneY());
                if(x<150) return;
                else {
                    IDWiderstand++;
                    Widerstand widerstand = new Widerstand(IDWiderstand, x, y, 0);
                    arraylist.add(widerstand);
                    //widerstand.draw(gc,0);
                    widerstand.draw(borderPane);
                    //xmlstring=widerstand.toxml(xmlstring);
                }
            }
        });

        //ENDE vom DRAG AND DROP

        /*
        //Seperator falls gewünscht
        Separator separator=new Separator();
        vbox.getChildren().add(separator);
        */
        //++++++Mülleimer++++++
        ImageView imageviewtrash=new ImageView();
        //Image trash= new Image("file:Images/muelleimerOffen.png",100,75,false,false);
        Image trash= new Image("file:Images/muelleimerOffen1.png",100,100,false,false);

        imageviewtrash.setImage(trash);
        imageviewtrash.setX(120);
        imageviewtrash.setY(400);
        vbox.getChildren().addAll(imageviewtrash);
        //++++ENDE Mülleimer++++
        //

        //+++++Files in das Fenster ziehen
              scene.setOnDragOver(new EventHandler<DragEvent>() {

            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
                //System.out.println("setOnDragOver");
            }});
        scene.setOnDragDropped(new EventHandler<DragEvent>()  {

            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles())
                {
                    success = true;
                    for (File file:db.getFiles())
                    {
                        //entscheidet ob es ein xml file ist
                        if(file.getName().toLowerCase().endsWith(".xml"))
                        {
                            if(arraylist.isEmpty()) {FileReader1(file);}
                            else{
                                ButtonType no = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);
                                ButtonType yes = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION," ",yes,no);
                                alert.setX(primScreenBounds.getWidth() /2 -212);
                                alert.setY(primScreenBounds.getHeight() /2 -65);
                                DialogPane dialogPane = alert.getDialogPane();
                                dialogPane.getStylesheets().add("file:src/main/java/Starter/Css.css");
                                alert.setTitle("Achtung");
                                alert.setHeaderText("");
                                alert.setContentText("Wollen sie das Projekt: \"" + file.getName() + "\" laden? \nNicht gespeichertes geht verloren");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == yes) {
                                    FileReader1(file);
                                } else return;
                            }
                        }
                        else
                        {
                            Alert alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setX(primScreenBounds.getWidth() /2 -212);
                            alert.setY(primScreenBounds.getHeight() /2 -65);
                            DialogPane dialogPane = alert.getDialogPane();
                            dialogPane.getStylesheets().add("file:src/main/java/Starter/Css.css");
                            alert.setTitle("Falsches Dateiformat");
                            alert.setHeaderText("");
                            alert.setContentText("Das Programm unterstützt nur das XML Format");
                            alert.showAndWait();
                        }
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }});
        //++++Ende Files in das Fenster ziehen

        //+++++Leitung zeichnen++++++
        //Neue Leitung zeichnen mit 2 Mausklicks auf Canvas
        canvas.setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event)
            {drawLines(event, gc);}});
        //+++++Ende Leitung zeichnen++++

        //++++++Maus Cursor++++++
        //Todo Mouse Cursor ändern falls gewünscht
        scene.setOnMouseEntered(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event)
            {

                //scene.setCursor(new ImageCursor(cursor));
            }});
        //+++++Ende Maus Cursor

        //Untere Vbox Style
        vboxEmpty.setPrefSize(15,100);
        //VBox Style
        vboxEmpty.setStyle("-fx-background-color: black;"
                + "-fx-border-style: solid;"
                + "-fx-border-color: darkgrey;"
                + "-fx-border-width: 0 0 0 3;"
                + "-fx-padding: 10.5px;");


        //++++++HBox als untere Leiste mit Tooltipps++++++++
        textToolTipps.setFill(Color.WHITE);
        hboxTipps.getChildren().add(textToolTipps);

        hboxTipps.setPrefSize(100,15);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(8000),
                ae ->changetip()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        //HBox Style
        hboxTipps.setStyle("-fx-background-color: black;"
                + "-fx-border-style: solid;"
                + "-fx-border-color: darkgrey;"
                + "-fx-border-width: 3 0 0 0;"
                + "-fx-padding: 10.5px;");
        //++++++Ende Hbox


        //Darstellung der menubar und vbox auf Borderpane mit Stylesheet
        borderPane.setRight(vboxEmpty);
        borderPane.setBottom(hboxTipps);
        borderPane.setTop(menuBar);
        borderPane.setLeft(vbox);
        scene.getStylesheets().add("file:src/main/java/Starter/Css.css");
        window.setScene(scene);
        window.show();
    }
    //Linien für das Gitter Werden gezeichnet;
    private void drawGitter(GraphicsContext gc){
        int i,j;
        gc.beginPath();
        gc.moveTo(0,0);
        gc.setStroke(Color.WHITE);
        for(i =0; i<=dim.getWidth(); i+=25){ //Senkrechte linien werden gezeichnet
            gc.moveTo(i,0);
            gc.lineTo(i, dim.getHeight());
        }
        for(j=0; j<=dim.getHeight(); j+=25){ //waagrechte linien werden gezichnet
            gc.moveTo(0,j);
            gc.lineTo(dim.getWidth(),j);
        }
        gc.stroke();
    }
    //Speichern unter
    public void saveas()
    {
        FileChooser fileChoose= new FileChooser();
        fileChoose.setTitle("Speichern unter...");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("XML Dateien (*.xml)", "*.xml");
        fileChoose.getExtensionFilters().add(extFilter);
        file = fileChoose.showSaveDialog(window);
        //Für jedes Element in der Liste wird die toxml aufgerufen und der String erweitert
        try {
            for(Bauelemente a: arraylist)
            {
                xmlstring=a.toxml(xmlstring);
            }
            String xmlheader="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            String start="<Header>\n"
                    + "<name>"+file.getName()+"</name>\n"
                    + "\t<Teile>\n\n";
            String end="\t</Teile>\n"
                    + "</Header>\n";
            all=start+xmlstring+end;
            FileWriter writer = new FileWriter(file);
            writer.write(all);
            writer.close();

        }catch (Exception f){//Catch exception if any
            //System.err.println("Error: " + f.getMessage());
        }
        xmlstring="";

    }
    //Speichern und vorhandenes überschreiben
    public void autosave()
    {
        if(file==null)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setX(primScreenBounds.getWidth() /2 -212);
            alert.setY(primScreenBounds.getHeight() /2 -65);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("file:src/main/java/Starter/Css.css");
            alert.setTitle("Fehler");
            alert.setHeaderText("");
            alert.setContentText("Es wurde kein Speicherort ausgewählt");
            alert.showAndWait();
        }
        else
        {
            //Für jedes Element in der Liste wird die toxml aufgerufen und der String erweitert
            try {
                for(Bauelemente a: arraylist)
                {
                    xmlstring=a.toxml(xmlstring);
                }
                String xmlheader="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
                String start="<Header>\n"
                      //  + "<name>"+file.getName()+"</name>\n"
                        + "\t<Teile>\n\n";
                String end="\t</Teile>\n"
                        + "</Header>\n";
                all=start+xmlstring+end;
                FileWriter writer = new FileWriter(file);
                writer.write(all);
                writer.close();
            }catch (Exception f) {//Catch exception if any
                //System.err.println("Error: " + f.getMessage());
            }
            xmlstring="";
        }
    }
    //Öffnen eines XML Files
    public void open()
    {
        if(arraylist.isEmpty()){
            file=null;
            //XKondensator, YKondensator, XlineStart, XLineEnd
            double xkon, yspu,xwid,yspa,xspa,ywid,ykon,xspu,xles,yles,xlee,ylee;
            //KondensatorOrientation
            double konOr,spaOr,widOr,spuOr;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Öffnen");
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("XML Dateien (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            file = fileChooser.showOpenDialog(window);
            deleteall();
            FileReader1(file);        }
        else {
            ButtonType no = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yes = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION," ",yes,no);
            alert.setX(primScreenBounds.getWidth() /2 -212);
            alert.setY(primScreenBounds.getHeight() /2 -65);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("file:src/main/java/Starter/Css.css");
            alert.setTitle("Neues Projekt anlegen");
            alert.setHeaderText("");
            alert.setContentText("Wollen Sie das aktuelle Projekt löschen und ein neues laden? Alle nicht gespeicherten Änderungen werden gelöscht.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yes) {
                file=null;
                //XKondensator, YKondensator, XlineStart, XLineEnd
                double xkon, yspu,xwid,yspa,xspa,ywid,ykon,xspu,xles,yles,xlee,ylee;
                //KondensatorOrientation
                double konOr,spaOr,widOr,spuOr;
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Öffnen");
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("XML Dateien (*.xml)", "*.xml");
                fileChooser.getExtensionFilters().add(extFilter);
                file = fileChooser.showOpenDialog(window);
                deleteall();
                FileReader1(file);
            } else return;
        }

    }

    //Zeichne Linie zeichnen per dragn drop
    public void drawLines(MouseEvent event, GraphicsContext gc)
    {
        /*Wenn Maus gedrückt setzte startpunkt
           lineZeichnen ist hintergrundlinie  */
        drawline.setStrokeWidth(5);
        drawline.setStroke(Color.GREY);
        drawline.setStartX(round(event.getSceneX()));
        drawline.setStartY(round(event.getSceneY()));
            xStartLeitung=round(event.getSceneX());
            yStartLeitung=round(event.getSceneY());

        /*Wenn Maus gedrückt ist wird linezeichnen gezeichnet und immer wieder gelöscht
          wird nicht am ende gerundet*/
            canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if(draglist.size() > 0){
                        return;
                    }
                    xEndLeitung=round(event.getSceneX());
                    yEndLeitung=round(event.getSceneY());
                    drawline.setEndX(event.getSceneX());
                    drawline.setEndY(event.getSceneY());
                    borderPane.getChildren().remove(drawline);
                    borderPane.getChildren().add(drawline);
                }
            });
            //Sobald Maus losgelassen wird richtige Linie gezeichnet
            canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(draglist.size() > 0){
                        return;
                    }
                    xEndLeitung = round(event.getSceneX());
                    yEndLeitung = round(event.getSceneY());
                    borderPane.getChildren().remove(drawline);

                    //Abfrage ob Start und Endpunkt gleich
                    if(xStartLeitung==xEndLeitung&&yStartLeitung==yEndLeitung)
                    {
                        //Leitungen dürfen nicht auf selben Punkt sein
                        xEndLeitung=0;
                        yEndLeitung=0;
                    }
                    else {
                        IDLeitung++;
                        Leitung leitung1 = new Leitung(IDLeitung, xStartLeitung, yStartLeitung, 0, xEndLeitung, yEndLeitung);
                        leitung1.draw(borderPane);
                        arraylist.add(leitung1);
                        xStartLeitung = 0;
                        yStartLeitung = 0;
                        xEndLeitung = 0;
                        yEndLeitung = 0;
                    }
                }
            });


        /*clickCount++;
        clickCount=clickCount%2;
        if(event.getButton()== MouseButton.PRIMARY) {
            //borderPane.getChildren().add(lineZeichnen);
            lineZeichnen.setStrokeWidth(5);
            lineZeichnen.setStroke(Color.GRAY);

            if(clickCount==1)
            {
                xStartLeitung=rundenLeitungen(event.getSceneX());
                yStartLeitung=rundenLeitungen(event.getSceneY());
            }
            else if (clickCount==0)
            {
                xEndLeitung=rundenLeitungen(event.getSceneX());
                yEndLeitung=rundenLeitungen(event.getSceneY());
                //borderPane.getChildren().remove(lineZeichnen);
            }


            if(xStartLeitung!=0 && yStartLeitung!=0&&xEndLeitung!=0&&yEndLeitung!=0)
            {
                if(xStartLeitung==xEndLeitung&&yStartLeitung==yEndLeitung)
                {
                    //Leitungen dürfen nicht auf selben Punkt sein
                    xEndLeitung=0;
                    yEndLeitung=0;
                }
                else {
                    IDLeitung++;
                    Leitung leitung1 = new Leitung(IDLeitung, xStartLeitung, yStartLeitung, 0, xEndLeitung, yEndLeitung);
                    leitung1.draw1(borderPane);
                    //xmlstring=leitung1.toxml(xmlstring);
                    arraylist.add(leitung1);
                    xStartLeitung = 0;
                    yStartLeitung = 0;
                    xEndLeitung = 0;
                    yEndLeitung = 0;
                }

            }
            else return;
        }*/
    }
    /*
    //Snap ans Raster der Bauteile
    public double rundenBauteile(double runden) {
        if (runden % 50 < 25) {
            return runden - (runden % 50);
        } else if (runden % 50 >= 25) {
            return runden + (50 - (runden % 50));
        } else return 0;
    }*/

    //Snap der Linien ans Raster
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
    //Neu
    public void newEmpty()
    {
        if(arraylist.isEmpty()){
          return;
        }
        else {
            ButtonType no = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yes = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION," ",yes,no);
            alert.setX(primScreenBounds.getWidth() /2 -212);
            alert.setY(primScreenBounds.getHeight() /2 -65);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("file:src/main/java/Starter/Css.css");
            alert.setTitle("Neues Projekt anlegen");
            alert.setHeaderText("");
            alert.setContentText("Wollen Sie das aktuelle Projekt löschen und ein neues anlegen? Alle nicht gespeicherten Änderungen werden gelöscht.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yes) {
                deleteall();
                //xmlstring="";
            } else return;
        }

    }
    //Löscht alles auf dem Borderpane und Graphic Content und erschafft ein neues
    public void deleteall()
    {
        IDKondensator=0;
        IDLeitung=0;
        IDSpannungsquelle=0;
        IDWiderstand=0;
        IDSpule=0;
        xmlstring="";
        arraylist.clear();
        //gc.clearRect(0, 0, dim.getWidth(), dim.getHeight());
        //drawGitter(gc);
        borderPane.getChildren().clear();
        borderPane.getChildren().add(canvas);
        borderPane.setRight(vboxEmpty);
        borderPane.setBottom(hboxTipps);
        borderPane.setTop(menuBar);
        borderPane.setLeft(vbox);
    }
    public void FileReader1(File file)
    {
        try {
            //prüft ob das file etwas beinhaltet
            BufferedReader reader = new BufferedReader(new FileReader(file));
            if(reader.readLine()==null) {
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setX(primScreenBounds.getWidth() /2 -212);
                alert.setY(primScreenBounds.getHeight() /2 -65);
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add("file:src/main/java/Starter/Css.css");
                    alert.setTitle("Leeres Dokument");
                    alert.setHeaderText("");
                    alert.setContentText("Konnte Datei \"" + file.getName() + "\" nicht laden, da die Datei leer ist");
                    alert.showAndWait();
            }
            else
            {
        //XKondensator, YKondensator, XlineStart, XLineEnd
        double xkon, yspu,xwid,yspa,xspa,ywid,ykon,xspu,xles,yles,xlee,ylee;
        //KondensatorOrientation
        int konOr,spaOr,widOr,spuOr;

            //prüft ob es ein file gibt
            if (file != null) {
                //Löscht die borderpane
                deleteall();
                //Zeig den File Inhalt in String
                try (Scanner scanner = new Scanner(new File(file.toString()))) {
                    //entscheidet ob Kondensator usw
                    while(scanner.hasNext())
                    {
                        String line=scanner.nextLine();
                        if(line.indexOf("<Kondensator>")!=-1)
                        {
                            try {
                                //Schreibt die Werte aus dem String in Variable
                                line = scanner.nextLine();
                                IDKondensator = Integer.parseInt(line.substring(line.indexOf("<ID>") + 4, line.indexOf("</ID>")));
                                line = scanner.nextLine();
                                xkon = (double) Integer.parseInt(line.substring(line.indexOf("<PositionX>") + 11, line.indexOf("</PositionX>")));
                                line = scanner.nextLine();
                                ykon = (double) Integer.parseInt(line.substring(line.indexOf("<PositionY>") + 11, line.indexOf("</PositionY>")));
                                line = scanner.nextLine();
                                konOr = Integer.parseInt(line.substring(line.indexOf("<Richtung>") + 10, line.indexOf("</Richtung>")));
                                Kondensator kondensator1 = new Kondensator(IDKondensator, xkon, ykon, konOr);
                                //Kondensator wird auf BorderPane gezeichnet
                                kondensator1.draw(borderPane);
                                //Kondensator wird in String gespeichert um es später wieder abspeichern zu können
                                arraylist.add(kondensator1);
                                IDKondensator=0;
                            }
                            catch (Exception f){
                                if(IDKondensator!=0)brokenfilealert("Kondensator bei ID: "+IDKondensator+" Zeile: "+line+"\nBauelement wird möglicherweise nicht angezeigt!");
                                else brokenfilealert("Kondensator in Zeile: "+line+"\nBauelement wird möglicherweise nicht angezeigt!");
                            }
                        }
                        else if(line.indexOf("<Spule>")!=-1)
                        {
                            try{
                            //Schreibt die Werte aus dem String in Variable
                             line=scanner.nextLine();
                            IDSpule=Integer.parseInt(line.substring(line.indexOf("<ID>")+4,line.indexOf("</ID>")));
                            line=scanner.nextLine();
                            xspu=(double)Integer.parseInt(line.substring(line.indexOf("<PositionX>")+11, line.indexOf("</PositionX>")));
                            line=scanner.nextLine();
                            yspu=(double)Integer.parseInt(line.substring(line.indexOf("<PositionY>")+11, line.indexOf("</PositionY>")));
                            line=scanner.nextLine();
                            spuOr=Integer.parseInt(line.substring(line.indexOf("<Richtung>")+10, line.indexOf("</Richtung>")));
                            Spule spule1= new Spule(IDSpule,xspu,yspu,spuOr);
                            //Spule wird auf BorderPane gezeichnet
                            spule1.draw(borderPane);
                            arraylist.add(spule1);
                            IDSpule=0;
                             }
                            catch (Exception f){
                                if(IDSpule!=0)brokenfilealert("Spule bei ID: "+IDSpule +"\nZeile: "+line+"\nBauelement wird möglicherweise nicht angezeigt!");
                                else brokenfilealert("Spule in Zeile: "+line+"\nBauelement wird möglicherweise nicht angezeigt!");
                    }
                        }
                        else if(line.indexOf("<Widerstand>")!=-1)
                        {
                            try {
                                //Schreibt die Werte aus dem String in Variable
                                line = scanner.nextLine();
                                IDWiderstand = Integer.parseInt(line.substring(line.indexOf("<ID>") + 4, line.indexOf("</ID>")));
                                line = scanner.nextLine();
                                xwid = (double) Integer.parseInt(line.substring(line.indexOf("<PositionX>") + 11, line.indexOf("</PositionX>")));
                                line = scanner.nextLine();
                                ywid = (double) Integer.parseInt(line.substring(line.indexOf("<PositionY>") + 11, line.indexOf("</PositionY>")));
                                line = scanner.nextLine();
                                widOr = Integer.parseInt(line.substring(line.indexOf("<Richtung>") + 10, line.indexOf("</Richtung>")));
                                Widerstand widerstand1 = new Widerstand(IDWiderstand, xwid, ywid, widOr);
                                //Widerstand wird auf BorderPane gezeichnet
                                widerstand1.draw(borderPane);
                                arraylist.add(widerstand1);
                                IDWiderstand=0;

                            }
                                catch (Exception f){
                                    if(IDWiderstand!=0) brokenfilealert("Widerstand bei ID: "+ IDWiderstand+"\nZeile: "+line+"\nBauelement wird möglicherweise nicht angezeigt!");
                                    else brokenfilealert("Widerstand in Zeile: "+line+"\nBauelement wird möglicherweise nicht angezeigt!");
                                }
                        }
                        else if(line.indexOf("<Spannungsquelle>")!=-1)
                        {
                            try {
                                //Schreibt die Werte aus dem String in Variable
                                line = scanner.nextLine();
                                IDSpannungsquelle = Integer.parseInt(line.substring(line.indexOf("<ID>") + 4, line.indexOf("</ID>")));
                                line = scanner.nextLine();
                                xspa = (double) Integer.parseInt(line.substring(line.indexOf("<PositionX>") + 11, line.indexOf("</PositionX>")));
                                line = scanner.nextLine();
                                yspa = (double) Integer.parseInt(line.substring(line.indexOf("<PositionY>") + 11, line.indexOf("</PositionY>")));
                                line = scanner.nextLine();
                                spaOr = Integer.parseInt(line.substring(line.indexOf("<Richtung>") + 10, line.indexOf("</Richtung>")));
                                Spannungsquelle spannungsquelle1 = new Spannungsquelle(IDSpannungsquelle, xspa, yspa, spaOr);
                                //Sannungsquelle wird auf BorderPane gezeichnet
                                spannungsquelle1.draw(borderPane);
                                arraylist.add(spannungsquelle1);
                                IDSpannungsquelle=0;
                            }
                            catch (Exception f){
                                if(IDSpannungsquelle!=0)brokenfilealert("Spannungsquelle bei ID: "+IDSpannungsquelle+"\nZeile: "+line+"\nBauelement wird möglicherweise nicht angezeigt!");
                                else brokenfilealert("Spannungsquelle in Zeile: "+line+"\nBauelement wird möglicherweise nicht angezeigt!");
                            }
                        }
                        else if(line.indexOf("<Leitung>")!=-1)
                        {
                            try {
                                //Schreibt die Werte aus dem String in Variable
                                line = scanner.nextLine();
                                IDLeitung = Integer.parseInt(line.substring(line.indexOf("<ID>") + 4, line.indexOf("</ID>")));
                                line = scanner.nextLine();
                                xles = (double) Integer.parseInt(line.substring(line.indexOf("<PositionXstart>") + 16, line.indexOf("</PositionXstart>")));
                                line = scanner.nextLine();
                                yles = (double) Integer.parseInt(line.substring(line.indexOf("<PositionYstart>") + 16, line.indexOf("</PositionYstart>")));
                                line = scanner.nextLine();
                                xlee = (double) Integer.parseInt(line.substring(line.indexOf("<PositionXend>") + 14, line.indexOf("</PositionXend>")));
                                line = scanner.nextLine();
                                ylee = (double) Integer.parseInt(line.substring(line.indexOf("<PositionYend>") + 14, line.indexOf("</PositionYend>")));
                                Leitung leitung1 = new Leitung(IDLeitung, xles, yles, 0, xlee, ylee);
                                leitung1.draw(borderPane);
                                arraylist.add(leitung1);
                                IDLeitung=0;
                            }
                            catch (Exception f){
                                if(IDLeitung!=0)brokenfilealert("Leitung bei ID: "+IDLeitung+"\nZeile: "+line+"\nBauelement wird möglicherweise nicht angezeigt!");
                                else brokenfilealert("Leitung in Zeile: "+line+"\nBauelement wird möglicherweise nicht angezeigt!");
                            }

                        }

                    }
                } catch (Exception f){//Catch exception if any
                    System.err.println("Error: " + f.getMessage());
                    System.out.println("leer");
                }
            }
            //Todo ist eig falsch kommt beim abbrechen button?

            }
        }
        catch (Exception f){System.err.println("Error: " + f.getMessage());}


    }
    public void showhelp()
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setX(primScreenBounds.getWidth() /2 -525);
        alert.setY(primScreenBounds.getHeight() /2 -362);
         //DialogPane braucht man zum stylen
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("file:src/main/java/Starter/Css.css");
        alert.setGraphic(helpView);
        alert.setTitle("Hilfe");
        alert.setHeaderText(" ");
        //alert.setContentText(" ");
        alert.showAndWait();
    }
    public void brokenfilealert(String Bauteil)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setX(primScreenBounds.getWidth() /2 -212);
        alert.setY(primScreenBounds.getHeight() /2 -65);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("file:src/main/java/Starter/Css.css");
        alert.setTitle("Fehler in Datei");
        alert.setHeaderText("");
        alert.setContentText("Fehler in Datei: " + Bauteil);
        alert.showAndWait();
    }
    public void changetip()
    {
        timernumber++;
        textToolTipps.setStyle("-fx-font: 16 arial");
        hboxTipps.getChildren().remove(textToolTipps);
        if(timernumber==7)timernumber=1;
        switch (timernumber){
            case 1:textToolTipps.setText("Tipp: Bauteile werden gelöscht, indem man sie auf den Mülleimer zieht und dann loslässt");break;
            case 2:textToolTipps.setText("Tipp: Bauelemente können durch Rechtsklick gedreht werden");break;
            case 3:textToolTipps.setText("Tipp: Start- und Endpunkte der Leitungen können verändert werden, indem man sie anklickt und an einen anderen Punkt zieht");break;
            case 4:textToolTipps.setText("Tipp: Bauteile können durch Ziehen des Bauteils auf die Editorfläche plaziert werden");break;
            case 5:textToolTipps.setText("Tipp: Leitungen werden durch Klicken und Ziehen der Maus gezeichnet");break;
            case 6:textToolTipps.setText("Tipp: Mehrere Bauteile können mithilfe der Steuerungstaste bewegt werden");break;
        }
        hboxTipps.getChildren().add(textToolTipps);


    }
}
