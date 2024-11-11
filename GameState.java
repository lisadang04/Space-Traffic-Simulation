public class GameState {
    private int score;

    public GameState() {
        this.score = 0; // Initialize score to zero
    }

    // Method to increase the score
    public void increaseScore() {
        score++;
    }

    // Method to decrease the score
    public void decreaseScore() {
        if (score > 0) {
            score--;
        }
    }

    // Getter for the score
    public int getScore() {
        return score;
    }
}