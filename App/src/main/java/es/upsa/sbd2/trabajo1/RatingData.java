package es.upsa.sbd2.trabajo1;

public class RatingData {
    private double averageRating;
    private int numVotes;

    public RatingData(double averageRating, int numVotes) {
        this.averageRating = averageRating;
        this.numVotes = numVotes;
    }

    @Override
    public String toString() {
        return "RatingData{" +
                "average=" + averageRating +
                ", numVotes=" + numVotes +
                '}';
    }
}
