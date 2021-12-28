package evolutionSimulation;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {
    @Test
    public void toStringTest(){
        assertEquals(MapDirection.NORTH.toString(), "Północ");
        assertEquals(MapDirection.NORTHEAST.toString(), "Północnywschód");
        assertEquals(MapDirection.EAST.toString(), "Wschód");
        assertEquals(MapDirection.SOUTHEAST.toString(), "Południowywschód");
        assertEquals(MapDirection.SOUTH.toString(), "Południe");
        assertEquals(MapDirection.SOUTHWEST.toString(), "Południowyzachód");
        assertEquals(MapDirection.WEST.toString(), "Zachód");
        assertEquals(MapDirection.NORTHWEST.toString(), "Północnyzachód");
    }

    @Test
    public void turnTest(){
        assertEquals(MapDirection.NORTH.turn(0), MapDirection.NORTH);
        assertEquals(MapDirection.NORTH.turn(1), MapDirection.NORTHEAST);
        assertEquals(MapDirection.NORTH.turn(2), MapDirection.EAST);
        assertEquals(MapDirection.NORTH.turn(3), MapDirection.SOUTHEAST);
        assertEquals(MapDirection.NORTH.turn(4), MapDirection.SOUTH);
        assertEquals(MapDirection.NORTH.turn(5), MapDirection.SOUTHWEST);
        assertEquals(MapDirection.NORTH.turn(6), MapDirection.WEST);
        assertEquals(MapDirection.NORTH.turn(7), MapDirection.NORTHWEST);

        assertEquals(MapDirection.NORTHEAST.turn(7), MapDirection.NORTH);
        assertEquals(MapDirection.EAST.turn(6), MapDirection.NORTH);
        assertEquals(MapDirection.SOUTHEAST.turn(5), MapDirection.NORTH);
        assertEquals(MapDirection.SOUTH.turn(4), MapDirection.NORTH);
        assertEquals(MapDirection.SOUTHWEST.turn(3), MapDirection.NORTH);
        assertEquals(MapDirection.WEST.turn(2), MapDirection.NORTH);
        assertEquals(MapDirection.NORTHWEST.turn(1), MapDirection.NORTH);
    }


    @Test
    public void toUniVectorTest(){
        assertEquals(MapDirection.NORTH.toUnitVector(), new Vector2d(0, 1));
        assertEquals(MapDirection.NORTHEAST.toUnitVector(), new Vector2d(1, 1));
        assertEquals(MapDirection.EAST.toUnitVector(), new Vector2d(1, 0));
        assertEquals(MapDirection.SOUTHEAST.toUnitVector(), new Vector2d(1, -1));
        assertEquals(MapDirection.SOUTH.toUnitVector(), new Vector2d(0, -1));
        assertEquals(MapDirection.SOUTHWEST.toUnitVector(), new Vector2d(-1, -1));
        assertEquals(MapDirection.WEST.toUnitVector(), new Vector2d(-1, 0));
        assertEquals(MapDirection.NORTHWEST.toUnitVector(), new Vector2d(-1, 1));
    }

}
