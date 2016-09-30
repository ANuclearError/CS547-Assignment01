package com.aidanogrady.cs547.assignment01;

import java.util.Properties;

/**
 * Interface for various search algorithms.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public interface Search {
    int search(Properties properties);

    int benchmark(Properties properties);
}
