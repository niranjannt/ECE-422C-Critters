/* CRITTERS Critter.java
 * ECE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student Name>
 * <Student EID>
 * Slip days used: <0>
 * Fall 2024
 */
package assignment3;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */
/*Initial commit*/

public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}


	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }

	private int energy = 0;
	protected int getEnergy() { return energy; }

	private int x_coord;
	private int y_coord;

	protected final void walk(int direction) {
		move(direction, 1, Params.walk_energy_cost);

	}

	protected final void run(int direction) {
		move(direction, 2, Params.run_energy_cost);

	}

	private void move(int direction, int steps, int energyCost) {
		if (energy < energyCost) return;
		energy -= energyCost;
		int[] dx = {1, 1, 0, -1, -1, -1, 0, 1};
		int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
		x_coord = (x_coord + dx[direction] * steps + Params.world_width) % Params.world_width;
		y_coord = (y_coord + dy[direction] * steps + Params.world_height) % Params.world_height;
	}

	protected final void reproduce(Critter offspring, int direction) {
		// The parent splits its energy equally with the offspring
		if(!(this.energy>=Params.min_reproduce_energy)){
			return;
		}
		offspring.energy = this.energy / 2;
		this.energy = (int) Math.ceil((double) this.energy / 2);

		// Set the offspring's coordinates based on the parent's coordinates and direction
		int[] dx = {1, 1, 0, -1, -1, -1, 0, 1};
		int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
		offspring.x_coord = (x_coord + dx[direction]  + Params.world_width) % Params.world_width;
		offspring.y_coord = (y_coord + dy[direction]  + Params.world_height) % Params.world_height;

		// Add the offspring to the parent's list of babies (or offspring)
		babies.add(offspring);
	}


	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);

	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		if (critter_class_name.length() != 0 && Character.isLowerCase(critter_class_name.charAt(0))){
			throw new InvalidCritterException(critter_class_name);
		}
		try {
			Class critterClass = Class.forName(myPackage + '.' + critter_class_name);
			Critter critter = (Critter) critterClass.newInstance();
			critter.x_coord = getRandomInt(Params.world_width);
			critter.y_coord = getRandomInt(Params.world_height);
			critter.energy = Params.start_energy;
			population.add(critter);
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoClassDefFoundError e) {
			throw new InvalidCritterException(critter_class_name);
		}


	}

	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			Class critterClass = Class.forName(myPackage + "." + critter_class_name);
			for (Critter critter : population) {
				if (critterClass.equals(critter.getClass())) {
					result.add(critter);
				}
			}
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		return result;
	}


	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();
	}


	/* the TestCritter class allows some critters to "cheat". If you want to
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here.
	 *
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}

		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}

		protected int getX_coord() {
			return super.x_coord;
		}

		protected int getY_coord() {
			return super.y_coord;
		}


		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}

		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}


	public static void worldTimeStep() {
		for(Critter critter :population){
			critter.doTimeStep();
		}


		removeDeadCritters();

		for (int i = 0; i < population.size(); i++) {
			Critter critter1 = population.get(i);
			int rolla;
			int rollb;
			for (int j = i + 1; j < population.size(); j++) { // Start from i + 1 to avoid duplicate checks
				Critter critter2 = population.get(j);
				if (critter1.x_coord == critter2.x_coord && critter1.y_coord == critter2.y_coord) {
					boolean fight1= critter1.fight(critter2.toString());
					boolean fight2=critter2.fight(critter1.toString());
					if(critter2.getEnergy()>0 && critter1.getEnergy()>0){
						if(fight1){
							rolla=getRandomInt(critter1.getEnergy());
						}
						else{
							rolla=0;

						}
						if(fight2){
							rollb=getRandomInt(critter2.getEnergy());
						}
						else{
							rollb=0;
						}
						if(rollb>rolla){
							critter2.energy+= (critter1.getEnergy()/2);
							population.remove(critter1);
						}
						else if(rolla>rollb){
							critter1.energy+=(critter2.getEnergy()/2);
							population.remove(critter2);


						}
						else{
							int random=getRandomInt(2);
							if(random==0){
								critter1.energy+=(critter2.getEnergy()/2);
								population.remove(critter2);

							}
							else{
								critter2.energy+= (critter1.getEnergy()/2);
								population.remove(critter1);

							}
						}
					}
				}
			}


		}

		for (Critter critter: population) {
			critter.energy -= Params.rest_energy_cost;
		}


		for(int i=0; i<Params.refresh_algae_count; i++){
			Algae algae = new Algae();
			algae.setX_coord(getRandomInt(Params.world_width)) ;
			algae.setY_coord(getRandomInt(Params.world_width));
			algae.setEnergy(Params.start_energy);
			population.add(algae);
		}

		population.addAll(babies);

		babies.clear();

		removeDeadCritters();

	}
	private static void removeDeadCritters(){
		population.removeIf(critter -> critter.energy <= 0);

	}
//Tested
	public static void displayWorld() {
		char[][] grid = new char[Params.world_height][Params.world_width];
		for (char[] row : grid) java.util.Arrays.fill(row, ' ');
		for (Critter critter : population) grid[critter.y_coord][critter.x_coord] = critter.toString().charAt(0);

		System.out.println("+" + "-".repeat(Params.world_width) + "+");
		for (char[] row : grid) System.out.println("|" + new String(row) + "|");
		System.out.println("+" + "-".repeat(Params.world_width) + "+");
	}
}
