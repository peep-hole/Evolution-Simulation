package agh.cs.lab1.Simulation;

import agh.cs.lab1.Enums.MapDirection;
import agh.cs.lab1.Utilities.Genes;
import agh.cs.lab1.MapElements.Animal;
import agh.cs.lab1.MapElements.Grass;
import agh.cs.lab1.Maps.EvolutionGeneratorMap;
import agh.cs.lab1.Maps.IStateChangeObserver;
import agh.cs.lab1.Utilities.Vector2d;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationEngine implements IStateChangeObserver {

    private final EvolutionGeneratorMap map;

    private final Map<Integer, Animal> animals;

    private Set<Vector2d> fieldsForSimulation;

    private final AtomicInteger index;

    private final int grassEatingProfit;
    private final int moveCost;
    private final int minimalEnergyForReproduction;
    private static final double reproductionCost = 0.25;

    private final Statistics stats;

    private int amountOfLivingAnimals;
    private int sumOfEnergy;
    private int sumOfLivingAnimalsChildren;
    private int deadAnimalsCount;
    private int totalLifeLengthForDead;

    private Animal followedAnimal;
    private HashMap<Integer, Animal> followedFamily;
    private int followedEpochsLeft;
    private Integer followedDeathEpoch;

    private LinkedList<Animal> deadAnimals;

    private AtomicInteger epochNumber;

    public SimulationEngine(int mapHeight, int mapWidth, float jungleRatio, int amountOfAnimalsOnStart, int moveCost,
                            int grassEatingProfit, int startEnergy) {

        map = new EvolutionGeneratorMap(mapWidth, mapHeight, jungleRatio);
        animals = new HashMap<>();
        index = new AtomicInteger(0);

        amountOfLivingAnimals = amountOfAnimalsOnStart;

        for(int i = 0; i < amountOfAnimalsOnStart; i++) {
            int id = index.get();

            animals.put(index.get(), new Animal(map, map.randomVectorInRange(map.getLowerLeftCorner(), map.getUpperRightCorner()),
                    randomDirection(), Genes.createRandomGenotype(), index.getAndIncrement(), startEnergy, 0));

            animals.get(id).addObserver(this);
        }

        this.grassEatingProfit = grassEatingProfit;
        this.moveCost = moveCost;

        minimalEnergyForReproduction = (int)(startEnergy/2);
        deadAnimalsCount = 0;

        stats = new Statistics();

        followedEpochsLeft = 0;

        epochNumber = new AtomicInteger(0);

        deadAnimals = new LinkedList<>();

        grassGrows();
    }


    public void followAnimalFor(int numberOfEpochs, int id) {
        followedEpochsLeft = numberOfEpochs;
        followedAnimal = animals.get(id);
        followedFamily = new HashMap<>();
        followedDeathEpoch = null;
        followedFamily.put(id, followedAnimal);

    }

    public void runDay() {

        System.out.println("---------Day: " + epochNumber.get() + "---------");

        epochNumber.incrementAndGet();

        totalLifeLengthForDead = 0;
        sumOfEnergy = 0;
        fieldsForSimulation = new HashSet<>();

        removeDeadAnimals();
        deadAnimals = new LinkedList<>();

        for(Animal animal : animals.values()) {
            animal.rotateAndRun(moveCost);
        }

        for(Vector2d field : fieldsForSimulation) {
            simulateField(field);
        }

        grassGrows();

        saveStatistics();

        if(followedEpochsLeft > 0) {
            if((!followedAnimal.isAlive())&&(followedDeathEpoch == null)) followedDeathEpoch = epochNumber.get();
            followedEpochsLeft--;
        }

        // check if all animals are dead

    }

    @Override
    public void stateChanged(Vector2d oldPosition, Vector2d newPosition, Animal examined) {
        if(!examined.isAlive()) {
            deadAnimals.add(examined);
            amountOfLivingAnimals --;
            deadAnimalsCount ++;
            totalLifeLengthForDead += (epochNumber.get() - examined.getBirthEpoch());
        }
        else {
            sumOfEnergy += examined.getEnergy();
            sumOfLivingAnimalsChildren += examined.getChildCount();
            fieldsForSimulation.add(newPosition);
        }
    }

    private MapDirection randomDirection() {
        Random generator = new Random();
        int limit = generator.nextInt(8);

        MapDirection result = MapDirection.NORTH;

        for(int i = 0; i < limit; i++) {
            result = result.next();
        }

        return result;

    }

    private void grassGrows() {
        Vector2d junglePosition = map.randomFreePosition(true);
        Vector2d steppePosition = map.randomFreePosition(false);

        if(junglePosition != null) map.addGrass(new Grass(junglePosition, grassEatingProfit));
        if(steppePosition != null) map.addGrass(new Grass(steppePosition, grassEatingProfit));
    }

    private void simulateField(Vector2d field) {

        LinkedList<Animal> animalList = map.listOfAnimalsAt(field);
        boolean isGrassThere = map.isGrassAt(field);

        if((animalList.size() == 1)&&(isGrassThere)&&(animalList.getFirst().isAlive())) {
            map.grassGetsEatenBy(animalList.getFirst());
        }

        else if(animalList.size() > 1) {

            ArrayList<Animal> top1 = new ArrayList<>();
            ArrayList<Animal> top2 = new ArrayList<>();

            for(Animal animal: animalList) {
                if(top1.size() == 0) top1.add(animal);
                else if(top1.get(0).getEnergy() == animal.getEnergy() ) top1.add(animal);
                else if(top1.get(0).getEnergy() < animal.getEnergy()) {
                    top2 = top1;
                    top1 = new ArrayList<>();
                    top1.add(animal);
                }
                else if(top2.size() == 0) top2.add(animal);
                else if(top2.get(0).getEnergy() == animal.getEnergy()) top2.add(animal);
                else if(top2.get(0).getEnergy() < animal.getEnergy()) {
                    top2 = new ArrayList<>();
                    top2.add(animal);
                }
            }

            Animal first;
            Animal second;
            Random generator = new Random();

            if(top1.size() >= 2) {

                first = top1.get(generator.nextInt(top1.size()));
                top1.remove(first);
                second = top1.get(generator.nextInt(top1.size()));
            }
            else {
                first = top1.get(0);
                second = top2.get(generator.nextInt(top2.size()));
            }

            if((isGrassThere)&&(first.isAlive())) {
                map.grassGetsEatenBy(first);
            }

            if(second.getEnergy() >= minimalEnergyForReproduction) {
                int newBornIndex = index.get();

                animals.put(index.get(), first.copulateWith(second, reproductionCost, randomDirection(),
                        index.getAndIncrement(), epochNumber.get()));
                        // it doesn't change sumOfEnergy on map so it is unnecessary to change it
                sumOfLivingAnimalsChildren += 2; // Each of parents has one child more
                amountOfLivingAnimals ++;
                animals.get(newBornIndex).addObserver(this);

                if((followedEpochsLeft > 0)&&
                        ((followedFamily.containsKey(first.getID()))||(followedFamily.containsKey(second.getID())))) {
                    followedFamily.put(newBornIndex, animals.get(newBornIndex));
                }
            }
        }


    }
    private void removeDeadAnimals() {

        map.removeDeadAnimals();
        for(Animal deadAnimal : deadAnimals) {
            animals.remove(deadAnimal.getID());
        }
    }

    private void saveStatistics() {
        double averageLifetimeForDead = ((stats.getAverageLifeLength() * (deadAnimalsCount - deadAnimals.size()))
        + totalLifeLengthForDead * deadAnimals.size()) / deadAnimalsCount;

        if(amountOfLivingAnimals > 0) {
            stats.updateStats(amountOfLivingAnimals, map.getGrassAmount(), map.getLeadingGenes(), ((double)sumOfEnergy/(double)amountOfLivingAnimals), ((double)sumOfLivingAnimalsChildren/(double)amountOfLivingAnimals), averageLifetimeForDead);
        }
        else {
            stats.updateStats(amountOfLivingAnimals, map.getGrassAmount(), map.getLeadingGenes(), 0, 0, averageLifetimeForDead);
        }


    }

    public Statistics getStats() {
        return stats;
    }

    public FollowedAnimalStats getFollowedAnimal() {

        if(followedEpochsLeft > 0) {

            int descendantsNumber = 0;
            for(int id : followedFamily.keySet()) {
                descendantsNumber += animals.get(id).getChildCount();
            }

            return new FollowedAnimalStats(epochNumber.get(), followedAnimal.getGenotype(),
                    followedAnimal.getChildCount(), descendantsNumber, followedDeathEpoch);

        }
        else return null;
    }

    public EvolutionGeneratorMap getMap() {
        return map;
    }

    public int getEpoch() {
        return epochNumber.get();
    }

}
