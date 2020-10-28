package agh.cs.lab1;

public class Animal {

    private MapDirection orientation;
    private Vector2d position;

    public Animal() {
        orientation = MapDirection.NORTH;
        position = new Vector2d(2, 2);
    }

    public String toString() {
        return "Position: " + position + ",  Orientation: " + orientation;
    }

    public void move(MoveDirection direction) {
        switch (direction) {

            case RIGHT:
                orientation = orientation.next();
                break;

            case LEFT:
                orientation = orientation.previous();
                break;

            case FORWARD:
                position = position.add(orientation.toUnitVector());

                if (position.x < 0 || position.x > 4 || position.y < 0 || position.y > 4) { // po co Pan pisał Vector2d.precedes?

                    position = position.add(orientation.toUnitVector().opposite()); // nie lepiej zapisać nową pozycję w zmiennej tymczasowej, a pole zmodyfikować dopiero jak wiemy, że jest poprawna?
                }
                break;

            case BACKWARD:
                position = position.add(orientation.toUnitVector().opposite());

                if (position.x < 0 || position.x > 4 || position.y < 0 || position.y > 4) {
                    position = position.add(orientation.toUnitVector());
                }
                break;

        }
    }

}
