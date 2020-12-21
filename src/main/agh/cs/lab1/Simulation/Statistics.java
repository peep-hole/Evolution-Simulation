package agh.cs.lab1.Simulation;

import agh.cs.lab1.Utilities.Genes;

import java.util.LinkedList;

public class Statistics {

    private long totalAnimalAmount;
    private long totalGrassAmount;
    private LinkedList<Genes> dominatingGenes;
    private double averageEnergy;
    private double averageLifeLength;
    private double averageChildAmount;
    private int epoch;

    public Statistics() {
        totalAnimalAmount = 0;
        totalGrassAmount = 0;
        dominatingGenes = new LinkedList<>();
        averageEnergy = 0;
        averageLifeLength = 0;
        averageChildAmount = 0;
    }

    public void updateStats( long totalAnimalAmount, long totalGrassAmount, LinkedList<Genes> dominatingGenes,
                             double averageEnergy, double averageLifeLength, double averageChildAmount, int epoch) {
        this.totalAnimalAmount = totalAnimalAmount;
        this.totalGrassAmount = totalGrassAmount;
        this.dominatingGenes = dominatingGenes;
        this.averageEnergy = averageEnergy;
        this.averageLifeLength = averageLifeLength;
        this.averageChildAmount = averageChildAmount;
        this.epoch = epoch;
    }

    public long getTotalAnimalAmount() {
        return totalAnimalAmount;
    }

    public long getTotalGrassAmount() {
        return totalGrassAmount;
    }

    public LinkedList<Genes> getDominatingGenes() {
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

    public int getEpoch() {
        return epoch;
    }
}
