package com.lab2.calculator;

import com.lab2.calculator.commands.CalculatorCommand;
import java.lang.reflect.InvocationTargetException;

public class CalculatorCommandFabric implements CalculatorCommandFabricInterface {
    public CalculatorCommand createNewMathOperation(String className) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        var newObject = Class.forName("com.lab2.calculator.commands." + className);
        return (CalculatorCommand) newObject.getDeclaredConstructor().newInstance();
    }
}
