package evolutionSimulation;

import java.util.*;

public abstract class AbstractWorldMap {
    protected Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected Map<Vector2d, Grass> grassMap = new HashMap<>();
    protected final static Vector2d lowerLeft = new Vector2d(0, 0);
    protected Vector2d upperRight;

    public AbstractWorldMap(int width, int height){
        this.upperRight = new Vector2d(width, height);
    }

    public IMapElement objectAt(Vector2d position){
        if (animalsAt(position) != null) return animalsAt(position).get(0);
        return grassAt(position);
    }

    public List<Animal> animalsAt(Vector2d position) {
        if (animals.get(position) == null) return null;
        return animals.get(position);
    }

    public Grass grassAt(Vector2d position){
        return grassMap.get(position);
    }

    public boolean isOccupied(Vector2d position) {
        return animalsAt(position) != null || grassAt(position) != null;
    }

    public void growGrass(){
        List<Vector2d> emptySpace = new ArrayList<>();
        for (int i = lowerLeft.x; i <= upperRight.x; i++){
            for (int j = lowerLeft.y; j <= upperRight.y; j++){
                Vector2d position = new Vector2d(i, j);
                if (!isOccupied(position)) emptySpace.add(position);
            }
        }

        if (emptySpace.size() != 0){
            Vector2d position = emptySpace.get((int) (Math.random()*emptySpace.size()));
            grassMap.put(position, new Grass(position));
        }
    }

    public void eatGrass(){
        List<Vector2d> eatenGrass = new ArrayList<>();
        for (Vector2d position: grassMap.keySet()) {
            if (animalsAt(position) != null){
                // tu jakos sortowac

//                Collections.sort(animals);
                Animal animal = animals.get(position).get(0);
                animal.energy += Grass.plantEnergy;
                eatenGrass.add(position);
            }
        }
        for (Vector2d position: eatenGrass) {
            grassMap.remove(position);
        }
    }

    public void place(Animal animal) {
        Vector2d position = animal.getPosition();

        if (animals.get(position) == null){
            List<Animal> animals1 = new ArrayList<>();
            animals1.add(animal);
            animals.put(position, animals1);
            return;
        }

        if (!isOnMap(animal.getPosition())) {
            throw new IllegalArgumentException(animal.getPosition() + " is not on map");
        }

        animals.get(position).add(animal);
    }

    public abstract Vector2d checkNewPosition(Vector2d newPosition);

    protected boolean canMoveTo(Vector2d position) {
        return isOnMap(position);
//        return !(objectAt(position) instanceof Animal);
    }

    private boolean isOnMap(Vector2d position){
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }

    public void removeAnimal(Animal animal, Vector2d position){
        animals.get(position).remove(animal);
        if (animals.get(position).isEmpty()) animals.remove(position);
    }


    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition ){
        removeAnimal(animal, oldPosition);

        place(animal);
    }

    public Vector2d[] getBoundaries() {
        return new Vector2d[]{lowerLeft, upperRight};
    }
}
