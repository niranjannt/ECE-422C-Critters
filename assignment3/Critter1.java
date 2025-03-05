/* CRITTERS <MyClass.java>
 * ECE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Niranjan Telkikar
 * nnt479
 * Slip days used: <0>
 * Fall 2024
 */

package assignment3;

import assignment3.Critter.TestCritter;
//This critter loves to reproduce and has many children!
public class Critter1 extends TestCritter {
    /**
     * This program has 4 children and walks right during its timestep
     */
    @Override
    public void doTimeStep() {
        Critter child=new Critter1();
        reproduce(child, Critter.getRandomInt(7));
        reproduce(child, Critter.getRandomInt(7));
        reproduce(child, Critter.getRandomInt(7));
        reproduce(child, Critter.getRandomInt(7));
        walk(0);
    }

    /**
     * This  critter loves to fight!
     * @param oponent
     * @return true
     */
    @Override
    public boolean fight(String oponent) {
        return true;
    }

    /**
     * This critter's toString representation is 1
     * @return 1
     */
    public String toString() {
        return "1";
    }

}
