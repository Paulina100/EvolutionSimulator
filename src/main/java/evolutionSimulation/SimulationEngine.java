package evolutionSimulation;

import evolutionSimulation.gui.App;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class SimulationEngine implements Runnable {
//    private MoveDirection[] directions;
    private final AbstractWorldMap map;
    private App app;
    private final List<Animal> animals = new ArrayList<>();

    public SimulationEngine(AbstractWorldMap map, Vector2d[] initialPositions, int startEnergy, App app){
        this.map = map;
        this.app = app;
        for (Vector2d position : initialPositions){
            Animal animal = new Animal(map, position, startEnergy);
            map.place(animal);
//                animal.addObserver(app);
            animals.add(animal);
        }
    }

//    public SimulationEngine(IWorldMap map, Vector2d[] initialPositions, App app){
//        this.map = map;
//        for (Vector2d position : initialPositions){
//            Animal animal = new Animal(map, position);
//            if (map.place(animal)){
//              animal.addObserver(app);
//                animals.add(animal);
//            }
//        }
//    }

//    public void setDirections(MoveDirection[] directions){
//        this.directions = directions;
//    }


//    public void run(){
//        int moveDelay = 300;
//        int i = 0;
//        while (i < directions.length) {
//            for (Animal animal : animals) {
//                animal.move(directions[i]);
//
//                try {
//                    Thread.sleep(moveDelay);
//                } catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//                i++;
//                if (i >= directions.length) break;
//            }
//        }
//    }

    public void run(){
        int moveDelay = 300;
//        int i = 0;
        while (true) {
            for (Animal animal : animals) animal.randomMove();
            map.growGrass();
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            app.newEra();
//            i++;
        }
    }
}