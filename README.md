# Lab 3: Critters

**Author:** Niranjan Telkikar  

---

## Overview
This project implements a Critters simulation using a provided population list data structure. Two custom critter subclasses were created, along with full implementations of the required Critter framework methods, world time stepping, and a command driven main controller.

---

## Critter Implementations

### Critter1
- Reproduces four times during each `doTimeStep`
- Walks to the right
- Always chooses to fight (`fight` returns `true`)
- `toString()` returns `"1"`

### Critter2
- Does nothing during its `doTimeStep`
- Moves right during a fight
- Always chooses to fight (`fight` returns `true`)
- `toString()` returns `"2"`

---

## Core `Critter` Class Methods

### Movement Methods
- **walk()**  
  Moves the critter one step in the specified direction.

- **run()**  
  Moves the critter two steps in the specified direction.

- **move()**  
  - Checks whether the critter has sufficient energy to move
  - Subtracts the appropriate energy cost
  - Updates the `(x, y)` coordinates based on direction and number of steps
  - Wraps coordinates using modulo arithmetic to maintain a toroidal grid

---

### Reproduction
- **reproduce(Critter offspring, int direction)**  
  - Returns immediately if the parent energy is below the minimum reproduction threshold
  - Splits energy between parent and offspring
    - Offspring receives half the energy
    - Parent keeps the remaining half, rounded up
  - Sets offspring coordinates using the same directional logic as movement
  - Adds offspring to the babies list

---

### Critter Creation and Queries

- **makeCritter(String critterClassName)**  
  - Dynamically loads the critter class using `Class.forName`
  - Instantiates a new critter
  - Assigns random `(x, y)` coordinates
  - Initializes energy using `Params.start_energy`
  - Adds the critter to the population list
  - Throws `InvalidCritterException` if the class does not exist

- **getInstances(String critterClassName)**  
  - Loads the requested critter class
  - Iterates through the population
  - Returns a list of critters matching the specified class
  - Throws an exception if the class does not exist

- **runStats(List<Critter>)**  
  Prints the number of critters of each type currently in the world.

---

### World Management

- **clearWorld()**  
  - Clears all critters, alive or dead
  - Empties both the population and babies lists

- **worldTimeStep()**  
  Executes a full simulation step:
  1. Each critter performs its individual time step
  2. Encounters are resolved for critters occupying the same position
  3. `fight()` is invoked for each critter in an encounter
  4. Random rolls determine combat outcomes when applicable
  5. Winner gains half of the loser's energy
  6. Rest energy cost is subtracted
  7. New algae are spawned with random positions and starting energy
  8. Babies are added to the population
  9. Babies list is cleared
  10. Dead critters are removed

---

### World Display

- **displayWorld()**  
  - Creates a 2D grid using `Params.world_height` and `Params.world_width`
  - Initializes the grid with spaces
  - Places critters using their `toString()` representations
  - Prints the top boundary, side walls, and bottom boundary

---

## Main Controller

The `Main` class processes user commands by splitting input on whitespace and dispatching the appropriate methods.

### Supported Commands

- `quit`  
  Terminates the program

- `show`  
  Displays the current world using `Critter.displayWorld()`

- `step [count]`  
  Advances the simulation by one step or by `count` steps if provided

- `seed <number>`  
  Sets the random number generator seed for deterministic testing

- `make <class_name> [count]`  
  Creates one or more instances of the specified critter subclass
  - If no count is provided, one critter is created
  - If a count is provided, the critter is created that many times

---

## Notes
- For Stages 1 and 2, the world initializes with 100 Algae and 25 Craig critters
- For Stage 3, the world starts empty
- All grid wrapping follows toroidal world rules
