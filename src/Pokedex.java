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
        for (int i = 1; i <= 151; i++) {
            //int i = 1;
                @SuppressWarnings("deprecation")
                URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + i + "/");

                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");
                connect.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(response.toString());

                long id = (long) jsonObject.get("id");
                String name = (String) jsonObject.get("name");
                double height = ((Number) jsonObject.get("height")).doubleValue();
                double weight = ((Number) jsonObject.get("weight")).doubleValue();

                // Extract type from the types array
                JSONArray typesArray = (JSONArray) jsonObject.get("types");
                JSONObject typeObject = (JSONObject) ((JSONObject) typesArray.get(0)).get("type");
                String type = (String) typeObject.get("name");
                List<String> types = new ArrayList<>();
                types.add(type);

                // Create Pokemon object and add it to the pokedex
                Pokemon pokemon = new Pokemon(id, name, types, height, weight);
                pokedex.add(pokemon);
            }
            } catch (Exception e){
                e.printStackTrace();
            }
            
        return pokedex;
    }
}