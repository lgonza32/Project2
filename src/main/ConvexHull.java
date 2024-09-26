package main;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Implements the Convex Hull algorithm using divide and conquer.
 * This class provides methods to find and merge Convex Hulls on a 2D plane.
 * The scheme behind Convex Hull, Divide and Conquer for a set P of n points:
 * <ul>
 * <li>Sort n points by x coordinates (n log n).
 * <li>Divide those set of points into two subsets by finding a median O(1).
 * <li>Find Convex Hulls of each half recursively O(n log n).
 * <li>Merge the two hulls; O(n)
 * </ul>
 * The Overall Time Complexity should be: O(n log n).
 */
public class ConvexHull {

    /**
     * Mehod that finds the "east" most point (the point with the largest x value)
     * in the hull.
     * 
     * @param hull A list of points from a Convex Hull.
     * @return The index of the "east" most point in the hull.
     */
    public static int findEastMost(List<Point> hull) {
        int index = 0;
        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(i).x > hull.get(index).x) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Method that finds the "west" most point (the point with the smallest x value)
     * in the hull .
     * 
     * @param hull A list of points from a Convex Hull.
     * @return The index of the "west" most point in the hull.
     */
    public static int findWestMost(List<Point> hull) {
        int index = 0;
        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(i).x < hull.get(index).x) {
                index = i;
            }
        }
        return index;
    }

    /**
     * This method checks to make sure that we are iterating through the cycle in a
     * counterclockwise direction when finding points on a plane.
     * This helps ensure that the convex shape is still a polygon and connections to
     * the points do not concave.
     * 
     * @param a First point
     * @param b Second point
     * @param c Third point
     * @return Positive - if counterclockwise
     *         Negative - if clockwise
     *         Zero - if collinear
     */
    public static int checkCCW(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    /**
     * This method finds the upper tangent of the convex boundary.
     * This helps with the convex shape is still a polygon during the merging of the
     * two hulls.
     * 
     * @param leftHull   Convex Hull of the left subset of n points (left half).
     * @param rightHull  Convex Hull of the right subset of n points (right half).
     * @param indexLeft  Index of the "east" most point in the left hull.
     * @param indexRight Index of the "west" most point in the right hull.
     * @return An array of the indices of the upper tangent in the left and right
     *         hulls.
     */
    public static int[] findUpperTangent(List<Point> leftHull, List<Point> rightHull, int indexLeft, int indexRight) {
        // Initialize sizes of hulls
        int nLeft = leftHull.size();
        int nRight = rightHull.size();

        // Initialize starting indexes
        int upperLeft = indexLeft;
        int upperRight = indexRight;

        boolean tangentFound = false;

        while (!tangentFound) {
            tangentFound = true;
            // Traverse through left hull to find upper tangent
            while (checkCCW(rightHull.get(upperRight), leftHull.get(upperLeft),
                    leftHull.get((upperLeft + 1) % nLeft)) > 0) {
                upperLeft = (upperLeft + 1) % nLeft; // Move counterclockwise
                tangentFound = false; // Eliminate clockwise turns until tangent is found
            }
            // Traverse through right hull to find upper tangent
            while (checkCCW(leftHull.get(upperLeft), rightHull.get(upperRight),
                    rightHull.get((upperRight - 1 + nRight) % nRight)) < 0) {
                upperRight = (upperRight - 1 + nRight) % nRight; // Move clockwise
                tangentFound = false; // Eliminate clockwise turns until tangent is found
            }
        }
        return new int[] { upperLeft, upperRight };

    }

    /**
     * This method finds the lower tangent of the convex boundary.
     * This helps with the convex shape is still a polygon during the merging of the
     * two hulls.
     * 
     * @param leftHull   Convex Hull of the left subset of n points (left half).
     * @param rightHull  Convex Hull of the right subset of n points (right half).
     * @param indexLeft  Index of the "east" most point in the left hull.
     * @param indexRight Index of the "west" most point in the right hull.
     * @return An array of the indices of the lower tangent in the left and right
     *         hulls.
     */
    public static int[] findLowerTangent(List<Point> leftHull, List<Point> rightHull, int indexLeft, int indexRight) {
        // Initialize sizes of hulls
        int nLeft = leftHull.size();
        int nRight = rightHull.size();

        // Initialize starting indexes
        int lowerLeft = indexLeft;
        int lowerRight = indexRight;

        boolean tangentFound = false;

        while (!tangentFound) {
            tangentFound = true;
            // Traverse clockwise through the left hull to find the lower tangent
            while (checkCCW(rightHull.get(lowerRight), leftHull.get(lowerLeft),
                    leftHull.get((lowerLeft - 1 + nLeft) % nLeft)) < 0) {
                lowerLeft = (lowerLeft - 1 + nLeft) % nLeft; // Move clockwise
                tangentFound = false; // Eliminate clockwise turns until tangent is found
            }
            // Traverse counterclockwise through the right hull to find the lower tangent
            while (checkCCW(leftHull.get(lowerLeft), rightHull.get(lowerRight),
                    rightHull.get((lowerRight + 1) % nRight)) > 0) {
                lowerRight = (lowerRight + 1) % nRight; // Move counterclockwise
                tangentFound = false; // Eliminate clockwise turns until tangent is found
            }
        }
        return new int[] { lowerLeft, lowerRight };
    }

    /**
     * Method that finds the Convex Hull of a given set of P of n points.
     * 
     * @param points
     * @return
     */
    // public static List<Point> convexHull(Point[] points) {

    // // Sort the n points by x coords to make finding median easier
    // Arrays.sort(points, Comparator.comparingInt(p -> p.x));

    // return points;
    // }

}
