# Mind Game

A memory challenge game with various categories like shapes, images, and maths, built using Java and Swing for the frontend and backend.

## Features
- **Shape Challenge**: Match identical shapes by clicking on cards.
- **Image Challenge**: Match pairs of identical images.
- **Maths Challenge**:Identify the banana value.
- **Leaderboard**: Track high scores across all levels.
- **User Authentication**: Login/logout functionality for players.

## Technologies Used
- **Java**: Backend and game logic.
- **Swing**: GUI design and interaction.
  
## Libraries Used
### 1. SQL Connector
- **Library**: MySQL JDBC Connector
- **Purpose**: Used to connect to the MySQL database and interact with it to store user scores, game data, and other persistent information.


### 2. JSON
- **Library**: JSON.simple or Jackson
- **Purpose**: Used to parse and generate JSON data for saving and loading game configurations, player progress, or other structured data.



### Explanation of Directories and Files:

1. **`src/`**: Contains the source code for your application.
   - **`src/main/java/`**: All Java source files, organized by package.
   - **`src/main/resources/`**: Non-code resources like images and icons.

2. **`lib/`**: This folder contains **external libraries (JAR files)** that are manually included in your project.
   - **`mysql-connector-java.jar`**: The MySQL connector used to interact with a MySQL database.
   - **`json-simple-1.1.1.jar`**: The JSON library for parsing and generating JSON data.
   - Add any other external libraries you are using here.

3. **`config/`**: Configuration files, such as settings for the game, in formats like JSON, XML, or properties files.

4. **`tests/`**: If you have written unit tests, this folder contains them. Tests are typically organized in a similar structure to `src/`.

5. **`README.md`**: The documentation for your project that includes instructions on how to run, build, and understand the project.

6. **`LICENSE`**: The license for your project, if applicable.

### Important Notes:
- **External Libraries**: In this case, you’re manually managing external libraries by placing them in the `lib/` folder. You would need to configure your IDE (like IntelliJ or Eclipse) to recognize the JAR files in the `lib/` directory so that they are included in the classpath when you run the application.
 
This structure should now clearly reflect that your project includes external libraries directly, and will make it easy for anyone working on the project to understand the folder organization.
