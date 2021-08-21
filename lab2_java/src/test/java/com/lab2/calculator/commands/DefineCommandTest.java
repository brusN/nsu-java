package com.lab2.calculator.commands;

import com.lab2.calculator.exceptions.CalculatorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Stack;

class DefineCommandTest {
    private DefineCommand testClass = new DefineCommand();
    private Stack<Double> stack = TestObjectsProvider.provideEmptyStack();
    private HashMap<String, Double> variables = new HashMap<>();

    @Test
    void areArgumentsCorrect() {
        Assertions.assertThrows(CalculatorException.class, () -> testClass.areArgumentsCorrect("1", "2"), "");
    }

    @Test
    void doWork() {

    }
}