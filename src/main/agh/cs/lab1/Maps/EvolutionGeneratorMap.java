package agh.cs.lab1.Maps;

import agh.cs.lab1.Enums.MapDirection;
import agh.cs.lab1.Utilities.Genes;
import agh.cs.lab1.MapElements.Animal;
import agh.cs.lab1.MapElements.Grass;
import agh.cs.lab1.Utilities.Vector2d;


import java.util.*;

public class EvolutionGeneratorMap implements IStateChangeObserver {


    private final Vector2d upperRight;
    private final Vector2d lowerLeft;

    private final Vector2d jungleUpperRight;
    private final Vector2d jungleLowerLeft;

    private final Map<Vector2d, LinkedList<Animal>> animalMap;
    private final Map<Vector2d, Grass> grassMap;
    private final Map<Genes, LinkedList<Animal>> genotypeRanking;
    private LinkedList<Animal> deadAnimals;

    private long grassSteppeAmount;
    private long grassJungleAmount;



    public EvolutionGeneratorMap(int width, int height, float jungleRatio) {

        upperRight = new Vector2d(width, height);
        lowerLeft = new Vector2d(0, 0);

        int xRatio = (int)Math.ceil((width * jungleRatio)/2);
        int yRatio = (int)Math.ceil((height * jungleRatio)/2);

        jungleLowerLeft = new Vector2d((width/2)-1-xRatio, (height/2)-1-yRatio);
        jungleUpperRight = new Vector2d((width/2)-1+xRatio, (height/2)-1+yRatio);

        animalMap = new HashMap<>();
        grassMap = new HashMap<>();

        grassJungleAmount = 0;
        grassSteppeAmount = 0;

        deadAnimals = new LinkedList<>();

        genotypeRanking = new HashMap<>();

    }

    // GETTERS

    public Vector2d getUpperRightCorner() {
        return upperRight;
    }

    public Vector2d getLowerLeftCorner() {
        return lowerLeft;
    }

    public Vector2d getJungleUpperRight() {
        return jungleUpperRight;
    }

    public Vector2d getJungleLowerLeft() {
        return jungleLowerLeft;
    }

    public LinkedList<Animal> listOfAnimalsAt(Vector2d position) {
        return animalMap.get(position);
    }

    public boolean isGrassAt(Vector2d position) {
        return grassMap.containsKey(position);
    }

    public LinkedList<Genes> getLeadingGenes() {
        LinkedList<Genes> result = new LinkedList<>();
        int popularity = 0;
        for(Genes gene : genotypeRanking.keySet()) {
            int geneCount = genotypeRanking.get(gene).size();
            if(geneCount > popularity) {
                result.clear();
                result.add(gene);
                popularity = geneCount;
            }
            else if(geneCount == popularity) {
                result.add(gene);
            }
        }
        return result;
    }

    public LinkedList<Animal> getDominatingGenotypeAnimals() {
        LinkedList<Animal> result = new LinkedList<>();
        LinkedList<Genes> dominatingGenes = getLeadingGenes();
        for(Genes gene : dominatingGenes) {
            result.addAll(genotypeRanking.get(gene));
        }
        return result;
    }

    public long getGrassAmount() {
        return grassJungleAmount + grassSteppeAmount;
    }

    public Animal getStrongestAnimalAt(Vector2d position) {
        if(!isOccupied(position)) return null;
        Animal result = null;
        for(Animal animal : listOfAnimalsAt(position)) {
            if((result == null)||(result.getEnergy() < animal.getEnergy())) result = animal;
        }

        return result;
    }

    public LinkedList<Animal> getListAnimal() {

        LinkedList<Animal> result = new LinkedList<>();
        for(LinkedList<Animal> list : animalMap.values()) {
            result.addAll(list);
        }
        return result;
    }

    public LinkedList<Grass> getListGrass() {

        return new LinkedList<>(grassMap.values());
    }

    public HashMap<Genes, Integer> getSumOfGeneOccur() {
        HashMap<Genes,Integer> result = new HashMap<>();
        for(Genes gene : genotypeRanking.keySet()) {
            result.put(gene, genotypeRanking.get(gene).size());
        }
        return result;
    }

    // RANDOM POSITION/VECTOR GETTERS

    public Vector2d randomFreePosition(boolean isJungle) {  // czy to musi być public?

        Random generator = new Random();
        int limit = 10;
        if(isJungle) {
           return findFreePosition(limit, jungleLowerLeft, jungleUpperRight);
        }

        else {
           switch (generator.nextInt(4)) {  // rozkład prawdopodobieństwa
               case 0:
                   return findFreePosition(limit, lowerLeft, new Vector2d(jungleLowerLeft.x, jungleUpperRight.y));
               case 1:
                   return findFreePosition(limit, new Vector2d(0, jungleUpperRight.y), new Vector2d(jungleUpperRight.x, upperRight.y));
               case 2:
                   return findFreePosition(limit, new Vector2d(jungleUpperRight.x, jungleLowerLeft.y), upperRight);
               case 3:
                   return findFreePosition(limit, new Vector2d(jungleLowerLeft.x, 0), new Vector2d(upperRight.x, jungleLowerLeft.y));
           }

        }
        return null;
    }

    public Vector2d findFreePosition(int limit, Vector2d lowerLeftCorner, Vector2d upperRightCorner) {
        Vector2d result = randomVectorInRange(lowerLeftCorner, upperRightCorner);
        while((limit>0)&&((result == null)||(!isEmpty(result)))) {
            result = randomVectorInRange(lowerLeftCorner, upperRightCorner);
            limit--;
        }
        if((limit > 0)||((!(result == null))&&(isEmpty(result)))) {
            return result;
        }
        else return null;
    }

