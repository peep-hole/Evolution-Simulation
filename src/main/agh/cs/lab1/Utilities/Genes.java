package agh.cs.lab1.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Genes {

    private final int[] genotype;
    private final static int genomeSize = 32;
    private final static int kindsOfGenes = 8; // if you change genomeSize or kindOfGenes => remember to change prime numbers

    private final static int[] primeNumbers = {29, 31, 37, 41, 43, 47, 53, 59}; // min(primeNumbers) + kindOfGenes - 1 > genomeSize
                                                                                // primeNumbers.length == kindOfGenes

    private final int genesHashCode;

    private Genes(int[] genotype, int genesHashCode) {
        if(genotype.length != genomeSize) throw new IllegalArgumentException("Genotype length must be equal " +
                                                                    genomeSize + ", cannot be:" + genotype.length);
        this.genotype = genotype;
        this.genesHashCode = genesHashCode;
    }

    public static Genes createRandomGenotype() {
        Random generator = new Random();
        int[] genes = new int[genomeSize];

        for(int i = 0; i < kindsOfGenes; i++) {
            genes[i] = i;
        }
        for(int i = kindsOfGenes; i < genomeSize; i++) {
            genes[i] = generator.nextInt(kindsOfGenes);
        }

        Arrays.sort(genes);

        int[] geneTypeCounter = new int[genomeSize];
        for(int gene : genes) {
            geneTypeCounter[gene]++;
        }

        int genesHashCode = getGeneHashCode(geneTypeCounter);

        return new Genes(genes, genesHashCode);
    }

    public static Genes createInheritedGenotype(Genes parent1, Genes parent2) {
        Random generator = new Random();
        int[] resultGenotype = new int[genomeSize];
        int geneBorder1 = generator.nextInt(genomeSize - 2)+1;
        int geneBorder2 = generator.nextInt(genomeSize - 1 - geneBorder1) + geneBorder1;

        // initial gene fusion

        int downBorder = 0;
        int upperBoarder = geneBorder1;
        int counter = 0;

        for(int i =0; i < 3; i++) {
            if((counter != -2) && ((counter == 2) || (generator.nextInt(2) == 0))) {
                for(int j = downBorder; j< upperBoarder; j++) {
                    resultGenotype[j] = parent1.getGenotype()[j];
                }
                counter--;
            }
            else {
                for (int j = downBorder; j < upperBoarder; j++) {
                    resultGenotype[j] = parent2.getGenotype()[j];
                }
                counter++;
            }
            downBorder = upperBoarder;
            if(i == 0) upperBoarder = geneBorder2;
            if(i == 1) upperBoarder = genomeSize;
        }

        // gene correction

        int[] geneTypeCounter = new int[kindsOfGenes];
        for(int gene : resultGenotype) {
            geneTypeCounter[gene]++;
        }

        List<Integer> lackingGenes = new ArrayList<>();
        List<Integer> excessiveGenes = new ArrayList<>();

        for(int i = 0; i < kindsOfGenes; i++) {
            if(geneTypeCounter[i] == 0) lackingGenes.add(i);
            if(geneTypeCounter[i] > 1) excessiveGenes.add(i);
        }

        int hashCode = getGeneHashCode(geneTypeCounter);

        for(int lackingGene : lackingGenes) {
            int forSubstitution = excessiveGenes.get(generator.nextInt(excessiveGenes.size()));
            for(int i = 0; i < resultGenotype.length; i++) {
                if(resultGenotype[i] == forSubstitution) resultGenotype[i] = lackingGene;
            }
            if(--geneTypeCounter[forSubstitution] == 1) excessiveGenes.remove(new Integer(forSubstitution));
        }

        Arrays.sort(resultGenotype);

        return  new Genes(resultGenotype, hashCode);

    }

    public int[] getGenotype() {
        return genotype;
    }

    public int getRandomGene() {
        Random generator = new Random();
        return genotype[generator.nextInt(32)];
    }

    private static int getGeneHashCode(int[] geneTypeCounter) {

        int hashCode = 0;

        for(int i = 0; i < kindsOfGenes; i++) {
            hashCode += geneTypeCounter[i] * primeNumbers[i];
        }
        return  hashCode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if(this == other)
            return true;
        if (!(other instanceof Genes))
            return false;
        Genes that = (Genes) other;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode(){
        return this.genesHashCode;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int genome : genotype) {
            result.append(genome);
        }
        return result.toString();

    }

}
