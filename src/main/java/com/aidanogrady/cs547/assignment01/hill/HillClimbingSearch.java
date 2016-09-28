package com.aidanogrady.cs547.assignment01.hill;

import com.aidanogrady.cs547.assignment01.Chromosome;
import com.aidanogrady.cs547.assignment01.Search;
import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

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

    @Override
    public int search(Properties properties) {
        String target = properties.getProperty("target");
        Chromosome next = Chromosome.generateChromosome(target);
        int i = 1;
        summary(i, next);
        while (next.getFitness() > 0) {
            List<Chromosome> neighbourhood = neighbourhood(next, target);
            boolean plateau = true;
            for (Chromosome c : neighbourhood) {
                if (c.getFitness() < next.getFitness()) {
                    next = c;
                    plateau = false;
                }
            }

            if (plateau) {
                LOGGER.info("Random restart");
                next = Chromosome.generateChromosome(target);
            }
            i++;
            summary(i, next);
        }
        return i;
    }

    private void summary(int i, Chromosome next) {
        LOGGER.info(i + ". " + next);
    }

    private List<Chromosome> neighbourhood(Chromosome chromosome, String target) {
        List<Chromosome> neighbourhood = new ArrayList<>();
        int length = chromosome.getSolution().length;
        for (int i = 0; i < length; i++) {
            char[] arr = chromosome.getSolution();
            arr[i] = (char) (arr[i] - 1);
            neighbourhood.add(new Chromosome(String.valueOf(arr), target));

            arr[i] = (char) (arr[i] + 2);
            neighbourhood.add(new Chromosome(String.valueOf(arr), target));
        }
        //LOGGER.info(neighbourhood.toString());
        return neighbourhood;
    }

    @Override
    public int benchmark(Properties properties) {
        return 0;
    }
}
