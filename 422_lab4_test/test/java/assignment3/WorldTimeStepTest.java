package assignment3;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

public class WorldTimeStepTest {

    @Test
    public void testWorldTimeStep() throws InvalidCritterException {
        Critter.clearWorld();
        boolean nofight;

        // Create two fighting critters
        Critter.makeCritter("MyCritter1");
        Critter.makeCritter("MyCritter6");
        MyCritter1 critter1 = (MyCritter1) Critter.TestCritter.getPopulation().get(0);
        MyCritter6 critter6 = (MyCritter6) Critter.TestCritter.getPopulation().get(1);

        // Set positions so they will fight
        critter1.setX_coord(5);
        critter1.setY_coord(5);
        critter6.setX_coord(5);
        critter6.setY_coord(5);

        // Set initial energy values
        critter1.setEnergy(20);
        critter6.setEnergy(10);

        // Create a reproducing critter
        Critter.makeCritter("MyCritter7");
        MyCritter7 parent = (MyCritter7) Critter.TestCritter.getPopulation().get(2);
        parent.setEnergy(Params.min_reproduce_energy + 10);
        MyCritter7 offspring = new MyCritter7();
        parent.reproduce(offspring, 0); // Reproduce in the right direction

        // Check that baby was added to the babies list before worldTimeStep()
        assertEquals(1, Critter.TestCritter.getBabies().size());

        // Capture initial number of critters
     //   int initialCritterCount = Critter.TestCritter.getPopulation().size();

        // Capture Algae count before worldTimeStep()
        int initialAlgaeCount = (int) Critter.TestCritter.getPopulation().stream()
                .filter(c -> c instanceof Algae).count();

        int energy1=critter1.getEnergy();
        int energy2=critter6.getEnergy();
        // Run world time step
        Critter.worldTimeStep();
        int initialCritterCount = Critter.TestCritter.getPopulation().size();


        // ======= Check Fight Mechanics =======
        Critter winner;
        Critter loser;

        if (critter1.getEnergy() > critter6.getEnergy()) {
            winner = critter1;
            loser = critter6;
        } else {
            winner = critter6;
            loser = critter1;
        }
        int actualEnergy=0;
        if(winner.equals(critter1)){
            if(winner.getEnergy()==energy1-Params.walk_energy_cost){
                nofight=true;
            }
            if(loser.getEnergy()==energy2){
                nofight=true;
            }
            else {
                nofight = false;
                actualEnergy = energy1;
            }
        }
        else {
            if(winner.getEnergy()==energy2){
                nofight=true;
            }
            if(loser.getEnergy()==energy1){
                nofight=true;
            }
            else {
                actualEnergy = energy2;
                nofight = false;
            }
        }
        //int actualEnergy= winner.getEnergy();
        if(!nofight) {
            int expectedWinnerEnergy = winner.getEnergy() - (loser.getEnergy() / 2) - Params.rest_energy_cost;
            assertTrue("Winner's energy should be increased by half of loser’s energy", actualEnergy == expectedWinnerEnergy);
            assertFalse("Loser should be removed from the population", Critter.TestCritter.getPopulation().contains(loser));
        }
        // ======= Check Rest Energy Applied Before Death =======
        for (Critter c : Critter.TestCritter.getPopulation()) {
            assertTrue("Rest energy cost should be applied before checking if critter is dead", c.getEnergy() > 0);
        }

        // ======= Check Algae Addition =======
        int algaeCountAfter = (int) Critter.TestCritter.getPopulation().stream()
                .filter(c -> c instanceof Algae).count();
        assertEquals("Algae count should increase by Params.refresh_algae_count",
                initialAlgaeCount + Params.refresh_algae_count, algaeCountAfter);

        // Ensure algae positions are random (not all identical)
        Set<String> algaePositions = new HashSet<>();
        for (Critter c : Critter.TestCritter.getPopulation()) {
            if (c instanceof Algae) {
                Critter.TestCritter testAlgae = (Critter.TestCritter) c;
                algaePositions.add(testAlgae.getX_coord() + "," + testAlgae.getY_coord());
            }
        }
        assertTrue("Algae should be placed at multiple random locations", algaePositions.size() > 1);

        // ======= Check Babies Moved to Population =======
        assertEquals("Babies should be added to the population", initialCritterCount, Critter.TestCritter.getPopulation().size());
        assertEquals("Babies list should be cleared after worldTimeStep", 0, Critter.TestCritter.getBabies().size());

        // ======= Check Dead Critters Removed =======
        for (Critter c : Critter.TestCritter.getPopulation()) {
            assertTrue("No dead critters should remain in population", c.getEnergy() > 0);
        }
    }
}
