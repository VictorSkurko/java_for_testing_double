package ru.skurko.sandbox.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.sandbox.Rectangle;

public class RectangleTests {

    @Test

    public void rectangleTests() {
        Rectangle rectangleTest = new Rectangle(10,10);
        Assert.assertEquals(rectangleTest.area(), 100.0);
    }
}