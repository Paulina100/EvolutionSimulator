package evolutionSimulation.gui;


import evolutionSimulation.Animal;
import evolutionSimulation.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    public VBox vbox;

    public GuiElementBox(IMapElement element, int width, int height){
        try {
            Image image;
            Label label;

            image = new Image(new FileInputStream(element.toImagePath()));

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(width * 0.75);
            imageView.setFitHeight(height * 0.75);
            if (element instanceof Animal){
                label = new Label("" + ((Animal) element).energy);
            }
            else{
                label = new Label("Trawa");
            }

            vbox = new VBox(imageView, label);
            vbox.setAlignment(Pos.CENTER);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}