package com.eliassen.crucible.core.tests;

import com.eliassen.crucible.core.helpers.RandomNumbers;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class RandomNumbersTests
{
    @Test
    public void randomNumbersFromOneToMax_shouldReturnIntFromOneToMaxInclusive()
    {
        int max = 1;
        int expected = 1;
        int result = RandomNumbers.randomNumberFromOneToMax(max);
        Assertions.assertEquals(expected, result);
    }
}
