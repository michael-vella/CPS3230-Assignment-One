package org.example;

import org.junit.jupiter.api.AfterEach;
import org.example.Utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UtilsTests {

    Utils utils;
    @BeforeEach
    public void setup(){
        utils = new Utils();
    }

    @AfterEach
    public void teardown(){
        utils = null;
    }

    @Test
    public void testParsePriceWithBothCommaAndDot(){
        // Exercise
        int result = utils.parsePrice("£5,000.00");

        // Verify
        Assertions.assertEquals(500000, result);
    }

    @Test
    public void testParsePriceWithOnlyComma(){
        // Exercise
        int result = utils.parsePrice("$4,000");

        // Verify
        Assertions.assertEquals(400000, result);
    }

    @Test
    public void testParsePriceWithOnlyDot(){
        // Exercise
        int result = utils.parsePrice("4000.00");

        // Verify
        Assertions.assertEquals(400000, result);
    }

    @Test
    public void testNParsePriceWithOnlyNumbers(){
        // Exercise
        int result = utils.parsePrice("£400");

        // Verify
        Assertions.assertEquals(40000, result);
    }
}
