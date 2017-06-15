import javafx.scene.paint.Color;
import java.util.HashMap;

public class InitBoard {

    /* Function pointer array */
    private interface Placement { void placePieces(Cell c, Vector3 v); }
    Placement[] placement = new Placement[] {
            new Placement() { public void placePieces(Cell c, Vector3 v) { placeTwo(c, v.y, v.x, v.z); }},
            new Placement() { public void placePieces(Cell c, Vector3 v) { placeThree(c, v.y, v.x, v.z); }},
            new Placement() { public void placePieces(Cell c, Vector3 v) { placeFour(c, v.y, v.x, v.z); }},
            new Placement() { public void placePieces(Cell c, Vector3 v) { placeFive(c, v.y, v.x, v.z); }},
            new Placement() { public void placePieces(Cell c, Vector3 v) { placeSix(c, v.y, v.x, v.z); }},
    };

    /* Players numbers & colors */
    public static final Color[] playersColors = new Color[]{
            Color.DARKSLATEGRAY,
            Color.ALICEBLUE,
            Color.LIGHTSEAGREEN,
            Color.PALEVIOLETRED,
            Color.PURPLE,
            Color.AZURE
    };

    protected void initPlayers(int number) {
        GameLogic.players = new Player[number];
        for (int i = 0; i < GameLogic.players.length; i++)
            GameLogic.players[i] = new Player(i + 1, playersColors[i]);
    }

    protected void initGutters(int radius) {
        HashMap<Vector3, Cell> cells = GameLogic.abaloneBoard.cells;

        cells.forEach((k, v) -> {
            if (k.z == radius || k.x == radius || k.y == radius
                    || k.z == -radius || k.x == -radius || k.y == -radius)
                v.setGutter();
        });
    }

    protected void initPieces(int numberOfPlayers) {
        HashMap<Vector3, Cell> cells = GameLogic.abaloneBoard.cells;

        cells.forEach((vector, cell) -> {
            if (!cell.gutter)
                placement[numberOfPlayers - 2].placePieces(cell, vector);
        });
    }

    protected void placeTwo(Cell cell, double y, double x, double z) {
        if (z == -4 || z == -3
                || (y == 2 && x == 0 && z == -2)
                || (y == 1 && x == 1 && z == -2)
                || (y == 0 && x == 2 && z == -2))
            cell.setPiece(new Piece(GameLogic.players[0], cell));
        else if (z == 4 || z == 3
                || (y == 0 && x == -2 && z == 2)
                || (y == -1 && x == -1 && z == 2)
                || (y == -2 && x == 0 && z == 2))
            cell.setPiece(new Piece(GameLogic.players[1], cell));
        else
            cell.removePiece();
    }

    protected void placeThree(Cell cell, double y, double x, double z) {
        if (z == -4 || z == -3)
            cell.setPiece(new Piece(GameLogic.players[0], cell));
        else if (y == -4 || y == -3)
            cell.setPiece(new Piece(GameLogic.players[1], cell));
        else if (x == -4 || x == -3)
            cell.setPiece(new Piece(GameLogic.players[2], cell));
        else
            cell.removePiece();
    }

    protected void placeFour(Cell cell, double y, double x, double z) {
        if ((z == -4 && x != 4) || (z == -3 && x != 3 && x != 4))
            cell.setPiece(new Piece(GameLogic.players[0], cell));
        else if ((x == 4 && z != -4) || (x == 3 && z != -3))
            cell.setPiece(new Piece(GameLogic.players[1], cell));
        else if ((z == 4 && x != -4) || (z == 3 && x != -3 && x != -4))
            cell.setPiece(new Piece(GameLogic.players[2], cell));
        else if ((x == -4 && z != 4) || (x == -3 && z != 3))
            cell.setPiece(new Piece(GameLogic.players[3], cell));
        else
            cell.removePiece();
    }

    protected void placeFive(Cell cell, double y, double x, double z) {}

    protected void placeSix(Cell cell, double y, double x, double z) {}
}
