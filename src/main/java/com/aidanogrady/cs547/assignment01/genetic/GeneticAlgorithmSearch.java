package com.aidanogrady.cs547.assignment01.genetic;

import com.aidanogrady.cs547.assignment01.Search;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneticAlgorithmSearch.class);

    @Override
    public int search(Properties properties) {
        String target = properties.getProperty("target");
        int size = Integer.parseInt(properties.getProperty("ga.population"));
        double elitism = Double.parseDouble(properties.getProperty("ga.elitism"));
        double mutation = Double.parseDouble(properties.getProperty("ga.mutation"));
        int tournament = Integer.parseInt(properties.getProperty("ga.tournament"));

        Population pop = new Population(size, elitism, mutation, tournament,
                target);
        int i = 1;
        Chromosome best = pop.getFittest();

        summary(i, pop);
        while (best.getFitness() > 0) {
            pop.evolve();
            best = pop.getFittest();
            i++;
            summary(i, pop);
        }
        return i;
    }

    private void summary(int i, Population pop) {
        String summary = "Generation " + i + ". ";
        summary += "Best: " + pop.getFittest() + "  Worst: " + pop.getLeastFit();
        summary += " Average: " + pop.getAverageFitness();
        //LOGGER.info(summary);
    }

    @Override
    public int benchmark(Properties properties) {
        int runs = Integer.parseInt(properties.getProperty("benchmark"));

        LOGGER.info("GENETIC ALGORITHM: " + runs + " RUNS");
        LOGGER.info("--------------------------------------------------------");
        int total = 0;
        for (int i = 1; i <= runs; i++) {
            int result = search(properties);
            total += result;
            LOGGER.info("Run " + i + " completed in " + result + " generations");
        }
        int average = total / runs;
        LOGGER.info("--------------------------------------------------------");
        LOGGER.info("Average: " + average + " generations");

        return average;
    }
}
