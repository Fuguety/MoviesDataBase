package es.upsa.sbd2.trabajo1;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class ExtractEpisodeInfo {

    public static String extractEpisodeInfo(String filePath) {
        try {
            return new String(Files.readAllBytes(Path.of(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}


