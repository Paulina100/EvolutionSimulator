package evolutionSimulation.gui;

import evolutionSimulation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import static java.lang.System.exit;
import static java.lang.System.out;

//public class App extends Application {
//    @Override
//    public void init(){
//
//    }
//
//    @Override
//    public void start(Stage primaryStage){
//        GridPane gridPane = new GridPane();
//        for (int i = 0; i < 3; i++) gridPane.getColumnConstraints().add(new ColumnConstraints(50));
//        for (int i = 0; i < 3; i++) gridPane.getRowConstraints().add(new RowConstraints(50));
//
//        gridPane.setGridLinesVisible(true);
//        Scene scene = new Scene(gridPane, 800, 800);
//
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//}


//import agh.ics.oop.*;
//        import javafx.application.Application;
//        import javafx.application.Platform;
//        import javafx.geometry.HPos;
//        import javafx.geometry.Pos;
//        import javafx.geometry.VPos;
//        import javafx.scene.Scene;
//        import javafx.scene.control.Button;
//        import javafx.scene.control.Label;
//        import javafx.scene.control.TextField;
//        import javafx.scene.layout.*;
//        import javafx.stage.Stage;
//
//        import static java.lang.System.exit;
//        import static java.lang.System.out;

public class App extends Application{
    AbstractWorldMap map;
    Vector2d[] positions;
    SimulationEngine engine;
    GridPane gridPane;
    Thread engineThread;


    @Override
    public void init() {
        try {
            //args = getParameters().getRaw().toArray(new String[0]);
            //directions = new OptionsParser().parse(args);
            map = new WrapMap(5, 5);
            positions = new Vector2d[]{new Vector2d(2, 2), new Vector2d(3, 4)};
            int startEnergy = 10;

            //engine = new SimulationEngine(directions, map, positions, this);
            engine = new SimulationEngine(map, positions, startEnergy, this);
        } catch (IllegalArgumentException ex){
            out.println(ex.getMessage());
            exit(0);
        }
    }

    private void startEngine(){
        if (!engineThread.isAlive()) {
            engineThread = new Thread(engine);
            engineThread.start();
        }
    }

    @Override
    public void start(Stage primaryStage){
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        TextField textField = new TextField();
        Button startButton = new Button("Start");
        startButton.setOnAction(event -> {
            engineThread = new Thread(engine);
            engineThread.start();
        });

        VBox interactiveBox = new VBox(textField, startButton);
        HBox buttonsAndMap = new HBox(interactiveBox, gridPane);

        this.updateScene();

        Scene scene = new Scene(buttonsAndMap, 800, 600);
        primaryStage.setScene(scene);
//        primaryStage.setFullScreen(true);
        primaryStage.show();

    }

    public void updateScene(){
        ColumnConstraints columnWidth = new ColumnConstraints(60);
        RowConstraints rowHeight = new RowConstraints(60);
        rowHeight.setValignment(VPos.CENTER);

        Vector2d[] boundaries = map.getBoundaries();

        int width = boundaries[1].x - boundaries[0].x + 2;
        int height = boundaries[1].y - boundaries[0].y + 2;

        for (int i = 0; i < height; i++) {
            gridPane.getRowConstraints().add(rowHeight);
        }
        for (int i = 0; i < width; i++){
            gridPane.getColumnConstraints().add(columnWidth);
        }

        Label label = new Label("y/x");
        gridPane.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);

        int val = boundaries[0].x;
        for (int i = 1; i < width; i++){
            label = new Label(Integer.toString(val));
            gridPane.add(label, i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
            val++;
        }

        val = boundaries[1].y;
        for (int i = 1; i < height; i++){
            label = new Label(Integer.toString(val));
            gridPane.add(label, 0, i);
            GridPane.setHalignment(label, HPos.CENTER);
            val--;
        }


        for (int i = boundaries[0].y; i <= boundaries[1].y; i++){
            for (int j = boundaries[0].x; j <= boundaries[1].x; j++){
                Vector2d position = new Vector2d(j, i);
                if (map.isOccupied(position)){
                    GuiElementBox box = new GuiElementBox((map.objectAt(position)));
                    gridPane.add(box.vbox, j - boundaries[0].x + 1, boundaries[1].y - i + 1);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
            }
        }


        gridPane.setGridLinesVisible(false);
        gridPane.setGridLinesVisible(true);


    }

    public void newEra() {
        Platform.runLater(()->{
            gridPane.getChildren().clear();
            gridPane.getRowConstraints().clear();
            gridPane.getColumnConstraints().clear();
            this.updateScene();
        });
    }
}

