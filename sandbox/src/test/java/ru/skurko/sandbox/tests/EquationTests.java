package ru.skurko.sandbox.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.sandbox.Equation;

public class EquationTests {

    @Test
    public void test0() {
//        решений нет
        Equation e = new Equation(1, 1, 1);
        Assert.assertEquals(e.rootNumber(), 0);
    }

    @Test
    public void test1() {
//        одно решение
        Equation e = new Equation(1, 2, 1);
        Assert.assertEquals(e.rootNumber(), 1);
    }

    @Test
    public void test2() {
//        два решения
        Equation e = new Equation(1, 5, 6);
        Assert.assertEquals(e.rootNumber(), 2);
    }

    @Test
    public void testLinean() {
//        линейное уравнение
        Equation e = new Equation(0, 1, 1);
        Assert.assertEquals(e.rootNumber(), 1);
    }

    @Test
    public void testConstanta() {
//        вырождается в константу
        Equation e = new Equation(0, 0, 1);
        Assert.assertEquals(e.rootNumber(), 0);
    }

    @Test
    public void testZero() {
//        все нули
        Equation e = new Equation(0, 0, 0);
        Assert.assertEquals(e.rootNumber(), -1);
    }
}