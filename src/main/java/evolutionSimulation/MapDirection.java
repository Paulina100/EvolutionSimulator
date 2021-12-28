package evolutionSimulation;

public enum MapDirection{
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public String toString(){
        return switch (this) {
            case NORTH -> "Północ";
            case NORTHEAST -> "Północnywschód";
            case EAST -> "Wschód";
            case SOUTHEAST -> "Południowywschód";
            case SOUTH -> "Południe";
            case SOUTHWEST -> "Południowyzachód";
            case WEST -> "Zachód";
            case NORTHWEST -> "Północnyzachód";
        };
    }

    private int toInt(){
        return switch (this) {
            case NORTH -> 0;
            case NORTHEAST -> 1;
            case EAST -> 2;
            case SOUTHEAST -> 3;
            case SOUTH -> 4;
            case SOUTHWEST -> 5;
            case WEST -> 6;
            case NORTHWEST -> 7;
        };
    }

    public MapDirection turn(int angle){
        int newDirection = this.toInt() + angle;
        newDirection %= 8;
        return switch (newDirection) {
            //case 0 -> NORTH;
            case 1 -> NORTHEAST;
            case 2 -> EAST;
            case 3 -> SOUTHEAST;
            case 4 -> SOUTH;
            case 5 -> SOUTHWEST;
            case 6 -> WEST;
            case 7 -> NORTHWEST;
            default -> NORTH;
        };
    }

    public Vector2d toUnitVector(){
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }
}