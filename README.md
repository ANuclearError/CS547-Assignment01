# CS547-Assignment01

## Synopsis
This Java program provides implementations of searhc problems looking for a
given string, using random, hill-climbling and genetic algorithms, comparing
the results gained from these algorithms.

The program allows for the tweaking of parameters within these algorithms as
part of the benchmarking.

## Author
Aidan O'Grady (201218150) - wlb12153@uni.strath.ac.uk

## Motivation
This project is a submission for Assignment 01 for class CS547 Advanced Topics
in Computer Science for the MEng Computer Science course in the University of
Strathclyde, Glasgow.

## How to Run
```java -jar CS547-Assignment01-AidanOGrady.jar config.properties```

### Properties
 * target: The string to search for.
 * benchmark: The umber of times to run each search/
 * random.limit: The number of attempts to generate a random string
 before giving up.
 * hillclimb.steps: The number of steps the algorithm takes per climb.
 * ga.population: The size of population in genetic algorithm.
 * ga.elitism: (Double from 0 to 1) The subset of top chromosomes to accept as
parents.
 * ga.mutation: (Double from 0 to 1) The probability of mutation
 * ga.tournament: Number of chromosomes in tournament selection.


## Output
The console will display a summary of the results in each search. The ```log```
directory will contain detailed results of each search.
