package main;

import java.util.ArrayList;
import java.util.LinkedList;
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
     * for a given hull. If there are multiple points with the same x value, the one
     * with the largest y value is returned.
     * 
     * @param hull A list of points from a Convex Hull.
     * @return The index of the "east" most point in the hull.
     * @throws IllegalArgumentException if hull is null or empty
     */
    public static int findEastMost(List<Point> hull) {
        if (hull == null || hull.isEmpty()) {
            throw new IllegalArgumentException("The hull cannot be null or empty.");
        }

        int index = 0;
        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(i).x > hull.get(index).x ||
                    (hull.get(i).x == hull.get(index).x && hull.get(i).y > hull.get(index).y)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Method that finds the "west" most point (the point with the smallest x value)
     * for a given hull. If there are multiple points with the same x value, the one
     * with the largest y value is returned.
     * 
     * @param hull A list of points from a Convex Hull.
     * @return The index of the "west" most point in the hull.
     * @throws IllegalArgumentException if hull is null or empty
     */
    public static int findWestMost(List<Point> hull) {
        if (hull == null || hull.isEmpty()) {
            throw new IllegalArgumentException("The hull cannot be null or empty.");
        }

        int index = 0;
        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(i).x < hull.get(index).x ||
                    (hull.get(i).x == hull.get(index).x && hull.get(i).y < hull.get(index).y)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * This method is used to determine whether the sequence of points a -> b -> c
     * makes a counterclockwise turn, a clockwise turn, or if they are collinear on
     * a 2D plane. This helps ensure that the convex shape is still a polygon and
     * connections to the points do not concave.
     * 
     * @param a First point
     * @param b Second point
     * @param c Third point
     * @return Positive - if counterclockwise turn.
     *         Negative - if clockwise turn.
     *         Zero - if collinear.
     */
    public static int checkCCW(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    /**
     * This method moves to the next point in the left hull in a counterclockwise
     * direction.
     *
     * @param leftHull       Convex Hull of the left subset of n points (left half).
     * @param rightHullPoint A point in the right hull.
     * @param currentIndex   Current index in the left hull.
     * @return The updated index in the left hull.
     */
    public static int moveLeftCCW(List<Point> leftHull, Point rightHullPoint, int currentIndex) {
        int nLeft = leftHull.size();
        while (checkCCW(rightHullPoint, leftHull.get(currentIndex),
                leftHull.get((currentIndex + 1) % nLeft)) > 0) {
            currentIndex = (currentIndex + 1) % nLeft; // Move counterclockwise
        }
        return currentIndex;
    }

    /**
     * This method moves to the next point in the right hull in a counterclockwise
     * direction.
     *
     * @param rightHull     Convex Hull of the right subset of n points (right
     *                      half).
     * @param leftHullPoint A point in the left hull.
     * @param currentIndex  Current index in the right hull.
     * @return The updated index in the right hull.
     */
    public static int moveRightCCW(List<Point> rightHull, Point leftHullPoint, int currentIndex) {
        int nRight = rightHull.size();
        while (checkCCW(leftHullPoint, rightHull.get(currentIndex),
                rightHull.get((currentIndex + 1) % nRight)) > 0) {
            currentIndex = (currentIndex + 1) % nRight; // Move counterclockwise
        }
        return currentIndex;
    }

    /**
     * This method moves to the previous point in the left hull in a clockwise
     * direction.
     *
     * @param leftHull       Convex Hull of the left subset of n points (left half).
     * @param rightHullPoint A point in the right hull.
     * @param currentIndex   Current index in the left hull.
     * @return The updated index in the left hull.
     */
    public static int moveLeftCW(List<Point> leftHull, Point rightHullPoint, int currentIndex) {
        int nLeft = leftHull.size();
        while (checkCCW(rightHullPoint, leftHull.get(currentIndex),
                leftHull.get((currentIndex - 1 + nLeft) % nLeft)) < 0) {
            currentIndex = (currentIndex - 1 + nLeft) % nLeft; // Move clockwise
        }
        return currentIndex;
    }

    /**
     * This method moves to the previous point in the left hull in a clockwise
     * direction.
     *
     * @param rightHull     Convex Hull of the right subset of n points (right
     *                      half).
     * @param leftHullPoint A point in the left hull.
     * @param currentIndex  Current index in the left hull.
     * @return The updated index in the left hull.
     */
    public static int moveRightCW(List<Point> rightHull, Point leftHullPoint, int currentIndex) {
        int nRight = rightHull.size();
        while (checkCCW(leftHullPoint, rightHull.get(currentIndex),
                rightHull.get((currentIndex - 1 + nRight) % nRight)) < 0) {
            currentIndex = (currentIndex - 1 + nRight) % nRight; // Move clockwise
        }
        return currentIndex;
    }

    /**
     * This method finds the upper tangent between two convex hulls (leftHull and
     * rightHull) during the merge step. The upper tangent is defined as the line
     * segment that touches both hulls without intersecting their interiors, and
     * lies above all other points in both hulls.
     * 
     * This method begins with a starting point from each hull and iteratively
     * refines the points to find the upper tangent by moving along the hulls in the
     * appropriate direction (counterclockwise for the left hull, clockwise for the
     * right hull).
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
            int updatedLeftIndex = moveLeftCCW(leftHull, rightHull.get(currentRightIndex), currentLeftIndex);
            if (updatedLeftIndex != currentLeftIndex) {
                tangentFound = false; // Keep searching
                currentLeftIndex = updatedLeftIndex;
            }
            // Traverse clockwise through right hull to find upper tangent
            int updatedRightIndex = moveRightCW(rightHull, leftHull.get(currentLeftIndex), currentRightIndex);
            if (updatedRightIndex != currentRightIndex) {
                tangentFound = false; // Keep searching
                currentRightIndex = updatedRightIndex;
            }
        }
        return new int[] { currentLeftIndex, currentRightIndex };
    }

    /**
     * This method finds the lower tangent between two convex hulls (leftHull and
     * rightHull) during the merge step. The lower tangent is defined as the line
     * segment that touches both hulls without intersecting their interiors, and
     * lies below all other points in both hulls.
     * 
     * This method begins with a starting point from each hull and iteratively
     * refines the points to find the upper tangent by moving along the hulls in the
     * appropriate direction (clockwise for the left hull, counterclockwise for the
     * right hull). This helps to make sure convexity of the Convex Hull has no .
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
            int updatedLeftIndex = moveLeftCW(leftHull, rightHull.get(currentRightIndex),
                    currentLeftIndex);
            if (updatedLeftIndex != currentLeftIndex) {
                tangentFound = false;
                currentLeftIndex = updatedLeftIndex;
            }
            // Traverse counterclockwise through the right hull to find the lower tangent
            int updatedRightIndex = moveRightCCW(rightHull, leftHull.get(currentLeftIndex),
                    currentRightIndex);
            if (updatedRightIndex != currentRightIndex) {
                tangentFound = false;
                currentRightIndex = updatedRightIndex;
            }
        }
        return new int[] { currentLeftIndex, currentRightIndex };
    }

    /**
     * Method that adds points from a given hull between two tangents to the merged
     * hull list.
     *
     * @param mergedHull List to which points will be added to the merged convex
     *                   hull.
     * @param hull       Hull from which points will be added.
     * @param startIndex Index to start adding points from.
     * @param endIndex   Index to stop adding points at.
     */
    public static void addPoints(List<Point> mergedHull, List<Point> hull, int startIndex, int endIndex) {
        int n = hull.size();
        int currentIndex = startIndex;

        // Traverse through the hull, adding points until reaching the endIndex
        while (true) {
            mergedHull.add(hull.get(currentIndex));
            if (currentIndex == endIndex) {
                break; // Stop when the end index is reached
            }
            currentIndex = (currentIndex + 1) % n; // Move to the next point
        }
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
        int indexLeft = findEastMost(leftHull);
        int indexRight = findWestMost(rightHull);

        // Find upper and lower tangents
        int[] upperTangent = findUpperTangent(leftHull, rightHull, indexLeft, indexRight);
        int[] lowerTangent = findLowerTangent(leftHull, rightHull, indexLeft, indexRight);
        int upperLeft = upperTangent[0];
        int upperRight = upperTangent[1];
        int lowerLeft = lowerTangent[0];
        int lowerRight = lowerTangent[1];

        List<Point> mergedHull = new LinkedList<>(); // Hold final Convex Hull

        // Adds up all the points from the left and right hulls between tangents
        addPoints(mergedHull, leftHull, upperLeft, lowerLeft); // left hull
        addPoints(mergedHull, rightHull, lowerRight, upperRight); // right hull

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
            LinkedList<Point> hull = new LinkedList<>();
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
