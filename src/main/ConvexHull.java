package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Implements the Convex Hull algorithm using divide and conquer.
 * This class provides methods to find and merge Convex Hulls on a 2D plane.
 * The scheme behind Convex Hull, Divide and Conquer for a set P of n points:
 * <ul>
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
     * @return An array containing two indices (points) of the lower tangent in the
     *         left and right hulls.
     */
    public static int[] findUpperTangent(List<Point> leftHull, List<Point> rightHull, int indexLeft, int indexRight) {
        int nLeft = leftHull.size(), nRight = rightHull.size(); // Initialize sizes of hulls
        int upperLeft = indexLeft, upperRight = indexRight; // Initialize starting indexes
        boolean tangentFound = false;

        while (!tangentFound) {
            tangentFound = true;
            // Traverse through left hull to find upper tangent
            while (checkCCW(rightHull.get(upperRight), leftHull.get(upperLeft),
                    leftHull.get((upperLeft + 1) % nLeft)) > 0) {
                upperLeft = (upperLeft + 1) % nLeft; // Move counterclockwise
                tangentFound = false; // Keep searching
            }
            // Traverse through right hull to find upper tangent
            while (checkCCW(leftHull.get(upperLeft), rightHull.get(upperRight),
                    rightHull.get((upperRight - 1 + nRight) % nRight)) < 0) {
                upperRight = (upperRight - 1 + nRight) % nRight; // Move clockwise
                tangentFound = false; // Keep searching
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
     * @return An array containing two indices (points) of the lower tangent in the
     *         left and right hulls.
     */
    public static int[] findLowerTangent(List<Point> leftHull, List<Point> rightHull, int indexLeft, int indexRight) {
        int nLeft = leftHull.size(), nRight = rightHull.size(); // Initialize sizes of hulls
        int lowerLeft = indexLeft, lowerRight = indexRight; // Initialize starting indexes
        boolean tangentFound = false;

        while (!tangentFound) {
            tangentFound = true;
            // Traverse clockwise through the left hull to find the lower tangent
            while (checkCCW(rightHull.get(lowerRight), leftHull.get(lowerLeft),
                    leftHull.get((lowerLeft - 1 + nLeft) % nLeft)) < 0) {
                lowerLeft = (lowerLeft - 1 + nLeft) % nLeft; // Move clockwise
                tangentFound = false; // Keep searching
            }
            // Traverse counterclockwise through the right hull to find the lower tangent
            while (checkCCW(leftHull.get(lowerLeft), rightHull.get(lowerRight),
                    rightHull.get((lowerRight + 1) % nRight)) > 0) {
                lowerRight = (lowerRight + 1) % nRight; // Move counterclockwise
                tangentFound = false; // Keep searching
            }
        }
        return new int[] { lowerLeft, lowerRight };
    }

    /**
     * This method allows to merge the two halves into a single Convex Hull
     * by finding the upper and lower tangents and combining the points between
     * the tangents.
     *
     * @param leftHull  Convex Hull of the left subset of n points (left half).
     * @param rightHull Convex Hull of the right subset of n points (right half).
     * @return A list of points representing the merged Convex Hull.
     */
    public static List<Point> mergeHulls(List<Point> leftHull, List<Point> rightHull) {
        // Initialize and call appropriate functions to find starting points
        int indexLeft = findEastMost(leftHull);
        int indexRight = findWestMost(rightHull);

        // Find upper and lower tangents
        int[] upperTangent = findUpperTangent(leftHull, rightHull, indexLeft, indexRight);
        int[] lowerTangent = findLowerTangent(leftHull, rightHull, indexLeft, indexRight);
        int upperLeft = upperTangent[0];
        int upperRight = upperTangent[1];
        int lowerLeft = lowerTangent[0];
        int lowerRight = lowerTangent[1];

        List<Point> mergedHull = new ArrayList<>(); // Hold final Convex Hull

        // Adds up all the points from the left hull between tangents
        for (int i = upperLeft; i != lowerLeft; i = (i + 1) % leftHull.size()) {
            mergedHull.add(leftHull.get(i));
        }
        mergedHull.add(leftHull.get(lowerLeft)); // Add lower tangent point

        // Adds up all the points from the right hull between the tangents
        for (int i = lowerRight; i != upperRight; i = (i + 1) % rightHull.size()) {
            mergedHull.add(rightHull.get(i));
        }
        mergedHull.add(rightHull.get(upperRight)); // Add upper tangent point

        return mergedHull;
    }

    /**
     * This method allows us to find the meridian, recursively find the hulls of
     * each subset halves, and merge it into one Convex Hull. This method is the
     * Divide and Conquer step through with the merge step.
     * 
     * @param points     An array of points.
     * @param indexLeft  Index of the "east" most point in the left hull.
     * @param indexRight Index of the "west" most point in the right hull.
     * @return
     */
    public static List<Point> findHull(Point[] points, int indexLeft, int indexRight) {
        // If there's only one or two points, they form the hull
        // This avoids stack overflow error
        if (indexRight - indexLeft + 1 <= 2) {
            List<Point> hull = new ArrayList<>();
            for (int i = indexLeft; i <= indexLeft; i++) {
                hull.add(points[i]);
            }
            return hull;
        }

        // Find the meridian of the "west" most and "east" most points
        int meridian = (indexLeft + indexRight) / 2;

        // Recursively find the two subset halves
        List<Point> leftHull = findHull(points, indexLeft, meridian);
        List<Point> rightHull = findHull(points, meridian + 1, indexRight);

        // Merge the two into one Convex Hull
        return mergeHulls(leftHull, rightHull);
    }

    /**
     * This method that finds the Convex Hull of a given set of P of n points using
     * Divide and Conquer. The points get sorted first in to split the points easier
     * to find the meridian.
     * 
     * @param points An array of points.
     * @return A list of points that show the Convex Hull.
     */
    public static List<Point> convexHull(Point[] points) {
        // Sort the n points by x coords to make finding median easier
        Arrays.sort(points, Comparator.comparingInt(p -> p.x));

        // Use Divide and Conquer to find the Convex Hull
        return findHull(points, 0, points.length - 1);
    }
}
