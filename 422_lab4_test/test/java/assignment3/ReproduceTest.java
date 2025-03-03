package assignment3;

import static org.junit.Assert.*;
import org.junit.Test;

public class ReproduceTest {

    private void testReproduceInDirection(int direction, int expectedDx, int expectedDy) throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 parent = (MyCritter1) Critter.TestCritter.getPopulation().get(0);

        // Set a fixed energy to ensure reproduction is possible
        int initialEnergy = Params.min_reproduce_energy + 10;
        parent.setEnergy(initialEnergy);

        // Reproduce in the given direction
        MyCritter1 offspring = new MyCritter1();
        parent.reproduce(offspring, direction);

        // Offspring should be added to babies list
        assertEquals(1, Critter.TestCritter.getBabies().size());

        // Fetch offspring
        MyCritter1 newBorn = (MyCritter1) Critter.TestCritter.getBabies().get(0);

        // Check energy changes
        int expectedParentEnergy = (int) Math.ceil(initialEnergy / 2.0);
        int expectedOffspringEnergy = initialEnergy / 2;

        assertEquals(expectedParentEnergy, parent.getEnergy());
        assertEquals(expectedOffspringEnergy, newBorn.getEnergy());

        // Check offspring placement with toroidal wrapping
        int expectedX = (parent.getX_coord() + expectedDx + Params.world_width) % Params.world_width;
        int expectedY = (parent.getY_coord() + expectedDy + Params.world_height) % Params.world_height;

        assertEquals(expectedX, newBorn.getX_coord());
        assertEquals(expectedY, newBorn.getY_coord());
    }

    @Test
    public void TestReproduceRight() throws InvalidCritterException {
        testReproduceInDirection(0, 1, 0);
    }

    @Test
    public void TestReproduceUpRight() throws InvalidCritterException {
        testReproduceInDirection(1, 1, -1);
    }

    @Test
    public void TestReproduceUp() throws InvalidCritterException {
        testReproduceInDirection(2, 0, -1);
    }

    @Test
    public void TestReproduceUpLeft() throws InvalidCritterException {
        testReproduceInDirection(3, -1, -1);
    }

    @Test
    public void TestReproduceLeft() throws InvalidCritterException {
        testReproduceInDirection(4, -1, 0);
    }

    @Test
    public void TestReproduceDownLeft() throws InvalidCritterException {
        testReproduceInDirection(5, -1, 1);
    }

    @Test
    public void TestReproduceDown() throws InvalidCritterException {
        testReproduceInDirection(6, 0, 1);
    }

    @Test
    public void TestReproduceDownRight() throws InvalidCritterException {
        testReproduceInDirection(7, 1, 1);
    }
}

