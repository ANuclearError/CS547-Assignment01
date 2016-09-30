package com.aidanogrady.cs547.assignment01.hill;

import com.aidanogrady.cs547.assignment01.Chromosome;
import com.aidanogrady.cs547.assignment01.Search;
import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        // Select random start point.
        String randomString = subset.get(RAND.nextInt(subset.size()));
        Chromosome next = new Chromosome(randomString, target);

        int i = 1;
        summary(i, next);
        int resets = 0;
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
                LOGGER.info("Random restart");
                randomString = subset.get(RAND.nextInt(subset.size()));
                next = new Chromosome(randomString, target);
                steps++;
                resets++;
            }
            i++;
            summary(i, next);
        }
        LOGGER.info("Number of resets: " + resets);
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
        return 0;
    }
}
