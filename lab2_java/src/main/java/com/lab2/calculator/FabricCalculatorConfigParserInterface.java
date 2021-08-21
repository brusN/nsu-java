package com.lab2.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

interface FabricCalculatorConfigParserInterface {
    static HashMap<String, String> getCalculatorCommandConfig(FileInputStream configFile) {
        Logger logger = LogManager.getLogger();
        HashMap<String, String> calculatorCommandConfig = new HashMap<>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(configFile))) {
            String fileLine = fileReader.readLine();
            while (fileLine != null) {

                // First part is syntax calculator command
                // Second part is name calculator command implementation class
                if (fileLine.isEmpty())
                    continue;

                // CSV format
                String[] lineParts = fileLine.split(",");
                if (calculatorCommandConfig.containsKey(lineParts[0])) {
                    throw new Exception("Multiple command definition!");
                }
                calculatorCommandConfig.put(lineParts[0], lineParts[1]);
                fileLine = fileReader.readLine();
            }
        } catch (Exception exception) {
            logger.error("Error while reading fabric config file: " + exception.getLocalizedMessage());
        }
        return calculatorCommandConfig;


    }

    /*
    Properties properties;

    try {
        InputStream is = FabricCalculatorConfigParser.class.getResourceAsStream("config");
        properties.load(is);
    } catch(IOException e) {

    }
    */

    static void printCalculatorCommandConfig(HashMap<String, String> map) {
        for (HashMap.Entry<String, String> entry : map.entrySet()) {
            System.out.println("Syntax: '" + entry.getKey() + "', classname: '" + entry.getValue() + "'");
        }
    }
}
