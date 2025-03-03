package assignment3;

import assignment3.Critter.TestCritter;
//This critter loves to reproduce and has many children!
public class Critter1 extends TestCritter {

    @Override
    public void doTimeStep() {
        Critter child=new Critter1();
        reproduce(child, Critter.getRandomInt(7));
        reproduce(child, Critter.getRandomInt(7));
        reproduce(child, Critter.getRandomInt(7));
        reproduce(child, Critter.getRandomInt(7));
        walk(0);
    }

    @Override
    public boolean fight(String oponent) {
        return true;
    }

    public String toString() {
        return "1";
    }

}
