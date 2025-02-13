package com.automation.utils;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CSVReader {

    // Data provider that reads data from a CSV file specific to the test method
    @DataProvider(name = "csvDataProvider")
    public static Object[][] csvDataProvider(Method method) {
        String testClassName = method.getDeclaringClass().getSimpleName();
        String testName = method.getName();
        List<Object[]> data = new ArrayList<>();
        // Construct the path to the CSV file (assumed to be in the same package as the test class)
        String csvFile = "./src/test/java/com/automation/tests/" + testClassName + ".csv";

        log.info("Reading data for test: {}", testName);
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split CSV line by comma and match it with the test method name
                String[] values = line.split(",");
                if (values[0].equalsIgnoreCase(testName)) {
                    Object[] params = new Object[values.length - 1];
                    for (int i = 1; i < values.length; i++) {
                        params[i - 1] = parseValue(values[i]);
                    }
                    data.add(params);
                }
            }
        } catch (IOException e) {
            log.error("Error reading CSV file {}: {}", csvFile, e.getMessage());
        }

        return data.toArray(new Object[0][0]);
    }

    // Helper method to handle value parsing based on type (String, Integer, Double).
    private static Object parseValue(String value) {
        value = value.trim();

        // If the value is wrapped in quotes, treat it as a string
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1); // Remove quotes for string values
        }

        // Attempt to parse the value as an Integer
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e1) {
            // Attempt to parse the value as a Double
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e2) {
                // If it's neither an Integer nor a Double, return the value as a String
                log.warn("Unable to parse value: {}", value);
                return value;
            }
        }
    }
}
