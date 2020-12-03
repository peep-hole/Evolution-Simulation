package agh.cs.lab1;

import java.util.*;

public class MapBoundary implements IPositionChangeObserver{

    private final List<IMapElement> alongX;
    private final List<IMapElement> alongY;

    private boolean updated;

    public MapBoundary() {
        alongX = new ArrayList<>();
        alongY = new ArrayList<>();
        updated = true;
    }

    public void addElement (IMapElement element) {
        alongX.add(element);
        alongY.add(element);
        if (element instanceof Animal) {
            ((Animal) element).addObserver(this);
        }
        updated = false;
    }


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        updated = false;
    }


    /*

    mysle ze sortowanie elementow tylko wtedy kiedy chcemy odczytac granice jest
     bardziej efektywne niz dodawanie elementow w posortowanej kolejnosci,
     w szczególności dla dużej ilości elementów które zmieniają swoją pozycję symultanicznie

     */
    private void update() {
        alongX.sort(Comparator.comparingInt(o -> o.getPosition().x));
        alongY.sort(Comparator.comparingInt(o -> o.getPosition().y));
        updated = true;
    }

    public Vector2d upperRight() {
        if(alongX.size() == 0) throw new IllegalStateException("Map is empty!");    // sugeruję raczej zwracać wektor (0,0) - pusta mapa właściwie nie jest błędna, choć nudna
        if(!updated) update();
        return new Vector2d(alongX.get(alongX.size()-1).getPosition().x,alongY.get(alongX.size()-1).getPosition().y);
    }
    public Vector2d lowerLeft() {
        if(alongX.size() == 0) throw new IllegalStateException("Map is empty!");
        if(!updated) update();
        return new Vector2d(alongX.get(0).getPosition().x,alongY.get(0).getPosition().y);
    }
}
