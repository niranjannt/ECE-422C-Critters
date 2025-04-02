
package assignment3;

import assignment3.Critter.TestCritter;
import org.junit.*;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestStage1 {

    private static final String CLI_INTEGRATION_TESTS_INOUTS  = "422_lab4_test/test/data/cli_integration_inouts/";
    private static ByteArrayOutputStream outContent;

    @Before
    public void setUp() throws Exception {
        Params.world_width = 20;
        Params.world_height = 20;
        Params.walk_energy_cost = 2;
        Params.run_energy_cost = 5;
        Params.rest_energy_cost = 1;
        Params.min_reproduce_energy = 20;
        Params.refresh_algae_count = (int)Math.max(1, Params.world_width* Params.world_height/1000);
        Params.photosynthesis_energy_amount = 1;
        Params.start_energy = 100;

        TestCritter.clearWorld();
    }

    // Utility method.
    /**
     *  Move n steps in the specified direction.
     *  current x, current y, direction to move, steps to move
     *  @return new co-ordinates.
     */
    public static int[] moveStep(int x_coord, int y_coord, int direction, int n) {
        int w = Params.world_width; int h = Params.world_height;
        int newX = x_coord + w; int newY = y_coord + h;

        switch (direction) {
            case 0: newX += n; break;
            case 1: newX += n;
                newY -= n; break;
            case 2: newY -= n; break;
            case 3: newX -= n;
                newY -= n; break;
            case 4: newX -= n; break;
            case 5: newX -= n;
                newY += n; break;
            case 6: newY += n; break;
            case 7: newX += n;
                newY += n; break;
        }
        return new int[]{newX%w, newY%h};
    }


    /*
     * 1. ParseInvalidInput
     */
    @Test(timeout=1000)
    public void ParseInvalidInput() throws InvalidCritterException{

        //Test: ParseInvalidInput
        //Test for Invalid Inputs
        //Expects invalid command to be output

        String fileFolder = "invalid_inputs";
        String[] inputs = {CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/input.txt" ,"test"};

        Main.main(inputs);
        outContent = Main.testOutputString;

        Scanner scanner = null;
        try {
            scanner = new Scanner( new File(CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/expected_output.txt") );
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        String text = cleanString(scanner.useDelimiter("\\A").next().trim());
        String output = cleanString(outContent.toString());
        scanner.close();
        //assertThat(text, containsString(output));
        assertEquals(text,output);
    }


    @Test(timeout=1000)
    public void ParseErrors() throws InvalidCritterException{

        /*
         * Test: ParseErrors
         * Test for errors within valid inputs
         * Expects errors to be printed
         */

        String fileFolder = "error_processing";
        String[] inputs = {CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/input.txt" ,"test"};

        Main.main(inputs);
        outContent = Main.testOutputString;

        Scanner scanner = null;
        try {
            scanner = new Scanner( new File(CLI_INTEGRATION_TESTS_INOUTS + fileFolder + "/expected_output.txt") );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String text = cleanString(scanner.useDelimiter("\\A").next().trim());
        String output = cleanString(outContent.toString());
        scanner.close();
        assertEquals(text,output);
    }

    /**
     *
     * Walk in one direction.
     * @throws InvalidCritterException
     */
    @Test(timeout=1000)
    public void testWalk() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 m1 = (MyCritter1) TestCritter.getPopulation().get(0);
        int x1a = m1.getX_coord(); int y1a = m1.getY_coord();
        m1.doTimeStep();
        int x1b = m1.getX_coord(); int y1b = m1.getY_coord();
        assertTrue((x1b - x1a == 1) || (x1b + Params.world_width - x1a) == 1);
        assertTrue(Math.abs(y1b - y1a) == 0);
    }

    /**
     *
     * Walk in multiple directions, in sequence.  Check location only.
     * @throws InvalidCritterException
     */
    @Test(timeout=1000)
    public void testWalkMultiDirection() throws InvalidCritterException {
        int dir = 0;
        // Walk in all 8 directions
        for (int i = 0; i < 8; i++) {
            TestCritter.clearWorld();
            Critter.makeCritter("MyCritter1");
            MyCritter1 m1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
            int x, y;
            x = 10; y = 10;
            m1.setX_coord(x);
            m1.setY_coord(y);

            m1.walk(dir);
            int [] destination = moveStep(x, y, dir, 1);
            assertEquals(m1.getX_coord(), destination[0]);
            assertEquals(m1.getY_coord(), destination[1]);
            dir++;
        }
    }

    @Test(timeout=1000)
    /**
     * 4.
     * Stays still for every turn.  Check energy drop at every turn.
     */
    public void RestEnergyTest () throws InvalidCritterException {  // rest until death
        Params.refresh_algae_count = 0;
        Critter.makeCritter("MyCritter6");
        MyCritter6 c = (MyCritter6) Critter.TestCritter.getPopulation().get(0);
        int step = 0;
        while (c.getEnergy() > 0) {
//		while (!c.isDead()) {
            assertEquals(Params.start_energy - step * Params.rest_energy_cost, c.getEnergy());
            Critter.worldTimeStep();
            step++;
        }
    }

    @Test(timeout=1000)
    public void FightFight() throws InvalidCritterException {
        // Create two Critters that return fight/fight
        //Checks energy makes sense afterwards
        int x = 0;
        int y = 0;
        Critter.makeCritter("MyCritter7");
        Critter.makeCritter("MyCritter7");
        MyCritter7 c1 = (MyCritter7) Critter.getInstances("MyCritter7").get(0);
        MyCritter7 c2 = (MyCritter7) Critter.getInstances("MyCritter7").get(1);
        c1.setX_coord(x);
        c1.setY_coord(y);
        c2.setX_coord(x);
        c2.setY_coord(y);

        // if (DEBUG)
        // System.out.println(TestCritter.getPop());
        Critter.worldTimeStep();

        assertEquals(2, TestCritter.getPopulation().size());
        MyCritter7 winner = c1.getEnergy() <= 0 ? c2 : c1;
        MyCritter7 loser = c1.getEnergy() <= 0 ? c1 : c2;
        int afterEnergy = Params.start_energy * 3 / 2 - Params.rest_energy_cost;
        assertEquals(afterEnergy, winner.getEnergy());
    }

    @Test(timeout=1000)
    public void NoFightNoFight() throws InvalidCritterException {

        int x = 0;
        int y = 0;
        Point p = new Point(x, y);
        Critter.makeCritter("Algae");
        Critter.makeCritter("Algae");
        Algae c1 = (Algae) Critter.getInstances("Algae").get(0);
        Algae c2 = (Algae) Critter.getInstances("Algae").get(1);
        c1.setX_coord(x);
        c1.setY_coord(y);
        c2.setX_coord(x);
        c2.setY_coord(y);

        Critter.worldTimeStep();

        // assertEquals(1, TestCritter.getPop().get(p).size());
        assertEquals(2, TestCritter.getPopulation().size());
        Algae winner = c1.getEnergy() <= 0 ? c2 : c1;
        Algae loser = c1.getEnergy() <= 0 ? c1 : c2;
        // Algae winner = c1.isDead() ? c2 : c1;
        // Algae loser = c1.isDead() ? c1 : c2;
        int afterEnergy = (Params.start_energy + Params.photosynthesis_energy_amount) * 3 / 2 - Params.rest_energy_cost;
        assertEquals(afterEnergy, winner.getEnergy());
    }

    @Test(timeout=1000)
    /**
     * 14. Check Algae generation.
     */
    public void AlgaeGenerationTest() throws InvalidCritterException {
        // Make sure that the rate of Algae generation is correct, by counting
        // Critters
        // after each time step.
        Params.refresh_algae_count = 3;
        Params.rest_energy_cost = 0;
        int numAlgaeExpected = Critter.getInstances("Algae").size() + Params.refresh_algae_count;
        Critter.worldTimeStep();
        int numAlgaeActual = Critter.getInstances("Algae").size();
        assertEquals(numAlgaeExpected, numAlgaeActual);
    }

    @Test(timeout=1000)
    /**
     * Checks that babies are not immediately added to the population and that
     * the babies move in the right direction
     */
    public void ReproduceTest() throws InvalidCritterException {
        int x = 0, y = 0, dir = 5;
        Critter.makeCritter("MyCritter7");
        MyCritter7 parent = (MyCritter7) Critter.getInstances("MyCritter7").get(0);
        parent.setX_coord(x);
        parent.setY_coord(y);

        MyCritter7 offspring = new MyCritter7();
        parent.reproduce(offspring, dir);

        assertEquals(1, Critter.getInstances("MyCritter7").size());
        assertTrue(TestCritter.getBabies().contains(offspring));

        int[] childXY = moveStep(x, y, dir, 1);
        assertEquals(childXY[0], offspring.getX_coord());
        assertEquals(childXY[1], offspring.getY_coord());
    }

    @Test(timeout=1000)
    /**
     * Checks that the babies are added to the population by the end of the time
     * step
     */
    public void ReproduceTestTimeStep() throws InvalidCritterException {
        int x = 0, y = 0;
        Critter.worldTimeStep();
        TestCritter.clearWorld();
        Critter.makeCritter("MyCritter7");
        MyCritter7 parent = (MyCritter7) Critter.getInstances("MyCritter7").get(0);
        parent.setX_coord(x);
        parent.setY_coord(y);

        Critter.worldTimeStep();

        // assertEquals(2, Critter.getInstances("MyCritter7").size());
        assertEquals(2, TestCritter.getPopulation().size());
        assertTrue(TestCritter.getBabies().isEmpty());

    }


    String cleanString(String input) {
        String lineSep = System.getProperty("line.separator");
        input = input.replaceAll("critter>", "")
                .replaceAll("\r\n", "\n")
                .replaceAll("critters>", "")
                .replaceAll(lineSep + "\\s+", "\n")
                .replaceAll(lineSep, "\n")
                .replaceAll("(?m)^[ \t]*\r?\n", "") // Remove empty lines
                .replaceAll("(?m)^\\s+", "") // Remove leading spaces/tabs from each line
                .replaceAll("(?m)\\s+$", "") // Remove trailing spaces/tabs from each line
                .trim();
        return input;
    }
}
