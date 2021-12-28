package evolutionSimulation;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractWorldMapTest {
    AbstractWorldMap map = new WorldMap(5, 5);

    Vector2d posCat = new Vector2d(2,2);
    Vector2d posDog = new Vector2d(3,4);


    Vector2d outsideMap1 = new Vector2d(6,2);
    Vector2d outsideMap2 = new Vector2d(3,-1);

    Vector2d onMap1 = new Vector2d(3, 3);
    Vector2d onMap2 = new Vector2d(0, 5);

    Animal cat = new Animal(map, posCat, 100);
    Animal dog = new Animal(map, posDog, 100);


    @Test
    public void objectAtTest(){
        map.place(cat);
        map.place(dog);
        assertEquals(cat, map.animalsAt(posCat).get(0));
        assertEquals(dog, map.animalsAt(posDog).get(0));

//        assertNotEquals(cat, map.objectAt(posDog));
//        assertNotEquals(dog, map.objectAt(posCat));
    }

    @Test
    public void isOccupiedTest(){
        map.place(cat);
        map.place(dog);
        assertTrue(map.isOccupied(posCat));
        assertTrue(map.isOccupied(posDog));
        assertFalse(map.isOccupied(outsideMap1));
        assertFalse(map.isOccupied(outsideMap2));

        assertFalse(map.isOccupied(onMap1));
        assertFalse(map.isOccupied(onMap2));
    }

    @Test
    public void placeAnimal(){

        try{
            map.place(new Animal(map, outsideMap1, 100));
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), outsideMap1 + " is not on map");
        }
//        try{
//            map.place(new Animal(map, posCat, 100));
//        } catch (IllegalArgumentException ex2) {
//            assertEquals(ex2.getMessage(), posCat + " is already occupied");
//        }

    }

    @Test
    public void animalSortTest(){
        Animal duck = new Animal(map, posCat, 50);
        Animal cow = new Animal(map, posCat, 200);
        map.place(cat);
        map.place(duck);
        map.place(cow);


        assertEquals(map.animals.get(posCat).get(0), cat);
        assertEquals(map.animals.get(posCat).get(1), duck);
        assertEquals(map.animals.get(posCat).get(2), cow);

        map.animals.get(posCat).sort(compareEnergy.reversed());

        assertEquals(map.animals.get(posCat).get(0), cow);
        assertEquals(map.animals.get(posCat).get(1), cat);
        assertEquals(map.animals.get(posCat).get(2), duck);
    }

    Comparator<Animal> compareEnergy = Comparator.comparingInt(o -> o.energy);

}
