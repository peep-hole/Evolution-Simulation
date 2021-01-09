package agh.cs.lab1.Simulation;

import agh.cs.lab1.Enums.MapDirection;
import agh.cs.lab1.MapElements.Animal;
import agh.cs.lab1.MapElements.Grass;
import agh.cs.lab1.Maps.EvolutionGeneratorMap;
import agh.cs.lab1.Maps.IStateChangeObserver;
import agh.cs.lab1.Utilities.Genes;
import agh.cs.lab1.Utilities.Vector2d;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationEngine implements IStateChangeObserver {

    // SIMULATION UTIL

    private final EvolutionGeneratorMap map;

    private final Map<Integer, Animal> animals;

    private LinkedList<Animal> deadAnimals;
    private Set<Vector2d> fieldsForSimulation;

    private final AtomicInteger index;
    private final AtomicInteger epochNumber;


    // PARAMETERS

    private final int grassEatingProfit;
    private final int moveCost;
    private final int minimalEnergyForReproduction;

    private static final double reproductionCost = 0.25;
    private static final int amountOfGrassPerSectorOnStart = 1;

    // STATISTICS

    private final Statistics stats;
    private int amountOfLivingAnimals;
    private int sumOfEnergy;
    private int sumOfLivingAnimalsChildren;
    private int deadAnimalsCount;
    private int totalLifeLengthForDead;

    // FOLLOWED ANIMAL STATS

    private Animal followedAnimal;
    private int followedEpochsLeft;
    private Integer followedDeathEpoch;
    private int startChildren;
    private int startDescendants;
    private int followedEpoch;

    // SUMMARY STATS

    private int totalAnimalAverages;
    private int totalGrassAverages;
    private final Map<Genes, Integer> geneTotalSumOfOccur;
    private double totalAverageEnergy;
    private double finalTotalLifeLength;
    private double totalChildrenCountAverages;


    public SimulationEngine(int mapHeight, int mapWidth, float jungleRatio, int amountOfAnimalsOnStart, int moveCost,
                            int grassEatingProfit, int startEnergy) {   // nie lepiej przekazać jakiś obiekt opakowujący ustawienia?

        map = new EvolutionGeneratorMap(mapWidth, mapHeight, jungleRatio);
        animals = new HashMap<>();
        index = new AtomicInteger(0);

        totalAnimalAverages = 0;
        totalGrassAverages = 0;
        geneTotalSumOfOccur = new HashMap<>();
        totalAverageEnergy = 0.0;
        finalTotalLifeLength = 0;
        totalChildrenCountAverages = 0;


        amountOfLivingAnimals = amountOfAnimalsOnStart;
        sumOfEnergy = 0;

        for (int i = 0; i < amountOfAnimalsOnStart; i++) {
            int id = index.get();

            animals.put(index.get(), new Animal(map, map.randomVectorInRange(map.getLowerLeftCorner(), map.getUpperRightCorner()),
                    randomDirection(), Genes.createRandomGenotype(), index.getAndIncrement(), startEnergy, 0));

            animals.get(id).addObserver(this);
            sumOfEnergy += animals.get(id).getEnergy();
        }

        this.grassEatingProfit = grassEatingProfit;
        this.moveCost = moveCost;

        minimalEnergyForReproduction = (startEnergy / 2);
        deadAnimalsCount = 0;

        stats = new Statistics();

        followedEpochsLeft = 0;

        epochNumber = new AtomicInteger(0);

        deadAnimals = new LinkedList<>();

        for (int i = 0; i < amountOfGrassPerSectorOnStart; i++) {
            grassGrows();
        }

        saveStatistics();
    }


    // SIMULATION

    public void runDay() {

        epochNumber.incrementAndGet();

        totalLifeLengthForDead = 0;
        sumOfEnergy = 0;
        fieldsForSimulation = new HashSet<>();

        sumOfLivingAnimalsChildren = 0;

        removeDeadAnimals();
        deadAnimals = new LinkedList<>();

        for (Animal animal : animals.values()) {
            animal.rotateAndRun(moveCost);
        }

        for (Vector2d field : fieldsForSimulation) {
            simulateField(field);
        }

        grassGrows();

        saveStatistics();

        if (followedEpochsLeft > 0) {
            if ((!followedAnimal.isAlive()) && (followedDeathEpoch == null)) followedDeathEpoch = epochNumber.get();
            followedEpochsLeft--;
        }
    }

    // ANIMAL/FIELD SIMULATION

    @Override
    public void stateChanged(Vector2d oldPosition, Vector2d newPosition, Animal examined) {
        if (!examined.isAlive()) {
            deadAnimals.add(examined);
            amountOfLivingAnimals--;
            deadAnimalsCount++;
            totalLifeLengthForDead += (epochNumber.get() - examined.getBirthEpoch());
        } else {
            sumOfEnergy += examined.getEnergy();
            sumOfLivingAnimalsChildren += examined.getChildCount();
            fieldsForSimulation.add(newPosition);
        }
    }

    private MapDirection randomDirection() {
        Random generator = new Random();
        int limit = generator.nextInt(8);

        MapDirection result = MapDirection.NORTH;

        for (int i = 0; i < limit; i++) {
            result = result.next();
        }

        return result;

    }

    public void followAnimalFor(int numberOfEpochs, int id) {
        followedEpochsLeft = numberOfEpochs;
        followedAnimal = animals.get(id);
        followedDeathEpoch = null;
        startChildren = followedAnimal.getChildCount();
        startDescendants = followedAnimal.getSumOfDescendants();
        followedEpoch = epochNumber.get();
    }

    private void simulateField(Vector2d field) {

        LinkedList<Animal> animalList = map.listOfAnimalsAt(field);
        boolean isGrassThere = map.isGrassAt(field);

        if ((animalList.size() == 1) && (isGrassThere) && (animalList.getFirst().isAlive())) {
            map.grassGetsEatenBy(animalList.getFirst());
        } else if (animalList.size() > 1) {

            ArrayList<Animal> top1 = new ArrayList<>();
            ArrayList<Animal> top2 = new ArrayList<>();

            for (Animal animal : animalList) {
                if (top1.size() == 0) top1.add(animal);
                else if (top1.get(0).getEnergy() == animal.getEnergy()) top1.add(animal);
                else if (top1.get(0).getEnergy() < animal.getEnergy()) {
                    top2 = top1;
                    top1 = new ArrayList<>();
                    top1.add(animal);
                } else if (top2.size() == 0) top2.add(animal);
                else if (top2.get(0).getEnergy() == animal.getEnergy()) top2.add(animal);
                else if (top2.get(0).getEnergy() < animal.getEnergy()) {
                    top2 = new ArrayList<>();
                    top2.add(animal);
                }   // czy posortowanie listy nie uprościłoby tego kodu?
            }

            Animal first;
            Animal second;
            Random generator = new Random();

            if (top1.size() >= 2) {

                first = top1.get(generator.nextInt(top1.size()));
                top1.remove(first);
                second = top1.get(generator.nextInt(top1.size()));
            } else {
                first = top1.get(0);
                second = top2.get(generator.nextInt(top2.size()));
            }

            if ((isGrassThere) && (first.isAlive())) {  // skomplikował Pan sobie życie wyborem momentu, kiedy zwierzę traci energię
                map.grassGetsEatenBy(first);    // jeśli kilka zwierząt ma równą energię, to powinny podzielić się trawą
            }

            if (second.getEnergy() >= minimalEnergyForReproduction) {
                int newBornIndex = index.get();

                animals.put(index.get(), first.copulateWith(second, reproductionCost, randomDirection(),
                        index.getAndIncrement(), epochNumber.get()));
                // it doesn't change sumOfEnergy on map so it is unnecessary to change it
                sumOfLivingAnimalsChildren += 2; // Each of parents has one child more
                amountOfLivingAnimals++;
                animals.get(newBornIndex).addObserver(this);

            }
        }
    }

    private void removeDeadAnimals() {

        map.removeDeadAnimals();
        for (Animal deadAnimal : deadAnimals) {
            animals.remove(deadAnimal.getID());
        }
    }

    // STATISTICS

    private void saveStatistics() {

        double averageLifetimeForDead;


        if (deadAnimalsCount == 0) averageLifetimeForDead = 0;

        else averageLifetimeForDead = ((stats.getAverageLifeLength() * (double) (deadAnimalsCount - deadAnimals.size()))
                + totalLifeLengthForDead) / (double) deadAnimalsCount;


        if (amountOfLivingAnimals > 0) {

            double avgChild = (double) (sumOfLivingAnimalsChildren) / (double) (amountOfLivingAnimals);

            stats.updateStats(amountOfLivingAnimals, map.getGrassAmount(), map.getLeadingGenes(), ((double) sumOfEnergy / (double) amountOfLivingAnimals), averageLifetimeForDead, avgChild, epochNumber.get());
        } else {
            stats.updateStats(amountOfLivingAnimals, map.getGrassAmount(), map.getLeadingGenes(), 0, averageLifetimeForDead, 0, epochNumber.get());
        }

        totalAnimalAverages += stats.getTotalAnimalAmount();
        totalGrassAverages += stats.getTotalGrassAmount();
        totalAverageEnergy += stats.getAverageEnergy();
        totalChildrenCountAverages += stats.getAverageChildAmount();
        finalTotalLifeLength += stats.getAverageLifeLength();

        HashMap<Genes, Integer> updateGene = map.getSumOfGeneOccur();
        for (Genes gene : updateGene.keySet()) {
            geneTotalSumOfOccur.putIfAbsent(gene, 0);
            int occurTimes = geneTotalSumOfOccur.get(gene);
            geneTotalSumOfOccur.replace(gene, occurTimes + updateGene.get(gene));
        }

    }

    public void saveStatsTxt(int mapNumber) {   // czy to na pewno odpowiedzialność silnika?

        try {


            FileWriter writer = new FileWriter("stats " + mapNumber + ".txt");

            writer.write("Epoch: " + epochNumber);
            writer.write("\nAverage Animal Amount: " + (double) totalAnimalAverages / (double) epochNumber.get() + "\n");
            writer.write("Average Grass Amount: " + (double) totalGrassAverages / (double) epochNumber.get() + "\n");
            writer.write("Average Energy Amount: " + totalAverageEnergy / (double) epochNumber.get() + "\n");
            writer.write("Average Children Amount: " + totalChildrenCountAverages / (double) epochNumber.get() + "\n");
            writer.write("Average Life Length: " + finalTotalLifeLength / (double) epochNumber.get() + "\n");


            Genes leader = null;

            for (Genes gene : geneTotalSumOfOccur.keySet()) {
                if ((leader == null) || (geneTotalSumOfOccur.get(gene) > geneTotalSumOfOccur.get(gene))) {
                    leader = gene;
                }
            }

            writer.write("Most popular genotype: " + leader + "\n");

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    // GRASS SIMULATION

    private void grassGrows() {
        Vector2d junglePosition = map.randomFreePosition(true);
        Vector2d steppePosition = map.randomFreePosition(false);

        if (junglePosition != null) map.addGrass(new Grass(junglePosition, grassEatingProfit));
        if (steppePosition != null) map.addGrass(new Grass(steppePosition, grassEatingProfit));
    }


    // GETTERS

    public Statistics getStats() {
        return stats;
    }

    public FollowedAnimalStats getFollowedAnimal() {

        if (followedEpochsLeft > 0) {

            return new FollowedAnimalStats(epochNumber.get() - followedEpoch,
                    followedAnimal.getGenotype(),
                    followedAnimal.getChildCount() - startChildren,
                    followedAnimal.getSumOfDescendants() - startDescendants,
                    followedDeathEpoch);

        } else return null;
    }

    public EvolutionGeneratorMap getMap() {
        return map;
    }


}
