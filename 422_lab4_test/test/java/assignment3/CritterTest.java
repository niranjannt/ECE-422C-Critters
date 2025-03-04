package assignment3;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class CritterTest {

    @Before
    public void setUp() {
        Critter.clearWorld();
    }

    @Test
    public void testWalk() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        Critter.TestCritter testCritter = (Critter.TestCritter) Critter.TestCritter.getPopulation().get(0);
        int x1 = testCritter.getX_coord();
        int y1 = testCritter.getY_coord();
        testCritter.walk(0);
        int x2 = testCritter.getX_coord();
        int y2 = testCritter.getY_coord();
        assertTrue(x2 == (x1 + 1) % Params.world_width);
        assertEquals(y1, y2);
    }

    @Test
    public void testRun() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        Critter.TestCritter testCritter = (Critter.TestCritter) Critter.TestCritter.getPopulation().get(0);
        int x1 = testCritter.getX_coord();
        int y1 = testCritter.getY_coord();
        testCritter.run(0);
        int x2 = testCritter.getX_coord();
        int y2 = testCritter.getY_coord();
        assertTrue(x2 == (x1 + 2) % Params.world_width);
        assertEquals(y1, y2);
    }

    @Test
    public void testReproduce() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        Critter.TestCritter parent = (Critter.TestCritter) Critter.TestCritter.getPopulation().get(0);
        parent.setEnergy(Params.min_reproduce_energy + 10);
        MyCritter1 offspring = new MyCritter1();
        parent.reproduce(offspring, 0);
        assertEquals(1, Critter.TestCritter.getBabies().size());
    }

    @Test
    public void testFightMechanics() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        Critter.makeCritter("MyCritter6");
        Critter.TestCritter c1 = (Critter.TestCritter) Critter.TestCritter.getPopulation().get(0);
        Critter.TestCritter c2 = (Critter.TestCritter) Critter.TestCritter.getPopulation().get(1);
        c1.setX_coord(5);
        c1.setY_coord(5);
        c2.setX_coord(5);
        c2.setY_coord(5);
        c1.setEnergy(20);
        c2.setEnergy(10);
        Critter.worldTimeStep();
        assertTrue(c1.getEnergy() > 0 || c2.getEnergy() > 0);
    }

    @Test
    public void testClearWorld() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        Critter.clearWorld();
        assertEquals(0, Critter.TestCritter.getPopulation().size());
    }

    @Test
    public void testAlgaeSpawning() {
        int initialAlgae = (int) Critter.TestCritter.getPopulation().stream().filter(c -> c instanceof Algae).count();
        Critter.worldTimeStep();
        int newAlgae = (int) Critter.TestCritter.getPopulation().stream().filter(c -> c instanceof Algae).count();
        assertEquals(initialAlgae + Params.refresh_algae_count, newAlgae);
    }

    @Test
    public void testBabiesAddedToPopulation() throws InvalidCritterException {
        Critter.makeCritter("MyCritter7");
        Critter.TestCritter parent = (Critter.TestCritter) Critter.TestCritter.getPopulation().get(0);
        parent.setEnergy(Params.min_reproduce_energy + 10);
        MyCritter7 offspring = new MyCritter7();
        parent.reproduce(offspring, 0);
        int initialPopulation = Critter.TestCritter.getPopulation().size();
        Critter.worldTimeStep();
        assertEquals(initialPopulation + 1, Critter.TestCritter.getPopulation().size());
    }

    @Test
    public void testDeadCrittersRemoved() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        Critter.TestCritter c1 = (Critter.TestCritter) Critter.TestCritter.getPopulation().get(0);
        c1.setEnergy(1);
        Critter.worldTimeStep();
        assertEquals(0, Critter.TestCritter.getPopulation().size());
    }
}
