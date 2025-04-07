package entities;

public class Player {
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public static Player dummy = new Player("Dummy");

    private final String name;
    private int score;

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}