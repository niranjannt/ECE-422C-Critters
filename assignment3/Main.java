/* CRITTERS <MyClass.java>
 * ECE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Niranjan Telkikar
 * nnt479
 * Slip days used: <0>
 * Fall 2024
 */

package assignment3; // cannot be in default package
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Scanner;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */

/**
 * This sets up the output
 */
public class Main {

    static Scanner kb;    // scanner connected to keyboard input, or input file
    private static String inputFile;    // input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;    // if test specified, holds all console output
    private static String myPackage;    // package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;    // if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * The main method invokes the methods based on the commands typed in.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name,
     *             and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        Critter.clearWorld(); // Ensures the world is empty before execution
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */

        String in = "";
        //While the command entered is not quit, keep running simulation
        while (!in.equals("quit")) {
            in = kb.nextLine();//Gets a line from the keyboard
            //Split based off of whitespace
            String[] command = in.split(" "); //The line is split by spaces


            if (command[0].equals("make")) { //Checks if the first command is make
                if (command.length == 2) {
                    try {
                        Critter.makeCritter(command[1]); //makes the Critter with name if it only one
                    } catch (InvalidCritterException e) {
                        System.out.print("error processing: "); //catches InvaludCritter if Critter does not exist
                        for (String str : command) {
                            System.out.print(str + " ");
                        }
                        System.out.println();

                    }

                } else if (command.length == 3) {
                    try {
                        int count = Integer.parseInt(command[2]); //gets the number of critters if >=1
                        if (count <= 0) { //If it is less than or equal to zero, there is an error.
                            System.out.print("error processing: ");
                            for (String str : command) {
                                System.out.print(str + " ");
                            }
                            System.out.println();


                        }
                        //Makes the number of critters needed
                        for (int i = 0; i < count; i++) {
                            Critter.makeCritter(command[1]);
                        }
                        //If it is invalid critter there is an error
                    } catch (NumberFormatException | InvalidCritterException e) {
                        System.out.print("error processing: ");
                        for (String str : command) {
                            System.out.print(str + " ");
                        }
                        System.out.println();
                    }
                }
                else{
                    System.out.print("error processing: ");

                    for (String str : command) {
                        System.out.print(str + " ");
                    }
                    System.out.println();

                }
                continue;
            }
            if (command[0].equals("seed")) {
                if (command.length == 2) {
                    try {
                        int result = Integer.parseInt(command[1]);
                        Critter.setSeed(result); //This sets the seed for this using the number
                    } catch (NumberFormatException e) {
                        System.out.print("error processing: ");
                        for (String str : command) {
                            System.out.print(str + " ");
                        }
                        System.out.println();

                    }
                } else {
                    System.out.print("error processing: ");
                    for (String str : command) {
                        System.out.print(str + " ");
                    }
                    System.out.println();

                }
                continue;

            }
            if (command[0].equals("show")) {
                if (command.length == 1) {
                    Critter.displayWorld(); //displays the world with show command
                } else {
                    System.out.print("error processing: ");
                    for (String str : command) {
                        System.out.print(str + " ");
                    }
                    System.out.println();


                }
                continue;
            }
            if (command[0].equals("step")) {
                if (command.length == 1) {
                    Critter.worldTimeStep(); //if step only one step is counted
                } else if (command.length == 2) {
                    try {
                        int count = Integer.parseInt(command[1]);
                        if (count <= 0) {
                            System.out.print("error processing: ");
                            for (String str : command) {
                                System.out.print(str + " ");
                            }
                            System.out.println();

                        }
                        for (int i = 0; i < count; i++) {
                            Critter.worldTimeStep(); //otherwise worldTimeStep is done
                        }
                    } catch (NumberFormatException e) {
                        System.out.print("error processing: ");
                        for (String str : command) {
                            System.out.print(str + " ");
                        }
                        System.out.println();

                    }
                } else {
                    System.out.print("error processing: ");
                    for (String str : command) {
                        System.out.print(str + " ");
                    }
                    System.out.println();

                }
                continue;

            }
            if(command[0].equals("stats")) {
                try {
                    if (command.length == 2) {
                      String className=command[1];
                        className="assignment3."+className; //gets classname by adding assignment3. since it is imported as a package
                        try {
                            Class<?> critterClass = Class.forName(className); //gets the name of the class (ex. Craig)
                            List<Critter> crittersList=Critter.getInstances(command[1]); //gets all instances of that Class
                            java.lang.reflect.Method method = critterClass.getMethod("runStats", java.util.List.class); //gets the method runStats inside that class
                            method.invoke(null, crittersList); // Null because it's a static method
                        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException |
                                 InvocationTargetException e) {
                            List<Critter> critter=Critter.getInstances(command[1]); //If there is no method it runs normally
                            Critter.runStats(critter); //This is running normally
                        }

                    }
                    else{
                        System.out.print("error processing: ");
                        for (String str : command) {
                            System.out.print(str + " "); //Otherwise error processing is printed
                        }
                        System.out.println();

                    }

                }
                catch(InvalidCritterException e){
                    System.out.print("error processing: ");
                    for (String str : command) {
                        System.out.print(str + " "); //otherwise error is printed
                    }
                    System.out.println();

                }
                continue;
            }
            if (command[0].equals("quit") && command.length == 1) {
                break; //If it is quit the while loop breaks and then the program ends
            } else {
                System.out.print("invalid command: "); //otherwise it is an invalid command
                for (String str : command) {
                    System.out.print(str+" ");

                }
                System.out.println();

            }



            /* Write your code above */
            System.out.flush();

        }
    }
}

