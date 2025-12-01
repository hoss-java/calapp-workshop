package com.CalApp;

import com.CalApp.lib.ExpressionParser;

/**
 * @file CalApp.java
 * @brief Interactive console calculator application.
 *
 * This class provides the program entry point for the calculator application.
 * It delegates actual parsing and evaluation to {@link com.CalApp.lib.ExpressionParser}.
 *
 * Features:
 * - Reads mathematical expressions from standard input.
 * - Supports addition, subtraction, multiplication, division and parentheses
 *   (handled by ExpressionParser).
 * - Prints evaluated results to standard output.
 * - Typing "exit" stops the interactive loop (behavior implemented by
 *   ExpressionParser.cal()).
 */
public class CalApp {

    /**
     * @brief Program entry point.
     *
     * Delegates to {@link com.CalApp.lib.ExpressionParser#cal(String[])} to run
     * the interactive calculator loop.
     *
     * @param args Command-line arguments (ignored by this application).
     */
    public static void main(String[] args) {
        ExpressionParser.cal(args);
    }
}
