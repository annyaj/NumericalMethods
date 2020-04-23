package ru.omsu.imit.numericalMethods.task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RichardsonMethodTest {

    @Test
    void orderingParameters1() {
        int[] exp = {1, 8, 4, 5, 2, 7, 3, 6};
        assertArrayEquals(exp, RichardsonMethod.orderingParameters(8));
    }

    @Test
    void orderingParameters2() {
        int[] exp = {1, 16, 8, 9, 4, 13, 5, 12, 2, 15, 7, 10, 3, 14, 6, 11};
        assertArrayEquals(exp, RichardsonMethod.orderingParameters(16));
    }
}