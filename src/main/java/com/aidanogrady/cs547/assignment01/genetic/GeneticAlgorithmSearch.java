package com.aidanogrady.cs547.assignment01.genetic;

import com.aidanogrady.cs547.assignment01.Search;

/**
 * The Genetic Algorithm search is not a local search, but instead polls various
 * different potential solutions, and uses them to continuously create better
 * ones until the correct solution is found.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public class GeneticAlgorithmSearch implements Search {
    @Override
    public int search(String target) {
        int size = 2048;
        double elitism = 0.1;
        double mutation = 0.1;
        int tournament = 16;
        Population pop = new Population(size, elitism, mutation, tournament,
                target);
        int i = 0;
        Chromosome best = pop.getFittest();
        Chromosome worst = pop.getLeastFit();
        System.out.print("Gen " + i + ". Best: " + best.toString());
        System.out.print(" Worst: " + worst.toString());
        System.out.println(" Average: " + pop.getAverageFitness());

        while (best.getFitness() > 0) {
            pop.evolve();
            best = pop.getFittest();
            worst = pop.getLeastFit();
            i++;
            System.out.print("Gen " + i + ". Best: " + best.toString());
            System.out.print(" Worst: " + worst.toString());
            System.out.println(" Average: " + pop.getAverageFitness());
        }
        return i;
    }
}
