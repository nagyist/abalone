import javafx.scene.paint.Color;

public class Player {

    public Player(int number, Color color) {
        this.number = number;
        this.color = color;
    }

    protected int incScore() { return (++score); }

    protected int number;
    protected Color color;
    protected int score = 0;
    protected int nbPieces = 0;
}
