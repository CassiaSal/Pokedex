package src;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
            long id = (long) jsonObject.get("id");
            String name = (String) jsonObject.get("name");
            int height = ((Number) jsonObject.get("height")).intValue();
            int weight = ((Number) jsonObject.get("weight")).intValue();

            // Create an ArrayList to store all types
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
            Pokemon pokemon = new Pokemon(id, name, types, height, weight);
            pokedex.add(pokemon);
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}