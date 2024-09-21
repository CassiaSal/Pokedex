package src;
import java.util.ArrayList;
import java.util.List;

/**
 * The Pokedex class fetches and displays the data for the original 151 Pokemon 
 * from the PokeAPI and prints their details to the console.
 * 
 * @author Cassia Salmon
 */
public class Pokedex{

    /**
     * This method calls the fetchPokemon method to retrieve the Pokemon data
     * and prints each Pokemon's details to the console.
     *
     * @param args Command-line arguments (Not used in this program).
     */
    public static void main(String[] args){
        List<Pokemon> pokedex = fetchPokemon();
        pokedex.forEach(System.out::println);
    }

    /**
     * Fetches the Pokemon data from the PokeAPI and returns a list of Pokemon objects.
     *
     * @return A List of Pokemon objects representing the original 151 Pokemon.
     */
    public static List<Pokemon> fetchPokemon(){
        List<Pokemon> pokedex = new ArrayList<>();
        try{

        } catch (Exception e){}
        return pokedex;
    }
}