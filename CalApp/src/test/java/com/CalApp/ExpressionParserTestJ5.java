package com.CalApp.lib;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;

/**
 * Unit test for ExpressionParser lib.
 */
class ExpressionParserTestJ5
{
    // simple reflection helper: invokes a private static method with one char arg
    @SuppressWarnings("unchecked")
    private static <R> R invokeStaticByName(String methodName, Object... args) throws Exception {
        Class<?> cls = ExpressionParser.class;
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
        try {
            Object ret = found.invoke(null, args);
            return (R) ret;
        } catch (InvocationTargetException ite) {
            // Method threw an exception — forward the underlying cause to the caller/test
            Throwable cause = ite.getTargetException();
            if (cause instanceof Exception) throw (Exception) cause;
            // if it's an error or non-Exception throwable, wrap or rethrow as appropriate
            throw new RuntimeException("Underlying method threw an error", cause);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            // Reflection-level problems — forward to caller/test
            throw e;
        }
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
    @Test
    void testIsOperator() throws Exception {
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
            boolean actual = invokeStaticByName("isOperator",ch);
            assertEquals("isOperator(" + ch + ")", expected == actual);
        }
    }

    /**
     * Unit tests for ExpressionParser::hasPrecedence.
     *
     * Iterates a table of (op1, op2, expected) cases and invokes the private
     * static method hasPrecedence via reflection, asserting the returned boolean
     * matches the expected value.
     *
     * This class uses JUnit 3 (extends TestCase) and contains a single test that
     * groups multiple cases in an array for concise verification.
     */
    @Test
    void testHasPrecedence() throws Exception {
        // array of inputs and expected results: {op1, op2, expected}
        Object[][] cases = new Object[][] {
            {'+', '(', Boolean.FALSE},
            {'*', ')', Boolean.FALSE},
            {'*', '+', Boolean.FALSE},
            {'/', '-', Boolean.FALSE},
            {'+', '*', Boolean.TRUE},
            {'-', '/', Boolean.TRUE},
            {'+', '-', Boolean.TRUE},
            {'*', '/', Boolean.TRUE}
        };

        for (Object[] c : cases) {
            char op1 = (Character) c[0];
            char op2 = (Character) c[1];
            boolean expected = (Boolean) c[2];
            boolean actual = invokeStaticByName("hasPrecedence",op1, op2);
            assertEquals("hasPrecedence(" + op1 + "," + op2 + ")", expected == actual);
        }
    }

    /**
     * Unit tests for ExpressionParser::applyOperation.
     *
     * Iterates a table of (op1, op2, expected) cases and invokes the private
     * static method applyOperation via reflection, asserting the returned boolean
     * matches the expected value.
     *
     * This class uses JUnit 3 (extends TestCase) and contains a single test that
     * groups multiple cases in an array for concise verification.
     */
    @Test
    void testApplyOperationTable() throws Exception {
        // table: {operator, b, a, expectedDouble (nullable), expectedException (String or null)}
        Object[][] cases = new Object[][] {
            {'+', 2.0, 3.0, 5.0, null},
            {'-', 2.0, 3.0, 1.0, null},
            {'*', 2.0, 3.0, 6.0, null},
            {'/', 2.0, 3.0, 1.5, null},
            {'/', 0.0, 3.0, null, "Cannot divide by zero"},
            {'%', 2.0, 3.0, null, "Invalid operator"}
        };

        for (Object[] c : cases) {
            char op = (Character) c[0];
            double b = (Double) c[1];
            double a = (Double) c[2];
            Double expected = (Double) c[3];
            String expectedErr = (String) c[4];

            if (expectedErr == null) {
                double actual = invokeStaticByName("applyOperation",op, b, a);
                assertEquals(expected.doubleValue(),
                             actual,
                             1e-9,
                             () -> "applyOperation(" + op + "," + b + "," + a + ")");
            } else {
                try {
                    invokeStaticByName("applyOperation",op, b, a);
                    fail("Expected UnsupportedOperationException for op=" + op);
                } catch (UnsupportedOperationException e) {
                    assertTrue(e.getMessage().contains(expectedErr),
                        ()->"Exception message should contain expected text");
                }
            }
        }
    }

    // test-only stub that overrides helper methods
    static class StubExpressionParser extends ExpressionParser {
    }

    @Test
    void testEvaluate(){
        StubExpressionParser parser = new StubExpressionParser();
        double result = parser.evaluate("2 + 3 * 4"); // uses overridden helpers
        assertEquals(14.0, result, 1e-9, () -> "expected 14.0 but was " + result);
    }
}
