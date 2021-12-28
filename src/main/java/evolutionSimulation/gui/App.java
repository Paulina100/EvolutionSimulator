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
    int maxHeight = 600;
    int maxWidth = 600;


    @Override
    public void start(Stage primaryStage){
        try {
            gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);

            Label labelWidth = new Label("Width: ");
            Label labelHeight = new Label("Height: ");
            Label labelNumberOfAnimals = new Label("Number of animals: ");
            Label labelStartEnergy = new Label("Start Energy: ");
            Label labelMoveEnergy = new Label("Move Energy: ");
            Label labelPlantEnergy = new Label("Plant Energy: ");
            Label labelJungleRatio = new Label("Jungle Ratio: ");

            TextField textFieldWidth = new TextField("5");
            TextField textFieldHeight = new TextField("5");
            TextField textFieldNumberOfAnimals = new TextField("10");
            TextField textFieldStartEnergy = new TextField("10");
            TextField textFieldMoveEnergy = new TextField("1");
            TextField textFieldPlantEnergy = new TextField("5");
            TextField textFieldJungleRatio = new TextField("0.25");
            Button startButton = new Button("Start");

            startButton.setOnAction(event -> {
                width = Integer.parseInt(textFieldWidth.getText());
                height = Integer.parseInt(textFieldHeight.getText());
                numberOfAnimals = Integer.parseInt(textFieldNumberOfAnimals.getText());
                moveEnergy = Integer.parseInt(textFieldMoveEnergy.getText());
                startEnergy = Integer.parseInt(textFieldStartEnergy.getText());
                plantEnergy = Integer.parseInt(textFieldPlantEnergy.getText());
                jungleRatio = Double.parseDouble(textFieldJungleRatio.getText());

                Grass.plantEnergy = plantEnergy;
                Animal.moveEnergy = moveEnergy;
                Animal.reproductionEnergy = startEnergy / 2;
                AbstractWorldMap.jungeRatio = jungleRatio;

                map = new WrapMap(width, height);

                List<Vector2d> positions = new ArrayList<>();
                for (int i = 0; i < numberOfAnimals; i++) {
                    positions.add(new Vector2d((int) (Math.random() * height), (int) (Math.random() * width)));
                }

                interactiveBox.getChildren().clear();
                interactiveBox.getChildren().add(gridPane);
                engine = new SimulationEngine(map, positions, startEnergy, this);

                engineThread = new Thread(engine);
                engineThread.start();
            });

            interactiveBox = new VBox(labelWidth, textFieldWidth, labelHeight, textFieldHeight, labelNumberOfAnimals, textFieldNumberOfAnimals, labelStartEnergy, textFieldStartEnergy, labelMoveEnergy, textFieldMoveEnergy, labelPlantEnergy, textFieldPlantEnergy, labelJungleRatio, textFieldJungleRatio, startButton);


            Scene scene = new Scene(interactiveBox);
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();

        }catch (IllegalArgumentException ex){
        System.out.println(ex.getMessage());
        System.exit(1);
        }
    }

    public void updateScene(){
        Vector2d[] boundaries = map.getBoundaries();

        int width = boundaries[1].x - boundaries[0].x + 2;
        int height = boundaries[1].y - boundaries[0].y + 2;

        int gridSize = Math.min(maxWidth/ width, maxHeight/height);
        ColumnConstraints columnWidth = new ColumnConstraints(gridSize);
        RowConstraints rowHeight = new RowConstraints(gridSize);
        rowHeight.setValignment(VPos.CENTER);


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
                    GuiElementBox box = new GuiElementBox((map.objectAt(position)), gridSize, gridSize);
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

