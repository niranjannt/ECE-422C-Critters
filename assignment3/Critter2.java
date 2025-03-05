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
//This critter tries to walk straight right during a fight!
public class Critter2 extends TestCritter {



    /**
     * This critter does not do anything during a time step
     */
    @Override
    public void doTimeStep() {

    }

    /**
     * This critter loves to walk right and fight!
     * @param oponent
     * @return
     */
    @Override
    public boolean fight(String oponent) {
        walk(0);
        return true;
    }

    /**
     *  The toString representation of Critter2 is 2
     * @return 2
     */
    public String toString() {
        return "2";
    }

}
