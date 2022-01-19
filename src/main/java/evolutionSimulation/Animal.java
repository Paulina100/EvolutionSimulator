package evolutionSimulation;

public class Animal implements IMapElement{
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    public int energy;  // public?
    public static int moveEnergy;
    public static int reproductionEnergy;
    private final Genotype genotype = new Genotype();   // a co z krzyżowaniem?
    private final AbstractWorldMap map;

    public Animal (AbstractWorldMap map, Vector2d initialPosition, int initialEnergy){
        this.map = map;
        this.position = initialPosition;
        this.energy = initialEnergy;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public String toString(){
        return switch (this.orientation){
            case NORTH -> "^";
            case NORTHEAST -> "^>";
            case EAST -> ">";
            case SOUTHEAST -> ">v";
            case SOUTH -> "v" ;
            case SOUTHWEST -> "<v" ;
            case WEST -> "<";
            case NORTHWEST -> "<^";
        };
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }


    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){   // newPosition nie jest potrzebne + czy ta metoda coś wnosi?
//        map.positionChanged(oldPosition, newPosition);
        map.positionChanged(this, oldPosition);
    }

    public void randomMove(){
        move(genotype.getRandomGen());
    }

    public void move(int direction){    // a jak przekażę -20?
        energy -= moveEnergy;
        switch (direction){
            case 0 -> {
                Vector2d newPosition = this.position.add(this.orientation.toUnitVector());
                newPosition = map.checkNewPosition(newPosition);
                if (newPosition != null) {
                    Vector2d oldPosition = this.position;
                    this.position = newPosition;
                    positionChanged(oldPosition, newPosition);
                }
            }
            case 4 -> { // za miesiąc będzie Pani pamiętała czemu 4?
                Vector2d newPosition = this.position.add(this.orientation.toUnitVector().opposite());
                newPosition = map.checkNewPosition(newPosition);
                if (newPosition != null) {
                    Vector2d oldPosition = this.position;
                    this.position = newPosition;
                    positionChanged(oldPosition, newPosition);
                }
            }
            default -> this.orientation = this.orientation.turn(direction);
        }
    }

    public String toImagePath() {
        return switch (this.orientation){
            case NORTH -> "src/main/resources/up.png";
            case NORTHEAST -> "src/main/resources/upright.png";
            case EAST -> "src/main/resources/right.png";
            case SOUTHEAST -> "src/main/resources/downright.png";
            case SOUTH -> "src/main/resources/down.png";
            case SOUTHWEST -> "src/main/resources/downleft.png";
            case WEST -> "src/main/resources/left.png";
            case NORTHWEST -> "src/main/resources/upleft.png";
        };
    }

}
