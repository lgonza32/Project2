package main;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * The main class is used to launch the Convex Hull algorithm using Divide and
 * Conquer.
 * 
 * @author Laurenz Luke Gonzales
 *         GWID: G20104564
 */

public class Main {
    
    /**
     * Generates an array of n random points with x and y coordinates ranging from
     * -10 to 10.
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
    
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Get the number of n points from the user
        System.out.print("Enter the number of points (n): ");
        int n = scanner.nextInt();

        // Generate random points on a 2D plan
        Point[] points = randPoints(n);

        // Loop to print n number of points
        System.out.println("Points (x, y):");
        for (Point p : points) {
            System.out.println("(" + p.x + ", " + p.y + ")");
        }
        
        // Calculate the Convex Hull
        List<Point> convexHull = ConvexHull.convexHull(points);

        // Loop to print the points in the Convex Hull
        System.out.println("Convex Hull:");
        for (Point point : convexHull) {
            System.out.println(point);
        }

        scanner.close();
    }
}
