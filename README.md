<!-- DESCRIPTION -->
# Project 2 - Convex Hull Divide and Conquer

A program that computes the Convex Hull of a set *P* of *n* points in a 2D plane, where the Convex Hull is defined as the smallest convex polygon containing the *n* points. We implement this using Divide and Conquer where we sort the points by the x coordinates, divide the set of points into two halves, recursively find the Convex Hull of each half, and combine them to make an overall Convex Hull.

<!-- REQUIREMENTS -->
## Requirements
- Java version "22.0.2" 2024-07-16
- Java(TM) SE Runtime Environment (build 22.0.2+9-70)
- JUnit 5

<!-- INSTALLATION -->
## How to Install
1. Clone Repo: 
```sh
git clone https://github.com/lgonza32/Project2
```
2. Navigate to directory
4. Run program

<!-- USAGE -->
## Usage
To run this program, simply execute the `Main.java` file. Note that within `Main.java`, there are commented out lines where it prints out the points. If you want to see the points, uncomment those lines. I commented them out so I can just see the output of experimental result in nanoseconds without the hassle of seeing 1,000,000+ points.

### Input:
- Users are able to set how many randomly generated points (n) they want to calculate.

### Output:
- The program will either output randomly generated points in the Convex Hull and/or display the runtime of the program in nanoseconds.

### Example:
```sh
Enter the number of points (n): 10000000
< -- Points will show here if uncommented -->
Experimental Result: 2114800900 ns
```

<!-- PROGRAM STRUCTURE -->
## Program Structure
- Main.java: Contains main method of input and output of program.
- ConvexHull.java: Contains the Convex Hull, Divide and Conquer implmentation.
- Point.java: Contains the points implementation.

## Time Complexity
Overall time complexity of the program is *O(n log n)*
- Divide Step: *O(1)*
- Conquer Step (Recursively): *O(log n)*
- Merge Step: *O(n)*
- Overall recurrence relation: *T(n) = 2T(n/2) + O(n)*
<p> Theoretical analysis of the Time Complexity is stated within the submitted document. </p>