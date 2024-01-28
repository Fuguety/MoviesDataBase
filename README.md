# MoviesDataBase

A Java project that utilizes [RapidApi](https://rapidapi.com) to search for series in a database and saves the retrieved data in JSON format.

## Overview

This project consists of several Java classes that work together to perform the following tasks:

- **Main (`Main.java`):**
  - Entry point for the application.
  - Requests data for a specified series using RapidApi.
  - Saves the retrieved data in JSON format.
  - Deletes temporary files if in debug mode.

  ```java
  // Sample code snippet for deleting temporary files in debug mode
  boolean debug = true;
  if (debug) {
      DeleteFiles.deleteFiles();
  }

- **DeleteFiles (`DeleteFiles.java`):**
  - Deletes specific JSON files used for temporary storage.

- **GetDataJsonMovies (`GetDataJsonMovies.java`):**
  - Extracts data from the Movies.json file, specifically for movies.

- **GetRatingJson (`GetRatingJson.java`):**
  - Extracts rating data from the Rating.json file.

- **JsonUtils (`JsonUtils.java`):**
  - Writes indented JSON data to a file.
  - Provides additional functions for working with JSON data, such as extracting IDs and top 5 IDs.

- **RequestData (`RequestData.java`):**
  - Requests series data from RapidApi, processes and stores it in JSON format.
  - Extracts additional data, such as episodes, ratings, and top cast members.
  - Saves each cast member's data in separate JSON files.

- **RequestDataEpisodes (`RequestDataEpisodes.java`):**
  - Requests detailed data for each episode, including ratings and other information.
  - Creates a combined JSON file containing information about episodes, including ratings.

- **RatingData (`RatingData.java`):**
  - Represents rating data, including average rating and number of votes.


## Usage

1. Open `Main.java` and set the `apiKey` variable with your RapidApi key.
2. Run the `Main` class to initiate the series data retrieval process.
3. Check the generated JSON files for the collected data.

## Dependencies

- Java
- RapidApi key (Replace `"Key Here"` in `Main.java` with your actual key)

## Note

Make sure to handle your RapidApi key securely and avoid sharing it publicly.

#### Code made with

> ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=black) </br>
> [RapidApi](https://rapidapi.com) </br>
> Make sure you are subscribed to this APIs _(Free subscription also works)_:
> [MoviesDataBase](https://rapidapi.com/SAdrian/api/moviesdatabase/)
> [IMDB8](https://rapidapi.com/apidojo/api/imdb8/)
> [IMDB-com](https://rapidapi.com/ntd119/api/imdb-com)


Recommended IDE: ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white) </br>




