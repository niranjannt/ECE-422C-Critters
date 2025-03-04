package assignment3;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

public class RunSpecificTestMethod {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java RunSpecificTestMethod <TestClassName> <TestMethodName>");
            System.exit(1);
        }

        String testClassName = args[0];
        String testMethodName = args[1];

        try {
            Class<?> testClass = Class.forName(testClassName);
            Request request = Request.method(testClass, testMethodName);
            JUnitCore junit = new JUnitCore();
            Result result = junit.run(request);

            if (result.wasSuccessful()) {
                System.out.println("Test passed.");
                System.exit(0);
            } else {
                System.out.println("Test failed.");
                System.exit(1);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Test class not found: " + testClassName);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
