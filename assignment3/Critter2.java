package assignment3;
import assignment3.Critter.TestCritter;
//This critter tries to walk straight right during a fight!
public class Critter2 extends TestCritter {

    @Override
    public void doTimeStep() {

    }

    @Override
    public boolean fight(String oponent) {
        walk(0);
        return true;
    }


    public String toString() {
        return "2";
    }

}
