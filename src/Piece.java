import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

class Piece extends Group {

    public Piece(Player player, Cell cell) {
        this.player = player;
        this.cell = cell;

        ++player.nbPieces;

        ellipse = new Ellipse(width, height);
        ellipse.setFill(player.color);
        ellipse.setStrokeWidth(2);
        ellipse.setStroke(player.color);
        getChildren().addAll(ellipse);
    }

    protected void select(boolean s) {
        ellipse.setStroke(s ? red : player.color);
        selected = s;
    }

    protected void setCell(Cell cell) {
        this.cell = cell;
    }

    protected Player player;
    private Cell cell;
    private Ellipse ellipse = null;

    protected boolean selected = false;

    private int width = 16;
    private int height = 16;
    private Color red = Color.INDIANRED;

}