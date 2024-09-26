package main;

import java.util.List;

/**
 * Implements the Convex Hull algorithm using divide and conquer.
 * This class provides methods to find and merge Convex Hulls on a 2D plane.
 * The scheme behind Convex Hull, Divide and Conquer for a set P of n points:
 * <ul>
 * <li>Sort n points by x coordinates (n log n).
 * <li>Divide those set of points into two using a median O(1).
 * <li>Find Convex Hulls of each half recursively O(n log n).
 * <li>Merge the two hulls; O(n)
 * </ul>
 * The Overall Time Complexity should be: O(n log n).
 */
public class ConvexHull {
    
    /**
     * Find the "east" most point (the point with the largest x value) in the hull .
     * 
     * @param hull A list of points from a Convex Hull.
     * @return The index of the "east" most point in the hull.
     */
    private static int findEastMost(List<Point> hull) {
        int index = 0;
        for(int i = 1; i < hull.size(); i++) {
            if(hull.get(i).x > hull.get(index).x) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Find the "west" most point (the point with the smallest x value) in the hull .
     * 
     * @param hull A list of points from a Convex Hull.
     * @return The index of the "west" most point in the hull.
     */
    private static int findWestMost(List<Point> hull) {
        int index = 0;
        for(int i = 1; i < hull.size(); i++) {
            if(hull.get(i).x < hull.get(index).x) {
                index = i;
            }
        }
        return index;
    }
}
