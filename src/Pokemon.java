package src;
/**
 * Represents a Pokémon with its attributes.
 * This record stores the ID, name, type, height, and weight of a Pokémon.
 * 
 * @author Cassia Salmon
 */
public record Pokemon(int ID, String name, String type, double height, double weight) {
    /**
     * Returns a string representation of the Pokémon.
     *
     * @return a formatted string containing the Pokémon's details.
     */
    @Override
    public String toString() {
        return "ID: " + ID +
               " | Name: " + name +
               " | Type: " + type +
               " | Height: " + height +
               " | Weight: " + weight;
    }
}
