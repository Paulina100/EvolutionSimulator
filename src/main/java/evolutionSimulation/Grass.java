package evolutionSimulation;    // przyjęło się tworzyć nazwy pakietów w stylu odwrotnych nazw domenowych

public class Grass implements IMapElement{
    private final Vector2d position;
    public static int plantEnergy;  // tak po prostu modyfikowalne?

    public Grass(Vector2d position){
        this.position = position;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public String toString(){
        return "*";
    }

    public String toImagePath() {   // lepiej to przenieść do GUI
        return "src/main/resources/grass.png";
    }
}