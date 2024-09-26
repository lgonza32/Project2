package test;

import main.Point;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the point class.
 * Test determines if x and y coordinates are implmented correctly using
 * assertions.
 */
public class PointTest {

    /**
     * Test the construct Point to make sure x and y coordinates are created
     * correctly.
     */
    @Test
    public void testMultiplePoints() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(-5, 2);
        Point p3 = new Point(10, -3);

        assertAll("Testing multiple points",
                () -> assertEquals(0, p1.x),
                () -> assertEquals(0, p1.y),
                () -> assertEquals(-5, p2.x),
                () -> assertEquals(2, p2.y),
                () -> assertEquals(10, p3.x),
                () -> assertEquals(-3, p3.y));
    }

}
