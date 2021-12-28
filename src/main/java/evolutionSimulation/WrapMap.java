package evolutionSimulation;

public class WrapMap extends AbstractWorldMap {
    public WrapMap(int width, int height){
        super(width, height);
    }

    public Vector2d checkNewPosition (Vector2d newPosition){
        if (canMoveTo(newPosition)) return newPosition;

        // to do usuniecia
//        if (objectsAt(newPosition) instanceof Animal) return null;

        int newX = newPosition.x;
        int newY = newPosition.y;

        if (newPosition.x < lowerLeft.x) newX = upperRight.x;
        if (newPosition.x > upperRight.x) newX = lowerLeft.x;
        if (newPosition.y < lowerLeft.y) newY = upperRight.y;
        if (newPosition.y > upperRight.y) newY = lowerLeft.y;

        return new Vector2d(newX, newY);
    }
}
