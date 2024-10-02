package main;

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * This class implements the Convex Hull algorithm using Divide and Conquer with
 * methods to find and merge Convex Hulls on a 2D plane.
 * 
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
     * This method finds the upper tangent between two convex hulls during the merge
     * step.
     * 
     * This method begins with a starting point from each hull and iteratively
     * refines the points to find the upper tangent by moving along the hulls in the
     * appropriate direction. This helps to make sure convexity of the Convex Hull
     * has no concaving points.
     * 
     * @param leftHull   Convex Hull of the left subset of n points (left half).
     * @param rightHull  Convex Hull of the right subset of n points (right half).
     * @param indexLeft  Index of the "east" most point in the left hull.
     * @param indexRight Index of the "west" most point in the right hull.
     * @return An array containing two points of the upper tangent in the left and
     *         right hulls.
     */
    public static int[] findUpperTangent(List<Point> leftHull, List<Point> rightHull, int indexLeft, int indexRight) {
        int currentLeftIndex = indexLeft;
        int currentRightIndex = indexRight;
        boolean tangentFound = false;

        while (!tangentFound) {
            tangentFound = true;
            // Traverse counterclockwise through left hull to find upper tangent
            int nextLeftIndex = ConvexHullUtil.moveLeftCCW(leftHull, rightHull.get(currentRightIndex),
                    currentLeftIndex);
            if (nextLeftIndex != currentLeftIndex) {
                tangentFound = false; // Keep searching
                currentLeftIndex = nextLeftIndex;
            }
            // Traverse clockwise through right hull to find upper tangent
            int nextRightIndex = ConvexHullUtil.moveRightCW(rightHull, leftHull.get(currentLeftIndex),
                    currentRightIndex);
            if (nextRightIndex != currentRightIndex) {
                tangentFound = false; // Keep searching
                currentRightIndex = nextRightIndex;
            }
        }
        return new int[] { currentLeftIndex, currentRightIndex };
    }

    /**
     * This method finds the lower tangent between two convex hulls during the merge
     * step.
     * 
     * This method begins with a starting point from each hull and iteratively
     * refines the points to find the upper tangent by moving along the hulls in the
     * appropriate direction. This helps to make sure convexity of the Convex Hull
     * has no concaving points.
     * 
     * @param leftHull   Convex Hull of the left subset of n points (left half).
     * @param rightHull  Convex Hull of the right subset of n points (right half).
     * @param indexLeft  Index of the "east" most point in the left hull.
     * @param indexRight Index of the "west" most point in the right hull.
     * @return An array containing two points of the lower tangent in the left and
     *         right hulls.
     */
    public static int[] findLowerTangent(List<Point> leftHull, List<Point> rightHull, int indexLeft, int indexRight) {
        int currentLeftIndex = indexLeft;
        int currentRightIndex = indexRight;
        boolean tangentFound = false;

        while (!tangentFound) {
            tangentFound = true;
            // Traverse clockwise through the left hull to find the lower tangent
            int nextLeftIndex = ConvexHullUtil.moveLeftCW(leftHull, rightHull.get(currentRightIndex),
                    currentLeftIndex);
            if (nextLeftIndex != currentLeftIndex) {
                tangentFound = false;
                currentLeftIndex = nextLeftIndex;
            }
            // Traverse counterclockwise through the right hull to find the lower tangent
            int nextRightIndex = ConvexHullUtil.moveRightCCW(rightHull, leftHull.get(currentLeftIndex),
                    currentRightIndex);
            if (nextRightIndex != currentRightIndex) {
                tangentFound = false;
                currentRightIndex = nextRightIndex;
            }
        }
        return new int[] { currentLeftIndex, currentRightIndex };
    }

    /**
     * This method merges two halves into a single Convex Hull
     * by finding the upper and lower tangents between the two hulls.
     *
     * @param leftHull  Convex Hull of the left subset of n points (left half).
     * @param rightHull Convex Hull of the right subset of n points (right half).
     * @return A list of points representing the merged Convex Hull.
     */
    public static List<Point> mergeHulls(List<Point> leftHull, List<Point> rightHull) {
        // Initialize and call appropriate functions to find starting points
        int indexLeft = ConvexHullUtil.findEastMost(leftHull);
        int indexRight = ConvexHullUtil.findWestMost(rightHull);

        // Find upper and lower tangents
        int[] upperTangent = findUpperTangent(leftHull, rightHull, indexLeft, indexRight);
        int[] lowerTangent = findLowerTangent(leftHull, rightHull, indexLeft, indexRight);
        int upperLeft = upperTangent[0];
        int upperRight = upperTangent[1];
        int lowerLeft = lowerTangent[0];
        int lowerRight = lowerTangent[1];

        List<Point> mergedHull = new LinkedList<>(); // Hold final Convex Hull

        // Adds up all the points from the left and right hulls between tangents
        ConvexHullUtil.addPoints(mergedHull, leftHull, upperLeft, lowerLeft); // left hull
        ConvexHullUtil.addPoints(mergedHull, rightHull, lowerRight, upperRight); // right hull

        return mergedHull;
    }

    /**
     * This method recursively finds the Convex Hull for a given set of points using
     * a Divide and Conquer. This method splits the set of points, finds the Convex
     * Hull for each subset, and then merges them.
     * 
     * @param points     An array of points.
     * @param indexLeft  Index of the "east" most point in the left hull.
     * @param indexRight Index of the "west" most point in the right hull.
     * @return A list of points representing the Convex Hull of a given set P of
     *         points n.
     */
    public static List<Point> findHull(Point[] points, int indexLeft, int indexRight) {
        // If there's only one or two points, they form the hull
        // This avoids stack overflow error
        if (indexRight - indexLeft + 1 <= 2) {
            LinkedList<Point> hull = new LinkedList<>();
            for (int i = indexLeft; i <= indexLeft; i++) {
                hull.add(points[i]);
            }
            return hull;
        }

        // Find the median of the "west" most and "east" most points (divide step)
        int median = (indexLeft + indexRight) / 2;

        // Recursively find the two subset halves (conquer step)
        List<Point> leftHull = findHull(points, indexLeft, median);
        List<Point> rightHull = findHull(points, median + 1, indexRight);

        // Merge the two into one Convex Hull (merge step)
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
    public static LinkedList<Point> convexHull(Point[] points) {
        // Sort the n points by x coords to make finding median easier
        Arrays.sort(points, Comparator.comparingInt(p -> p.x));

        // Use Divide and Conquer to find the Convex Hull
        return new LinkedList<>(findHull(points, 0, points.length - 1));
    }
}
