package assignment3;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class MovementTest {

    @Before
    public void setup(){
    Critter.clearWorld();
    }

    @Test
    public void TestRight() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.walk(0);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(x2 - x1 == 1 || x2 + Params.world_width - x1 == 1);
        assertTrue(y1 == y2);
    }

    @Test
    public void TestUpRight() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.walk(1);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(x2 - x1 == 1 || x2 + Params.world_width - x1 == 1);
        assertTrue(y2 - y1 == -1 || y2 + Params.world_height - y1 == -1);
    }

    @Test
    public void TestUp() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.walk(2);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(y2 - y1 == -1 || y2 + Params.world_height - y1 == -1);
        assertTrue(x1 == x2);
    }

    @Test
    public void TestUpLeft() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.walk(3);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(x2 - x1 == -1 || x2 - Params.world_width - x1 == -1);
        assertTrue(y2 - y1 == -1 || y2 + Params.world_height - y1 == -1);
    }

    @Test
    public void TestLeft() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.walk(4);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(x2 - x1 == -1 || x2 + Params.world_width - x1 == -1);
        assertTrue(y1 == y2);
    }

    @Test
    public void TestDownLeft() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.walk(5);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(x2 - x1 == -1 || x2 + Params.world_width - x1 == -1);
        assertTrue(y2 - y1 == 1 || y2 - Params.world_height - y1 == 1);
    }

    @Test
    public void TestDown() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.walk(6);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(y2 - y1 == 1 || y2 - Params.world_height - y1 == 1);
        assertTrue(x1 == x2);
    }

    @Test
    public void TestDownRight() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.walk(7);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(x2 - x1 == 1 || x2 + Params.world_width - x1 == 1);
        assertTrue(y2 - y1 == 1 || y2 - Params.world_height - y1 == 1);
    }
    @Test
    public void TestRunRight() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.run(0);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(x2 - x1 == 2 || x2 + Params.world_width - x1 == 2);
        assertTrue(y1 == y2);
    }

    @Test
    public void TestRunUpRight() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.run(1);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(x2 - x1 == 2 || x2 + Params.world_width - x1 == 2);
        assertTrue(y2 - y1 == -2 || y2 + Params.world_height - y1 == -2);
    }

    @Test
    public void TestRunUp() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.run(2);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(y2 - y1 == -2 || y2 + Params.world_height - y1 == -2);
        assertTrue(x1 == x2);
    }

    @Test
    public void TestRunUpLeft() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.run(3);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(x2 - x1 == -2 || x2 - Params.world_width - x1 == -2);
        assertTrue(y2 - y1 == -2 || y2 + Params.world_height - y1 == -2);
    }

    @Test
    public void TestRunLeft() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.run(4);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(x2 - x1 == -2 || x2 + Params.world_width - x1 == -2);
        assertTrue(y1 == y2);
    }

    @Test
    public void TestRunDownLeft() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.run(5);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(x2 - x1 == -2 || x2 + Params.world_width - x1 == -2);
        assertTrue(y2 - y1 == 2 || y2 - Params.world_height - y1 == 2);
    }

    @Test
    public void TestRunDown() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.run(6);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(y2 - y1 == 2 || y2 - Params.world_height - y1 == 2);
        assertTrue(x1 == x2);
    }

    @Test
    public void TestRunDownRight() throws InvalidCritterException {
        Critter.makeCritter("MyCritter1");
        MyCritter1 c1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        int x1 = c1.getX_coord();
        int y1 = c1.getY_coord();
        c1.run(7);
        int x2 = c1.getX_coord();
        int y2 = c1.getY_coord();
        assertTrue(x2 - x1 == 2 || x2 + Params.world_width - x1 == 2);
        assertTrue(y2 - y1 == 2 || y2 - Params.world_height - y1 == 2);
    }

}
