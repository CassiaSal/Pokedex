package src;

import java.util.List;
import java.util.Map;

/**
 * Represents a Pokémon with its attributes.
 * This record stores the ID, name, type, height, and weight of a Pokémon.
 * 
 * @author Cassia Salmon
 */
public record Pokemon(int ID, String name, List<String> types, int height, int weight) {
    /**
     * Returns a string representation of the Pokémon.
     *
     * @return a formatted string containing the Pokémon's details.
     */
    @Override
    public String toString() {
        return String.format("| ID: %-4d | Name: %-10s | Type: %-20s | Height: %-5d | Weight: %-5d |", 
                         ID, name, String.join(" & ", types), height, weight);
    }

    /**
     * Print the pokemon's more specific information.
     * 
     * @param abilities the pokemon's abilities.
     * @param baseStats all of the pokemon's base statistics.
     * @return the string to print out all of the pokemon's information.
     */
    public String print(List<String> abilities, Map<String, Integer> baseStats) {
        String result = "\n\nID: " + ID + "\n" +
                        "Name: " + name + "\n" +
                        "Types: " + String.join(", ", types) + "\n" +
                        "Height: " + height + "\n" +
                        "Weight: " + weight + "\n" +
                        "Abilities: " + String.join(", ", abilities) + "\n" +
                        "Base Stats:\n";

        for (String entry : baseStats.keySet()) {
            result += "     " + entry + ": " + baseStats.get(entry) + "\n";
        }

        return result;
    }
}
