package com.CalApp.lib;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Unit test for ExpressionParser lib.
 */
public class ExpressionParserTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ExpressionParserTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ExpressionParserTest.class );
    }

    // simple reflection helper: invokes isOperator static method with one char arg
    private static boolean invokeIsOperator(char ch) throws Exception {
        Method m = ExpressionParser.class.getDeclaredMethod("isOperator", char.class);
        m.setAccessible(true);
        return (Boolean) m.invoke(null, ch); // static method -> null instance
    }

    /**
     * Unit tests for ExpressionParser::isOperator.
     *
     * Iterates a table of (op1, op2, expected) cases and invokes the private
     * static method isOperator via reflection, asserting the returned boolean
     * matches the expected value.
     *
     * This class uses JUnit 3 (extends TestCase) and contains a single test that
     * groups multiple cases in an array for concise verification.
     */
    public void testIsOperator() throws Exception {
        Object[][] cases = new Object[][] {
            {'+', Boolean.TRUE},
            {'-', Boolean.TRUE},
            {'*', Boolean.TRUE},
            {'/', Boolean.TRUE},
            {'a', Boolean.FALSE},
            {'1', Boolean.FALSE},
            {' ', Boolean.FALSE},
            {'%', Boolean.FALSE}
        };

        for (Object[] c : cases) {
            char ch = (Character) c[0];
            boolean expected = (Boolean) c[1];
            boolean actual = invokeIsOperator(ch);
            assertEquals("isOperator(" + ch + ")", expected, actual);
        }
    }

}
