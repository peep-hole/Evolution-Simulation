package agh.cs.lab1.MapElements;

import agh.cs.lab1.Enums.EnergyStatus;
import agh.cs.lab1.Enums.MapDirection;
import agh.cs.lab1.Utilities.Genes;
import agh.cs.lab1.Maps.EvolutionGeneratorMap;
import agh.cs.lab1.Maps.IStateChangeObserver;
import agh.cs.lab1.Utilities.Vector2d;

import java.util.LinkedList;
import java.util.List;

public class Animal implements IMapElement {

    private MapDirection orientation;
    private Vector2d position;
    private final EvolutionGeneratorMap map;
    private int energy;
    private final Genes genotype;
    private final int startEnergy;

    private final int ID;

    private final List<IStateChangeObserver> observers;

    private int childCount;

    private final int birthEpoch;

    public Animal(EvolutionGeneratorMap map, Vector2d position, MapDirection direction, Genes genes, int ID, int startEnergy, int birthEpoch) {

        observers = new LinkedList<>();
        orientation = direction;
        this.map = map;
        this.position = position;
        genotype = genes;
        this.ID = ID;
        energy = startEnergy;
        childCount = 0;
        this.birthEpoch = birthEpoch;
        this.startEnergy = startEnergy;
        this.map.place(this);


    }


    public void addObserver(IStateChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IStateChangeObserver observer) {
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for(IStateChangeObserver observer : observers) {
            observer.stateChanged(oldPosition, newPosition,this);
        }
    }

    public Vector2d getPosition() {
        return position;
    }

    public String toString(){
        return String.valueOf(ID);
    }

    public int getEnergy() {
        return energy;
    }

    public void eats(Grass grass) {
        if(grass == null) throw new IllegalArgumentException("There is no grass to eat at position: " + position);
        energy += grass.getNutritionalValue();
    }

    public void rotateAndRun(int movingCost) {

        int angle = genotype.getRandomGene();
        if((angle > 7)||(angle < 0)) throw new IllegalArgumentException("Rotation angle cannot be: " + angle);

        for(int i = 0; i< angle; i++) {
            orientation = orientation.next();
        }

        Vector2d oldPosition = position;
        position = map.calculateNewPosition(orientation, position);
        energy -= movingCost;
        positionChanged(oldPosition, position);
    }

    public Animal copulateWith(Animal partner, double reproductionCost,
                               MapDirection orientation, int newID, int actualEpoch) {

        int cumulatedEnergy = (int)(energy * reproductionCost) + (int)(partner.energy * reproductionCost);

        this.energy -= (int)(reproductionCost * energy);
        partner.energy -= (int)(reproductionCost * partner.energy);

        this.childCount ++;
        partner.childCount ++;

        return new Animal(map, map.getRandomFreePositionAround(position), orientation,
                Genes.createInheritedGenotype(genotype, partner.getGenotype()), newID, cumulatedEnergy, actualEpoch);

    }

    public int getChildCount() {
        return childCount;
    }


    public Genes getGenotype() {
        return genotype;
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public int getID() {
        return ID;
    }

    public int getBirthEpoch() {
        return birthEpoch;
    }

    public EnergyStatus getEnergyStatus() {
        if(10*energy > 6*startEnergy) {
            return EnergyStatus.HIGH;
        }
        else if(10*energy > 3*startEnergy) {
            return EnergyStatus.MEDIUM;
        }
        else if(energy > 0) {
            return EnergyStatus.LOW;
        }
        else return EnergyStatus.DEAD;

    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if(this == other)
            return true;
        if (!(other instanceof Animal))
            return false;
        Animal that = (Animal) other;
        return this.getID() == that.getID();
    }

    @Override
    public int hashCode() {
        return (new Integer(getID())).hashCode();
    }

}
