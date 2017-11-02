package ru.skurko.sandbox.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.sandbox.Square;

public class SquareTests {

    @Test

    public void testArea() {
        Square squareTest = new Square(5);
        Assert.assertEquals(squareTest.area(), 25.0);
    }
}
