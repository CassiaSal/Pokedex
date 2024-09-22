# Pokédex Command-Line Application
## Description
This program is a simple command-line Pokédex that displays data for the original 151 Pokémon from the PokéAPI.
It displays the ID, name, type, height and weight for each Pokémon. It can sort the Pokémon by each of these attributes and search for specific ones by their name or ID number.

## Features
- Fetches and displays the first 151 Pokémon with their ID, name, type, height, and weight.
- Sorts the Pokémon list by ID, name, type, height, and weight.
- Provides a search function to filter Pokémon by name or ID number and prints more specific information about it.

## Installation
To run this project, you need:
- Java 11 or higher
- An internet connection to fetch data from the PokéAPI

Steps to Run the Project:

Open Visual Studio Code and clone the repository:

```
https://github.com/CassiaSal/Pokedex.git
```
Go to the Pokedex.java file in the src folder.

Click "Run Java"

Once the program starts, it will fetch and display the first 151 Pokémon with basic details and open a menu that shows the other options.

## Future Enhancements
- **Data Retrieval:** Currently I am getting the information from the REST API Docs instead of the GraphQL Docs. The REST API docs takes some time to load and fetch the information. In the future I could use the GraphQL Docs in order to speed up the process. Using GraphQL would be more efficient as I can request specific data instead of sorting through all of it.
- **Type Sorting:** The current implementation of only sorts the Pokémon by their first type. I could improve this to use all of the Pokémon's types to make the sorting system more comprehensive.
- **User Interface:** With more time, I would love to develop a web application to create a more user-friendly interface. I’ve considered using htmx to make a website quickly to create a better user experience.

## Credits
Created by Cassia Salmon. Feel free to reach out if you have any questions!
