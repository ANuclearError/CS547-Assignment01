package com.aidanogrady.cs547.assignment01.genetic;

import java.util.Random;

/**
 * A chromosome is a potential solution to the problem. It has a fitness that
 * is used to show how close the string is to its target.
 *
 * @author Aidan O'Grady
 * @since 0.1
 */
class Chromosome implements Comparable<Chromosome> {
    /**
     * Random generator to ensure the chaos required.
     */
    private static final Random RAND = new Random();

    /**
     * The fitness ranking of this solution.
     */
    private final int fitness;

    /**
     * The solution being considered.
     */
    private final char[] solution;

    /**
     * The target String this chromosome is trying to reach.
     */
    private final String target;

    /**
     * Creates a new Chromosome from the given solution and target strings.
     *
     * @param solution the solution being considered.
     * @param target the target desired.
     */
    Chromosome(String solution, String target) {
        this.solution = solution.toCharArray();
        this.target = target;
        this.fitness = calculateFitness();
    }

    /**
     * Creates a new Chromosome from the given solution char array and target
     * string.
     *
     * @param solution the solution being considered.
     * @param target the target desired.
     */
    public Chromosome(char[] solution, String target) {
        this.solution = solution;
        this.target = target;
        this.fitness = calculateFitness();
    }

    /**
     * Returns the solution.
     *
     * @return solution.
     */
    char[] getSolution() {
        return solution;
    }

    /**
     * Returns the fitness.
     *
     * @return fitness.
     */
    int getFitness() {
        return fitness;
    }

    /**
     * Returns the target string we are trying to generate.
     * @return target
     */
    public String getTarget() {
        return target;
    }

    /**
     * Calculates the fitness of this solution in relation to the given target.
     * A lower fitness indicates a better solution.
     *
     * @return fitness
     */
    private int calculateFitness() {
        char[] targetArray = target.toCharArray();

        int fitness = 0;

        int min = Math.min(solution.length, targetArray.length);
        for (int i = 0; i < min; i++) {
            fitness += Math.abs(solution[i] - targetArray[i]);
        }
        return fitness;
    }

    /**
     * Returns a new Chromosome with a randomly generated string the same length
     * as the given target string.
     *
     * @param target the target string being looked for.
     * @return new chromosome
     */
    static Chromosome generateChromosome(String target) {
        char[] arr = new char[target.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (char) (RAND.nextInt(95) + 32);
        }
        return new Chromosome(String.valueOf(arr), target);
    }

    /**
     * Performs a crossover with another chromosome, resulting
     * @param parent the other parent in the crossover
     * @param index the index at which to split the parents
     * @return crossover
     */
    Chromosome crossover(Chromosome parent, int index) {
        char[] arr = solution;
        char[] parArr = parent.getSolution();
        System.arraycopy(parArr, index, arr, index, arr.length - index);
        return new Chromosome(String.valueOf(arr), target);
    }

    /**
     * Returns a random mutation of the given Chromosome.
     *
     * @return mutated chromosome.
     */
    Chromosome mutate() {
        char[] arr = solution;
        int index = RAND.nextInt(arr.length);
        arr[index] = (char) (RAND.nextInt(95) + 32);
        return new Chromosome(String.valueOf(arr), target);
    }

    @Override
    public String toString() {
        return String.valueOf(solution) + " (" + fitness + ")";
    }

    /**
     * Compares this chromosome with another and returns the result, for sorting
     * purposes.
     *
     * @param o the chromosome being compared to
     * @return 0 if equal, -1 if this is better, 1 if this is worse
     */
    public int compareTo(Chromosome o) {
        if (o.getFitness() > fitness) // this is a better solution
            return -1;
        if (fitness > o.getFitness()) // this is a worse solution
            return 1;
        return 0;
    }
}
