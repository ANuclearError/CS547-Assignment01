package com.aidanogrady.cs547.assignment01.genetic;

import com.aidanogrady.cs547.assignment01.Chromosome;

import java.util.Arrays;
import java.util.Random;

/**
 * The population is a set of chromosomes either randomly created or spawned by
 * previous generations. It has the ability to evolve future generations by
 * combining the best solutions available.
 *
 * @author Aidan O'Grady
 * @since 0.1
 */
class Population {
    /**
     * The population.
     */
    private Chromosome[] population;

    /**
     * The size of population to be considered.
     */
    private int size;

    /**
     * The rate of elitism
     */
    private double elitism;

    /**
     * The probability of mutation occurring.
     */
    private double mutation;

    /**
     * The number of combatants in a tournament.
     */
    private int tournament;

    /**
     * RNG for mutation, crossover, etc.
     */
    private Random random;

    /**
     * Constructs a new population.
     *
     * @param size the desired size of population
     * @param elitism the desired elitism ratio
     * @param mutation the desired mutation rate
     * @param tournament the desired tournament size
     * @param target the solution to be found
     */
    Population(int size, double elitism, double mutation, int tournament,
               String target) {
        this.size = size;
        this.elitism = elitism;
        this.mutation = mutation;
        this.tournament = tournament;

        this.population = new Chromosome[size];
        for (int i = 0; i < size; i++) {
            population[i] = Chromosome.generateChromosome(target);
        }
        Arrays.sort(population);

        random = new Random();
    }

    /**
     * Returns the chromosome with the lowest fitness.
     *
     * @return best solution thus far.
     */
    Chromosome getFittest() {
        return population[0];
    }

    /**
     * Returns the average fitness of this population.
     *
     * @return average fitness.
     */
    double getAverageFitness() {
        double fitness = 0.0;
        for (int i = 0; i < size; i++) {
            fitness += population[i].getFitness();
        }
        return fitness / size;
    }

    /**
     * Returns the chromosome with the highest fitness.
     *
     * @return best solution thus far.
     */
    Chromosome getLeastFit() {
        return population[size - 1];
    }

    /**
     * Evolves the current population into a new one with better fitness.
     */
    void evolve() {
        // Create a subpool of the best solutions in the current population.
        int eliteSize = (int) Math.round(size * elitism);
        Chromosome[] elite = new Chromosome[eliteSize];
        System.arraycopy(population, 0, elite, 0, eliteSize);

        Chromosome[] nextGen = new Chromosome[size];
        for (int i = 0; i < nextGen.length; i++) {
            // Choose two random parents and offset for crossover.
            Chromosome mother = selectParent(elite);
            Chromosome father = selectParent(elite);
            int offset = random.nextInt(mother.getSolution().length);

            // Create child and roll on mutationRate.
            nextGen[i] = mother.crossover(father, offset);
            if (random.nextFloat() < mutation) {
                nextGen[i] = nextGen[i].mutate();
            }

            if (i < (size - 2)) { // Handle odd pop size.
                nextGen[++i] = father.crossover(mother, offset);
                if (random.nextFloat() < mutation) {
                    nextGen[i] = nextGen[i].mutate();
                }
            }
        }
        Arrays.sort(nextGen);
        population = nextGen;
    }

    /**
     * Selects a parent by performing a tournament with random members of the
     * population's elite.
     *
     * @param elite the population eligible for tournament
     * @return chromosome
     */
    private Chromosome selectParent(Chromosome[] elite) {
        Chromosome chromosome = elite[random.nextInt(elite.length)];
        for (int i = 0; i < tournament; i++) {
            Chromosome opponent = elite[random.nextInt(elite.length)];
            if (opponent.getFitness() < chromosome.getFitness())
                chromosome = opponent;
        }
        return chromosome;
    }
}
