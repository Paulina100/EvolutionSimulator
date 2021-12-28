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

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;
import static java.lang.System.out;


public class App extends Application{
    AbstractWorldMap map;
    SimulationEngine engine;
    GridPane gridPane;
    VBox interactiveBox;
    Thread engineThread;
    int width;
    int height;
    int numberOfAnimals;
    int moveEnergy;
    int startEnergy;
    int plantEnergy;
    double jungleRatio;



//    @Override
//    public void init() {
//        try {
//            //args = getParameters().getRaw().toArray(new String[0]);
//            //directions = new OptionsParser().parse(args);
//
////            int width = 5;
////            int height = 5;
////            int numberOfAnimals = 10;
////            int startEnergy = 10;
////            int plantEnergy = 10;
////            int moveEnergy = 1;
//            Grass.plantEnergy =plantEnergy;
//            Animal.moveEnergy = moveEnergy;
//            Animal.reproductionEnergy = startEnergy/2;
//
//            map = new WrapMap(width, height);
////            positions = new Vector2d[]{new Vector2d(2, 2), new Vector2d(3, 4)};
//            List<Vector2d> positions = new ArrayList<>();
//            for (int i = 0; i < numberOfAnimals; i++){
//                positions.add(new Vector2d((int) (Math.random()*height), (int) (Math.random()*width)));
//            }
//
//
//            //engine = new SimulationEngine(directions, map, positions, this);
//            engine = new SimulationEngine(map, positions, startEnergy, this);
//        } catch (IllegalArgumentException ex){
//            out.println(ex.getMessage());
//            exit(0);
//        }
//    }

    private void startEngine(){
        if (!engineThread.isAlive()) {
            engineThread = new Thread(engine);
            engineThread.start();
        }
    }

    @Override
    public void start(Stage primaryStage){
        try {
            gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
//            TextField textField = new TextField();


            Label labelWidth = new Label("Width: ");
            Label labelHeight = new Label("Height: ");
            Label labelNumberOfAnimals = new Label("Number of animals: ");
            Label labelStartEnergy = new Label("Start Energy: ");
            Label labelMoveEnergy = new Label("Move Energy: ");
            Label labelPlantEnergy = new Label("Plant Energy: ");
            Label labelJungleRatio = new Label("Jungle Ratio: ");

            TextField widthField = new TextField("5");
            TextField heightField = new TextField("5");
            TextField numberOfAnimalsField = new TextField("10");
            TextField startEnergyField = new TextField("10");
            TextField moveEnergyField = new TextField("1");
            TextField plantEnergyField = new TextField("5");
            TextField jungleRatioField = new TextField("0.3");
            Button startButton = new Button("Start");

            startButton.setOnAction(event -> {
                width = Integer.parseInt(widthField.getText());
                height = Integer.parseInt(heightField.getText());
                numberOfAnimals = Integer.parseInt(numberOfAnimalsField.getText());
                moveEnergy = Integer.parseInt(moveEnergyField.getText());
                startEnergy = Integer.parseInt(startEnergyField.getText());
                plantEnergy = Integer.parseInt(plantEnergyField.getText());
                jungleRatio = Double.parseDouble(jungleRatioField.getText());

//            Grass.plantEnergy =plantEnergy;
//            Animal.moveEnergy = moveEnergy;
//            Animal.reproductionEnergy = startEnergy/2;


                Grass.plantEnergy = plantEnergy;
                Animal.moveEnergy = moveEnergy;
                Animal.reproductionEnergy = startEnergy / 2;

                map = new WrapMap(width, height);
//            positions = new Vector2d[]{new Vector2d(2, 2), new Vector2d(3, 4)};
                List<Vector2d> positions = new ArrayList<>();
                for (int i = 0; i < numberOfAnimals; i++) {
                    positions.add(new Vector2d((int) (Math.random() * height), (int) (Math.random() * width)));
                }

                interactiveBox.getChildren().clear();
                interactiveBox.getChildren().add(gridPane);
                //engine = new SimulationEngine(directions, map, positions, this);
                engine = new SimulationEngine(map, positions, startEnergy, this);

                engineThread = new Thread(engine);
                engineThread.start();
            });

            interactiveBox = new VBox(labelWidth, widthField, labelHeight, heightField, labelNumberOfAnimals, numberOfAnimalsField, labelStartEnergy, startEnergyField, labelMoveEnergy, moveEnergyField, labelPlantEnergy, plantEnergyField, labelJungleRatio, jungleRatioField, startButton, gridPane);
//        HBox buttonsAndMap = new HBox(interactiveBox, gridPane);

//            this.updateScene();

            Scene scene = new Scene(interactiveBox, 800, 600);
            primaryStage.setScene(scene);
//        primaryStage.setFullScreen(true);
            primaryStage.show();


        }catch (IllegalArgumentException ex){
        System.out.println(ex.getMessage());
        System.exit(1);
        }

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

