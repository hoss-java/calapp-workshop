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

    // simple reflection helper: invokes a private static method with one char arg
    @SuppressWarnings("unchecked")
    private static <R> R invokeStaticByName(String className, String methodName, Object... args) throws Exception {
        Class<?> cls = Class.forName(className);
        Method found = null;
        int argCount = args == null ? 0 : args.length;

        for (Method m : cls.getDeclaredMethods()) {
            if (!m.getName().equals(methodName)) continue;
            if (!Modifier.isStatic(m.getModifiers())) continue;
            if (m.getParameterTypes().length != argCount) continue;
            found = m;
            break;
        }

        if (found == null) {
            throw new NoSuchMethodException("No matching static method '" + methodName + "' with " + argCount + " parameters found on " + cls.getName());
        }

        found.setAccessible(true);
        Object ret = found.invoke(null, args); // may throw IllegalArgumentException if args incompatible
        return (R) ret;
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
            boolean actual = invokeStaticByName("ExpressionParser","isOperator",ch);
            assertEquals("isOperator(" + ch + ")", expected, actual);
        }
    }

}
