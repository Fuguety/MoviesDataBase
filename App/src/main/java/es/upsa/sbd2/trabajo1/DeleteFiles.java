package es.upsa.sbd2.trabajo1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFiles {

    public static void deleteFiles() {
        try {
            Path path = Paths.get("Actor1.json");
            Files.delete(path);
            path = Paths.get("Actor2.json");
            Files.delete(path);
            path = Paths.get("Actor3.json");
            Files.delete(path);
            path = Paths.get("Actor4.json");
            Files.delete(path);
            path = Paths.get("Actor5.json");
            Files.delete(path);
            path = Paths.get("Episodes.json");
            Files.delete(path);
            path = Paths.get("Movies.json");
            Files.delete(path);
            path = Paths.get("Rating.json");
            Files.delete(path);
            path = Paths.get("Series.json");
            Files.delete(path);
            path = Paths.get("TopCast.json");
            Files.delete(path);

            System.out.println("Files deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
