package evolutionSimulation;

import evolutionSimulation.gui.App;

import java.util.ArrayList;
import java.util.List;


public class SimulationEngine implements Runnable {
    private final AbstractWorldMap map;
    private final App app;
    private final List<Animal> animals = new ArrayList<>();

    public SimulationEngine(AbstractWorldMap map, List<Vector2d> initialPositions, int startEnergy, App app){
        this.map = map;
        this.app = app;

        for (Vector2d position : initialPositions){
            Animal animal = new Animal(map, position, startEnergy);
            map.place(animal);
            animals.add(animal);
        }
    }

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

        while (animals.size() > 0) {
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
        }
    }
}