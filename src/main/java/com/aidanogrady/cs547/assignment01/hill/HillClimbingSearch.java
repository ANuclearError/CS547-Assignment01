package com.aidanogrady.cs547.assignment01.hill;

import com.aidanogrady.cs547.assignment01.Chromosome;
import com.aidanogrady.cs547.assignment01.Search;
import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.*;

/**
 * Hill Climbing search operates by taking a random starting point in the search
 * space and using its fitness function to find neighboring solutions to
 * improve its results until the search is complete.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public class HillClimbingSearch implements Search {
    private static final Logger LOGGER = LoggerFactory.getLogger(HillClimbingSearch.class);

    /**
     * Random generator to ensure the chaos required.
     */
    private static final Random RAND = new Random();

    /**
     * The target of this hill climb.
     */
    private String target;

    /**
     * The subset of strings being considered. Due to maths, this prevents just
     * giving up. Due to sorting purposes, strings must be used rather than
     * Chromosomes.
     */
    private List<String> subset;

    /**
     * The number of resets required to complete search.
     */
    private int restarts;

    @Override
    public int search(Properties properties) {
        target = properties.getProperty("target");
        int max = Integer.parseInt(properties.getProperty("hill.size"));
        int steps = Integer.parseInt(properties.getProperty("hill.steps"));

        // Generate subset.
        subset = new ArrayList<>();
        subset.add(target); // Guarantee the target is in the subset.
        for (int i = 0; i < max; i++) {
            subset.add(randomString(target.length()));
        }
        Collections.sort(subset);
        LOGGER.info("Generated random subset of solutions");

        // Select random start point.
        String randomString = subset.get(RAND.nextInt(subset.size()));
        Chromosome next = new Chromosome(randomString, target);

        int i = 1;
        restarts = 0;
        summary(i, next);
        while (next.getFitness() > 0) {
            List<Chromosome> neighbourhood = neighbourhood(next, steps);
            boolean plateau = true;
            for (Chromosome c : neighbourhood) {
                if (c.getFitness() < next.getFitness()) {
                    next = c;
                    plateau = false;
                }
            }

            if (plateau) {
                randomString = subset.get(RAND.nextInt(subset.size()));
                next = new Chromosome(randomString, target);
                steps++;
                restarts++;
                LOGGER.debug("Restarting");
            }
            i++;
            summary(i, next);
        }
        return i;
    }

    /**
     * Logs summer of current state.
     *
     * @param i the generation currently being logged
     * @param next the current chromosome.
     */
    private void summary(int i, Chromosome next) {
        LOGGER.info(i + ". " + next);
    }

    /**
     * Generates a random string of the given length.
     *
     * @param length the length of the string to generate.
     * @return random string.
     */
    private String randomString(int length) {
        char[] arr = new char[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (char) (RAND.nextInt(95) + 32);
        }
        return String.valueOf(arr);
    }

    /**
     * Returns the neighbouring strings of the given solution within the given
     * steps.
     *
     * @param chromosome the current
     * @param steps the number of steps that can be taken per climb
     * @return neighbourhood
     */
    private List<Chromosome> neighbourhood(Chromosome chromosome, int steps) {
        List<Chromosome> neighbourhood = new ArrayList<>();
        int index = subset.indexOf(String.valueOf(chromosome.getSolution()));
        for (int i = 0; i < steps; i++) {
            int posInd = (index + i) % subset.size();
            int negInd = index - i;
            if (negInd < 0)
                negInd = subset.size() + negInd;
            neighbourhood.add(new Chromosome(subset.get(posInd), target));
            neighbourhood.add(new Chromosome(subset.get(negInd), target));
        }
        return neighbourhood;
    }

    @Override
    public int benchmark(Properties properties) {
        int runs = Integer.parseInt(properties.getProperty("benchmark"));

        LOGGER.info("HILL CLIMBING: " + runs + " RUNS");
        System.out.println("HILL CLIMBING: " + runs + " RUNS");
        LOGGER.info("--------------------------------------------------------");
        System.out.println("--------------------------------------------------------");

        int totalClimbs = 0;
        int totalRestarts = 0;
        long totalTime = 0;

        for (int i = 1; i <= runs; i++) {
            long start = System.currentTimeMillis();
            int result = search(properties);
            long end = System.currentTimeMillis();

            totalClimbs += result;
            totalRestarts += restarts;
            totalTime += (end - start);

            System.out.println("Run " + i + " completed in " + result + " climbs with " + restarts + " restarts");
        }
        int averageClimbs = totalClimbs / runs;
        int averageRestarts = totalRestarts / runs;

        LOGGER.info("--------------------------------------------------------");
        System.out.println("--------------------------------------------------------");

        System.out.println("Average no. of climbs: " + averageClimbs + " climbs");
        LOGGER.info("Average no. of climbs: " + averageClimbs + " climbs");
        System.out.println("Average no. of restarts: " + averageRestarts + " restarts");
        LOGGER.info("Average no. of restarts: " + averageRestarts + " restarts");
        System.out.println("Total time: " + totalTime + "ms");
        LOGGER.info("Total time: " + totalTime + "ms");
        System.out.println("Average time: " + totalTime/runs + "ms");
        LOGGER.info("Average time: " + totalTime/runs + "ms");

        return averageClimbs;
    }
}
