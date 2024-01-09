//Name: Varneetpal Kaur
//Student number: 11282219
//NSID: van491
//Course name: CMPT280
//Instructor's name: Mark Eramian

import lib280.list.LinkedList280;

public class Ship {

    // These are the name of the ships in Tractor Jack's fleet.  This is used ot initialize data for the assignemnt.
    // Note that it is a static variable, and not an instance variable.
    public static String ShipNames[] = { "The Prairie Onion", "The Blackstrap", "The Bunnyhug", "The Salty Farmer", "The Icebreaker" };

    // List of sacks of grain to be hauled by this ship.
    protected LinkedList280<Sack> cargo;

    // The name of this ship.
    protected String name;

    // The capacity (in pounds) of this ship.  A ship cannot carry more than this much grain.
    protected float capacity;

    /**
     * Create a new ship.
     *
     * @param name Name of the ship.
     * @param capacity Capacity of the ship in pounds.  The ship can carry sacks of grain with total weight up to this value.
     */
    public Ship(String name, float capacity) {
        this.cargo = new LinkedList280<Sack>();
        this.name = name;
        this.capacity = capacity;
    }

    /**
     * Load a sack of grain onto this ship.
     * @param s The sack of grain to load.
     */
    public void loadSack(Sack s) {
        // Add the sack to the list of sacks this ship is carrying.  Since this is only a simulation, we don't
        // care if this causes the ship to be overloaded at the time of loading.
        cargo.insertFirst(s);
    }

    /**
     * Check whether this ship is overloaded.
     *
     * @return true if the total weight of all sacks of grain (of any type) on this ship exceeds this.capacity,
     *         false otherwise.
     */
    public boolean isOverloaded() {
        // TODO: Implement this method.
        //When the list is empty
        if (cargo.isEmpty()){
            //throw new RuntimeException("Error: Cargo is empty.");
            return false;
        }

        cargo.goFirst();
        double total_val = 0; //used to get the weight of cargo
        while (cargo.itemExists()){
            //adding weight for every item in the list
            total_val = total_val + cargo.item().getWeight();
            cargo.goForth();
        }
        return total_val > this.capacity;
    }

    /**
     * Determine how many sacks of a particular type of grain are on this ship.
     *
     * @param type A type of grain, e.g. WHEAT, BARLEY, RYE (See the enumerated type Grain in Sack.java)
     *
     * @return The number of sacks of grain on this ship with a grain type equal to 'type'.
     */

    public int sacksOfGrainType(Grain type) {
        // TODO: Implement this method.
        if (cargo.isEmpty()){
            throw new RuntimeException("Error: Cargo is empty.");
        }
        int count = 0; //for counting the umber of sacks of each type
        cargo.goFirst();
        while(cargo.itemExists()){
            if (cargo.item().type == type){
                count = count + 1; //for every time a sack of the given type is found
            }
            cargo.goForth();
        }
        return count;
    }

    // Accessor methods ...

    public LinkedList280<Sack> getCargo() {
        return cargo;
    }

    public String getName() {
        return name;
    }

    public float getCapacity() {
        return capacity;
    }

    /**
     *  @return a printable string describing the ship's name and contents.
     */
    public String toString() {
        return this.name + " has a " + this.capacity + " pound capacity and carries: " + cargo.toString()+'\n';
    }
}
