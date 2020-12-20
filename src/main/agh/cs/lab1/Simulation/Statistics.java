package agh.cs.lab1.Simulation;

import agh.cs.lab1.Utilities.Genes;

import java.util.LinkedList;
import java.util.List;

public class Statistics {

    private long totalAnimalAmount;
    private long totalGrassAmount;
    private List<Genes> dominatingGenes;
    private double averageEnergy;
    private double averageLifeLength;
    private double averageChildAmount;

    public Statistics() {
        totalAnimalAmount = 0;
        totalGrassAmount = 0;
        dominatingGenes = new LinkedList<>();
        averageEnergy = 0;
        averageLifeLength = 0;
        averageChildAmount = 0;
    }

    public void updateStats( long totalAnimalAmount, long totalGrassAmount, List<Genes> dominatingGenes,
                             double averageEnergy, double averageLifeLength, double averageChildAmount) {
        this.totalAnimalAmount = totalAnimalAmount;
        this.totalGrassAmount = totalGrassAmount;
        this.dominatingGenes = dominatingGenes;
        this.averageEnergy = averageEnergy;
        this.averageLifeLength = averageLifeLength;
        this.averageChildAmount = averageChildAmount;
    }

    public long getTotalAnimalAmount() {
        return totalAnimalAmount;
    }

    public long getTotalGrassAmount() {
        return totalGrassAmount;
    }

    public List<Genes> getDominatingGenes() {
        return dominatingGenes;
    }

    public double getAverageEnergy() {
        return averageEnergy;
    }

    public double getAverageLifeLength() {
        return averageLifeLength;
    }

    public double getAverageChildAmount() {
        return averageChildAmount;
    }
}
