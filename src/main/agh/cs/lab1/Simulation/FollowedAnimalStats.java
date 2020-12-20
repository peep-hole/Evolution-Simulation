package agh.cs.lab1.Simulation;

import agh.cs.lab1.Utilities.Genes;

public class FollowedAnimalStats {
    public final int epoch;
    public final Genes genotype;
    public final int childrenAmount;
    public final int descendantsAmount;
    public final Integer deathEpoch;

    public FollowedAnimalStats(int epoch, Genes genotype, int childrenAmount, int descendantsAmount, int deathEpoch) {

        this.epoch = epoch;
        this.genotype = genotype;
        this.childrenAmount = childrenAmount;
        this.descendantsAmount = descendantsAmount;
        this.deathEpoch = deathEpoch;

    }

}
