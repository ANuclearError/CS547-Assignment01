package com.aidanogrady.cs547.assignment01.genetic;

import com.aidanogrady.cs547.assignment01.Search;

import java.util.Properties;

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
    public int search(Properties properties) {
        String target = properties.getProperty("target");
        int size = Integer.parseInt(properties.getProperty("ga.population"));
        double elitism = Double.parseDouble(properties.getProperty("ga.elitism"));
        double mutation = Double.parseDouble(properties.getProperty("ga.mutation"));
        int tournament = Integer.parseInt(properties.getProperty("ga.tournament"));

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
