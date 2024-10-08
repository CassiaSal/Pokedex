package src;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * The Pokedex class fetches and displays the data for the original 151 Pokemon 
 * from the PokeAPI and prints their details to the console.
 * 
 * @author Cassia Salmon
 */
public class Pokedex{
    private static List<Pokemon> pokedex = new ArrayList<>();
    /**
     * This method calls the fetchPokemon method to retrieve the Pokemon data
     * and prints each Pokemon's details to the console.
     *
     * @param args Command-line arguments (Not used in this program).
     */
    public static void main(String[] args){
        for(int i = 1; i <= 151; i++){
            makePokemon(i);
        }
        pokedex.forEach(System.out::println);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Main menu loop
        while(!exit){
            System.out.println("\nMenu:  Show All  |  Sort by ID  |  Sort by Name  |  Sort by Type  |  Sort by Height  |  Sort by Weight  |  Search Pokemon  |  Exit  |");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            
            //Checking which option the user has selected
            switch (choice.toLowerCase()) {
                case "exit":
                    exit = true;
                    break;
                case "show all":
                    pokedex.forEach(System.out::println);
                    break;
                case "sort by id":
                    sortById();
                    break;
                case "sort by name":
                    sortByName();
                    break;
                case "sort by type":
                    sortByType();
                    break;
                case "sort by height":
                    sortByHeight();
                    break;
                case "sort by weight":
                    sortByWeight();
                    break;
                case "search pokemon":
                    System.out.print("Enter name or ID to search: ");
                    String searchTerm = scanner.nextLine();
                    searchPokemon(searchTerm);
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
        scanner.close();
    }

    /**
     * Fetches the information for a specific pokemon from the PokeAPI.
     * 
     * @param ID the pokemon we need information from.
     * @return the information gathered from the API.
     */
    public static String fetchInformation(int ID){
        StringBuilder response = new StringBuilder();
        try{
        @SuppressWarnings("deprecation")
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + ID + "/");

            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");
            connect.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String line;
            //Reading each line of the file
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return response.toString();
    }

    /**
     * Fetches the Pokemon data from the PokeAPI and returns a list of Pokemon objects.
     *
     * @param ID The ID number of the Pokemon we want information about.
     */
    public static void makePokemon(int ID){
        try{
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(fetchInformation(ID));

            //Extract the information from the API
            String name = (String) jsonObject.get("name");
            int height = ((Number) jsonObject.get("height")).intValue();
            int weight = ((Number) jsonObject.get("weight")).intValue();

            // Create an ArrayList to store all the types
            ArrayList<String> types = new ArrayList<>();

            // Extract the 'types' array from the JSON object
            JSONArray typesArray = (JSONArray) jsonObject.get("types");

            // Iterate through each type entry in the array
            for (int i = 0; i < typesArray.size(); i++) {
                JSONObject typeObject = (JSONObject) ((JSONObject) typesArray.get(i)).get("type");
                String typeName = (String) typeObject.get("name");
                types.add(typeName);  // Add the extracted type to the ArrayList
            }

            // Create Pokemon object and add it to the pokedex
            Pokemon pokemon = new Pokemon(ID, name, types, height, weight);
            pokedex.add(pokemon);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Searches the list of Pokemon by their name or ID.
     * Prints the list of matching Pokemon or a message if no Pokemon is found.
     *
     * @param search The search string to match against Pokemon names or IDs.
     */
    public static void searchPokemon(String search) {
        // Find the first matching Pokemon based on the search term
        Optional<Pokemon> result = pokedex.stream()
                                          .filter(p -> p.name().toLowerCase().contains(search.toLowerCase()) ||
                                           String.valueOf(p.ID()).equals(search))
                                          .findFirst(); // Get the first match

        // Check if a Pokemon was found
        if (result.isPresent()) {
            Pokemon pokemon = result.get();
            try{
                //Get extra information about the pokemon
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(fetchInformation(pokemon.ID()));
                
                //Extract Abilities
                JSONArray abilitiesArray = (JSONArray) jsonObject.get("abilities");
                List<String> abilities = new ArrayList<>();
                for (Object abilityObj : abilitiesArray) {
                    JSONObject ability = (JSONObject) ((JSONObject) abilityObj).get("ability");
                    abilities.add((String) ability.get("name"));
                }

                // Extract base stats
                Map<String, Integer> baseStats = new HashMap<>();
                JSONArray statsArray = (JSONArray) jsonObject.get("stats");
                for (Object statObj : statsArray) {
                    JSONObject stat = (JSONObject) statObj;
                    String statName = (String) ((JSONObject) stat.get("stat")).get("name");
                    int baseStat = ((Number) stat.get("base_stat")).intValue();
                    baseStats.put(statName, baseStat);
                }
            
                System.out.println("Found Pokemon: " + pokemon.print(abilities, baseStats));

            } catch(Exception e){
                e.printStackTrace();
            }

        } else {
            System.out.println("No Pokemon found with the term: " + search);
        }
    }

    /**
     * Sorts the list of Pokemon by their ID in ascending order and prints the sorted list.
     */
    public static void sortById() {
        pokedex.sort(Comparator.comparingLong(Pokemon::ID));
        System.out.println("Sorted by ID:");
        pokedex.forEach(System.out::println);
    }

    /**
     * Sorts the list of Pokemon by their name in alphabetical order and prints the sorted list.
     */
    public static void sortByName() {
        pokedex.sort(Comparator.comparing(Pokemon::name));
        System.out.println("Sorted by Name:");
        pokedex.forEach(System.out::println);
    }

    /**
     * Sorts the list of Pokemon by their first type in alphabetical order and prints the sorted list.
     */
    public static void sortByType() {
        pokedex.sort(Comparator.comparing(s -> s.types().get(0)));
        System.out.println("Sorted by Type:");
        pokedex.forEach(System.out::println);
    }

    /**
     * Sorts the list of Pokemon by their height in ascending order and prints the sorted list.
     */
    public static void sortByHeight() {
        pokedex.sort(Comparator.comparingInt(Pokemon::height));
        System.out.println("Sorted by Height:");
        pokedex.forEach(System.out::println);
    }

    /**
     * Sorts the list of Pokemon by their weight in ascending order and prints the sorted list.
     */
    public static void sortByWeight() {
        pokedex.sort(Comparator.comparingInt(Pokemon::weight));
        System.out.println("Sorted by Weight:");
        pokedex.forEach(System.out::println);
    }

}