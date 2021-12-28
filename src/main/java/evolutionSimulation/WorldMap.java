package evolutionSimulation;

public class WorldMap extends AbstractWorldMap {
    public WorldMap(int width, int height){
        super(width, height);
    }
    public Vector2d checkNewPosition (Vector2d newPosition){
        if (canMoveTo(newPosition)) return newPosition;
        return null;
    }
}
