package agh.cs.lab1.Maps;   // czy ten interfejs na pewno tu pasuje?

import agh.cs.lab1.MapElements.Animal;
import agh.cs.lab1.Utilities.Vector2d;

public interface IStateChangeObserver {

    void stateChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);   // czy potrzebujemy nową pozycję, skoro dostajemy całe zwierzę?
}
