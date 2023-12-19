package es.upsa.sbd2.trabajo1;

public class MovieData {
    private String id;
    private String title;
    private String imageUrl;
    private String releaseDate;

    public MovieData(String id, String title, String imageUrl, String releaseDate) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
