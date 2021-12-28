package evolutionSimulation;

import evolutionSimulation.gui.App;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static java.lang.System.out;

public class SimulationEngine implements Runnable {
//    private MoveDirection[] directions;
    private final AbstractWorldMap map;
    private App app;
    private final List<Animal> animals = new ArrayList<>();

    public SimulationEngine(AbstractWorldMap map, List<Vector2d> initialPositions, int startEnergy, App app){
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

    private void removeDeadAnimals(){
        List<Animal> deadAnimals = new ArrayList<>();
        for (Animal animal : animals){
            if (animal.energy <= 0){
                deadAnimals.add(animal);
                map.removeAnimal(animal, animal.getPosition());
            }
        }
        for (Animal animal : deadAnimals) {
            animals.remove(animal);
        }
    }

    public void run(){
        int moveDelay = 300;
//        int i = 0;
        while (true) {
            removeDeadAnimals();
            for (Animal animal : animals) animal.randomMove();
            map.eatGrass();

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