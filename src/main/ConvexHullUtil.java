package main;

import java.util.List;

/**
 * Helper class for ConvexHull.java.
 * This class contantains helper methods that do specific operations to help
 * compute the Convex Hull.
 */
public class ConvexHullUtil {

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

}