    public Vector2d randomVectorInRange(Vector2d lowerLeft, Vector2d upperRight) {  // czy to musi być publiczne?

        if(lowerLeft.equals(upperRight)) return lowerLeft;

        if((upperRight.x - lowerLeft.x == 0)||(upperRight.y- lowerLeft.y == 0)) return null;    // nie czytelniej jest: (upperRight.y == lowerLeft.y) ?

        if(!lowerLeft.precedes(upperRight)) return null;

        Random generator = new Random();
        return new Vector2d(generator.nextInt(upperRight.x - lowerLeft.x) + lowerLeft.x,
                generator.nextInt(upperRight.y - lowerLeft.y) + lowerLeft.y);
    }


    public Vector2d getRandomFreePositionAround(Vector2d position) {
        ArrayList<Vector2d> positionsAround = new ArrayList<>();
        ArrayList<Vector2d> forDraw = new ArrayList<>();

        for(MapDirection direction : MapDirection.values()) {
            positionsAround.add(calculateNewPosition(direction, position));
            if(!isOccupied(positionsAround.get(positionsAround.size() - 1)))
                forDraw.add(positionsAround.get(positionsAround.size() - 1));
        }

        Random generator = new Random();

        if(forDraw.isEmpty()) return positionsAround.get(generator.nextInt(positionsAround.size()));

        return forDraw.get(generator.nextInt(forDraw.size()));

    }

    // FUNCTIONS OF MAP/POSITION STATE

    public boolean isOccupied(Vector2d position) {
        List<Animal> animals = animalMap.get(position);
        return (animals != null) && (animals.size() != 0);
    }

    public boolean isEmpty(Vector2d position) {
        return((!isOccupied(position))&&(!isGrassAt(position)));
    }

    public boolean isInJungle(Vector2d position) {
        return (position.follows(jungleLowerLeft))&&(position.precedes(jungleUpperRight));
    }


    // ADDING/REMOVING ELEMENTS

    public void place(Animal animal) {

        Vector2d position = animal.getPosition();

        animalMap.computeIfAbsent(position, k -> new LinkedList<>());

        animalMap.get(position).add(animal);

        animal.addObserver(this);


        genotypeRanking.computeIfAbsent(animal.getGenotype(), k -> new LinkedList<>());
        genotypeRanking.get(animal.getGenotype()).add(animal);

    }

    public void removeAnimal(Animal animal) {

        Vector2d position = animal.getPosition();

        if(animalMap.get(position) == null) throw new IllegalArgumentException("There is no animal" +
                                                                " to remove at position: " + position);
        animalMap.get(position).remove(animal);

        if(animalMap.get(position).size() == 0) animalMap.remove(position);

        Genes genotype = animal.getGenotype();
        genotypeRanking.get(genotype).remove(animal);

        if(genotypeRanking.get(genotype).size() == 0) {
            genotypeRanking.remove(genotype);
        }
        deadAnimals = new LinkedList<>();

    }

    public void removeDeadAnimals() {
        for(Animal deadAnimal: deadAnimals) {
            removeAnimal(deadAnimal);
        }
    }

    public void addGrass(Grass grass) {

        Vector2d position = grass.getPosition();

        if((isGrassAt(position))||isOccupied(position)) {

            throw new IllegalArgumentException("You have to check if there is no animal or other grass before adding" +
                    "new grass. Position: " + position);
        }

        grassMap.put(position, grass);

        if(isInJungle(position)) grassJungleAmount++;
        else grassSteppeAmount++;

    }

    public void removeGrassFrom(Vector2d grassPosition){

        if(grassMap.get(grassPosition) == null) throw new IllegalArgumentException("There is no grass at: " + grassPosition);
        grassMap.remove(grassPosition);

        if(!isOccupied(grassPosition)) {
            if (isInJungle(grassPosition)) grassJungleAmount--;
            else grassSteppeAmount--;

        }
    }

    public void grassGetsEatenBy(Animal animal) {

        Vector2d fieldWhereGrassIs = animal.getPosition();
        animal.eats(grassMap.get(fieldWhereGrassIs));
        removeGrassFrom(fieldWhereGrassIs);

    }

    // STATUS CHANGE ANIMAL FUNCTIONS

    @Override
    public void stateChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {


        if(!animal.isAlive()) {
            deadAnimals.add(animal);
        }
        positionChanged(oldPosition, newPosition, animal);  // i assume that animal dies shortly after getting to a new position
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {

        animalMap.get(oldPosition).remove(animal);
        if(animalMap.get(oldPosition).size() == 0) {
            animalMap.remove(oldPosition);
        }

        animalMap.computeIfAbsent(newPosition, k -> new LinkedList<>());
        animalMap.get(newPosition).add(animal);

    }

    // MAP UTIL

    public Vector2d calculateNewPosition(MapDirection orientation, Vector2d oldPosition) {

         Vector2d wantedPosition = oldPosition.add(orientation.toUnitVector());

        if(!wantedPosition.precedes(upperRight)) {
            if(wantedPosition.x > upperRight.x) wantedPosition = new Vector2d(lowerLeft.x, wantedPosition.y);
            if(wantedPosition.y > upperRight.y) wantedPosition = new Vector2d(wantedPosition.x, lowerLeft.y);
        }
        if(!wantedPosition.follows(getLowerLeftCorner())) {
            if(wantedPosition.x < lowerLeft.x) wantedPosition = new Vector2d(upperRight.x, wantedPosition.y);
            if(wantedPosition.y < lowerLeft.y) wantedPosition = new Vector2d(wantedPosition.x, upperRight.y);
        }

        return wantedPosition;

    }



}
