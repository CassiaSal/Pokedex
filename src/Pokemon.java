package src;

import java.util.List;

/**
 * Represents a Pokémon with its attributes.
 * This record stores the ID, name, type, height, and weight of a Pokémon.
 * 
 * @author Cassia Salmon
 */
public record Pokemon(long ID, String name, List<String> types, double height, double weight) {
    /**
     * Returns a string representation of the Pokémon.
     *
     * @return a formatted string containing the Pokémon's details.
     */
    @Override
    public String toString() {
        return "| ID: " + ID +
               " | Name: " + name +
               " | Type: " + String.join(" & ", types) +
               " | Height: " + height +
               " | Weight: " + weight + " |";
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Pokemon){
            Pokemon otherP = (Pokemon) other;
            return otherP.ID() == ID;
        } else {
            return false;
        }
    }
}
