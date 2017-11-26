package ru.skurko.sandbox.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.skurko.sandbox.Primes;

public class PrimeTests {
    @Test
    public void testPrime() {
        Assert.assertTrue(Primes.isPrime(Integer.MAX_VALUE)); //вводим простое число
    }

    @Test
    public void testPrimeFast() {
        Assert.assertTrue(Primes.isPrimeFast(Integer.MAX_VALUE)); //вводим простое число
    }

    @Test
    public void testNonPrime() {
        Assert.assertFalse(Primes.isPrime(Integer.MAX_VALUE - 2)); // вводим не простое число
    }

    @Test(enabled = false)
    public void testPrimeLong() {
        long n = Integer.MAX_VALUE;
        Assert.assertTrue(Primes.isPrime(n)); //вводим простое число
    }

}
