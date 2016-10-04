package com.aidanogrady.cs547.assignment01.random;

import com.aidanogrady.cs547.assignment01.Chromosome;
import com.aidanogrady.cs547.assignment01.Search;
import com.aidanogrady.cs547.assignment01.hill.HillClimbingSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Random;

/**
 * Random search, it's going to be useless.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public class RandomSearch implements Search {
    private static final Logger LOGGER = LoggerFactory.getLogger(RandomSearch.class);

    /**
     * Random generator to ensure the chaos required.
     */
    private static final Random RAND = new Random();

    @Override
    public int search(Properties properties) {
        String target = properties.getProperty("target");
        int limit = Integer.parseInt(properties.getProperty("random.limit"));

        int length = target.length();

        String next = "";
        for (int i = 0; i < limit; i++) {
            next = generateString(length);
            if (next.equals(target)) {
                LOGGER.info("Attempt " + i + " found! " + new Chromosome(next, target));
                return i;
            }
        }
        LOGGER.info("Searched halted on: " + new Chromosome(next, target));
        return -1;
    }

    /**
     * Generates a random string of the given length.
     *
     * @param length the length of string
     * @return string
     */
    private String generateString(int length) {
        char[] arr = new char[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (char) (RAND.nextInt(95) + 32);
        }
        return String.valueOf(arr);
    }

    @Override
    public int benchmark(Properties properties) {
        int runs = Integer.parseInt(properties.getProperty("benchmark"));

        LOGGER.info("RANDOM SEARCH: " + runs + " RUNS");
        System.out.println("RANDOM SEARCH: " + runs + " RUNS");

        int successes = 0;
        long totalTime = 0;

        for (int i = 1; i <= runs; i++) {
            LOGGER.info("Run " + i + " starting");
            long start = System.currentTimeMillis();
            int result = search(properties);
            long end = System.currentTimeMillis();

            if (result > 0) {
                successes++;
                LOGGER.info("Run " + i + " succeeded in " + result+ " attempts");
                System.out.println("\tRun " + i + " succeeded in " + result+ " attempts");
            } else {
                LOGGER.info("Run " + i + " failed to find target in limit");
                System.out.println("\tRun " + i + " failed to find target in limit");
            }
            totalTime += (end - start);
        }

        double successRate = ((double) successes / (double) runs) * 100;
        successRate = Math.round(successRate * 100) / 100;

        LOGGER.info("Number of successes: " + successes);
        System.out.println("Number of successes: " + successes);
        LOGGER.info("Success rate: " + successRate);
        System.out.println("Success rate: " + successRate + "%");
        LOGGER.info("Total time: " + totalTime + "ms");
        System.out.println("Total time: " + totalTime + "ms");
        LOGGER.info("Average time: " + totalTime/runs + "ms");
        System.out.println("Average time: " + totalTime/runs + "ms");
        return successes;
    }
}
