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
                Search search = new HillClimbingSearch();
                search.search(properties);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
