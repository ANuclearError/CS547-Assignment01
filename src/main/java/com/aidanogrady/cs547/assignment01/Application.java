package com.aidanogrady.cs547.assignment01;

import com.aidanogrady.cs547.assignment01.genetic.GeneticAlgorithmSearch;
import com.aidanogrady.cs547.assignment01.hill.HillClimbingSearch;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Main application class for the system.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public class Application {
    public static void main(String[] args) {
        System.out.println("CS547 Assignment 01: Introductory Exercise");
        System.out.println("Author: Aidan O'Grady (201218150)");

        if (args.length < 1) {
            System.out.println("Please provide a .properties file");
        } else {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(args[0]));
                if (validateProperties(properties)) {
                    Search[] searches = new Search[2];
                    searches[0] = new HillClimbingSearch();
                    searches[1] = new GeneticAlgorithmSearch();
                    for (Search search : searches) {
                        search.benchmark(properties);
                        System.out.println();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Validates the config properties are valid.
     *
     * @param properties the properties to validate.
     * @return valid
     */
    private static boolean validateProperties(Properties properties) {
        String[] props = {"target", "benchmark", "hill.steps", "hill.size",
                "ga.population", "ga.elitism", "ga.mutation", "ga.tournament"
        };

        String[] types = {"string", "int", "int", "int", "int", "double",
                "double", "int"
        };

        for (int i = 0; i < props.length; i++) {
            String prop = props[i];
            String value = properties.getProperty(prop);
            if (value == null) {
                System.out.println(prop + " property does not exist in file.");
            }
            switch (types[i]) {
                case "int":
                    try {
                        int val = Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        System.out.println(prop + " must be " + types[i]);
                        return false;
                    }
                    break;
                case "double":
                    try {
                        double val = Double.parseDouble(value);
                    } catch (NumberFormatException e) {
                        System.out.println(prop + " must be " + types[i]);
                        return false;
                    }
                    break;

            }
        }
        return true;
    }
}
