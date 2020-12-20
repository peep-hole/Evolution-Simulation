package agh.cs.lab1.Maps;

import agh.cs.lab1.MapElements.Animal;
import agh.cs.lab1.Utilities.Vector2d;

public interface IStateChangeObserver {

    void stateChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);
}
