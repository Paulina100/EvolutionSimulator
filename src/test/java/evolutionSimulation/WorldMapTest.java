package evolutionSimulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WorldMapTest {
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
    public void checkNewPositionTest(){
        map.place(cat);
        map.place(dog);

        assertEquals(map.checkNewPosition(onMap1), onMap1);
        assertEquals(map.checkNewPosition(onMap2), onMap2);

//        assertNull(map.checkNewPosition(posCat));
//        assertNull(map.checkNewPosition(posDog));

        assertNull(map.checkNewPosition(outsideMap1));
        assertNull(map.checkNewPosition(outsideMap2));

    }
}
