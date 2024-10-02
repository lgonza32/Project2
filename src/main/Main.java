package main;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * The main class is used to launch the Convex Hull using Divide and
 * Conquer program. This class also helps output the execution time, in ns, of
 * the program.
 * 
 * @author Laurenz Luke Gonzales
 *         GWID: G20104564
 */

public class Main {

    /**
     * This method Generates an array of n random points with x and y coordinates
     * ranging from -10 to 10.
     *
     * @param n The number of points to generate.
     * @return An array of randomly generated points.
     */
    public static Point[] randPoints(int n) {
        Random random = new Random();
        Point[] points = new Point[n];

        // Generate random points with x and y coordinates between -10 and 10
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(20) - 10; // Generates a value between -10 and 10
            int y = random.nextInt(20) - 10; // Generates a value between -10 and 10
            points[i] = new Point(x, y);
        }

        return points;
    }

    /**
     * This method computes and outputs the implementation of the Convex Hull
     * using Divide and Conquer program. It also calculates and outputs the
     * execution time of the program in nanoseconds.
     * 
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the number of n points from the user
        System.out.print("Enter the number of points (n): ");
        int n = scanner.nextInt();

        // Generate random points on a 2D plane
        Point[] points = randPoints(n);

        // Loop to print n number of points
        // System.out.println("Points (x, y):");
        // for (Point p : points) {
        // System.out.println("(" + p.x + ", " + p.y + ")");
        // }

        // To measure execution time with larger sizes of n more accurately, comment out
        // the loops that print out points (also helps out to not see 10,000,000+
        // points).
        long startTimeNs = System.nanoTime(); // Measure time in nanoseconds

        // Calculate the Convex Hull
        List<Point> convexHull = ConvexHull.convexHull(points);

        long endTimeNs = System.nanoTime();

        // Loop to print the points in the Convex Hull
        // System.out.println("Convex Hull:");
        // for (Point point : convexHull) {
        // System.out.println(point);
        // }

        long experimentalResultNs = endTimeNs - startTimeNs; // Time in nanoseconds

        // Output time results
        System.out.println("For n = " + n);
        System.out.println("Experimental Result (ns): " + experimentalResultNs + " ns");

        scanner.close();
    }
}
