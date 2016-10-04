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
     * The target of this hill climb.
     */
    private String target;

    /**
     * The number of resets required to complete search.
     */
    private int restarts;

    @Override
    public int search(Properties properties) {
        target = properties.getProperty("target");
        int steps = Integer.parseInt(properties.getProperty("hillclimb.steps"));

        // Select random start point.
        Chromosome next = Chromosome.generateChromosome(target);

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
                next = Chromosome.generateChromosome(target);
                restarts++;
                LOGGER.info("Restart number " + restarts);
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
     * Returns the neighbouring strings of the given solution within the given
     * steps.
     *
     * @param chromosome the current
     * @return neighbourhood
     */
    private List<Chromosome> neighbourhood(Chromosome chromosome, int steps) {
        List<Chromosome> neighbourhood = new ArrayList<>();

        // This neighbourhood guarantees an improvement will be found.
        char[] orig = chromosome.getSolution();
        for (int i = 0; i < orig.length; i++) {
            for (int j = 1; j < steps; j++) {
                char[] arr = new char[orig.length];

                System.arraycopy(orig, 0, arr, 0, arr.length);

                int val = (orig[i] - 32 - j) % 94 + 32;
                arr[i] = (char) val;
                neighbourhood.add(new Chromosome(arr, target));

                val = (orig[i] - 32 + j) % 95 + 32;
                arr[i] = (char) val;
                neighbourhood.add(new Chromosome(arr, target));
            }
        }
        return neighbourhood;
    }

    @Override
    public int benchmark(Properties properties) {
        int runs = Integer.parseInt(properties.getProperty("benchmark"));

        LOGGER.info("HILL CLIMBING: " + runs + " RUNS");
        System.out.println("HILL CLIMBING: " + runs + " RUNS");

        int totalClimbs = 0;
        int totalRestarts = 0;
        long totalTime = 0;

        for (int i = 1; i <= runs; i++) {
            LOGGER.info("Run " + i + " starting");
            long start = System.currentTimeMillis();
            int result = search(properties);
            long end = System.currentTimeMillis();

            totalClimbs += result;
            totalRestarts += restarts;
            totalTime += (end - start);

            LOGGER.info("Run " + i + " completed in " + result + " climbs with " + restarts + " restarts");
            System.out.println("\tRun " + i + " completed in " + result + " climbs with " + restarts + " restarts");
        }
        int averageClimbs = totalClimbs / runs;
        int averageRestarts = totalRestarts / runs;


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

    public static void main(String[] args) {
        char[] orig = {'H', 'i', '!'};
        for (int i = 0; i < orig.length; i++) {
            for (int j = 0; j < 95; j++) {
                char[] arr = new char[orig.length];
                System.arraycopy(orig, 0, arr, 0, arr.length);
                arr[i] = (char) (j + 32);
                System.out.println(arr);
            }
        }
    }
}
