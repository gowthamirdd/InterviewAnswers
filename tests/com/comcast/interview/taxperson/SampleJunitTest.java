package com.comcast.interview;

import org.junit.Before;
import org.junit.Test;


public class SampleJunitTest {

    public CalculateTax calculateTax;

    @Before
    public void setup() throws Exception{
        calculateTax = new CalculateTax();
    }

    @Test
    public void necessaryItemTest() throws RuntimeException {
        System.out.println(calculateTax.calculate(100,false));
    }

    @Test
    public void luxuryItemTest() throws RuntimeException {
        System.out.println(calculateTax.calculate(100,true));
    }
    
    @Test
    public void zeroTest() throws RuntimeException{
        System.out.println(calculateTax.calculate(0,true));
    }
    
    @Test
    public void lessThanZeroTest() throws RuntimeException{
        System.out.println(calculateTax.calculate(-1000,true));
    }

}